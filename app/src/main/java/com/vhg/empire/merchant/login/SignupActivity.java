package com.vhg.empire.merchant.login;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vhg.empire.merchant.AppConfig;
import com.vhg.empire.merchant.AppController;
import com.vhg.empire.merchant.MainActivity;
import com.vhg.empire.merchant.R;
import com.vhg.empire.merchant.dialogs.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

/**
 * Created by VinceGee on 9/11/2015.
 */
public class SignupActivity extends Activity {
    private static final String TAG = SignupActivity.class.getSimpleName();
    @InjectView(R.id.name) EditText inputFullName;
    @InjectView(R.id.input_email) EditText inputEmail;
    @InjectView(R.id.input_password) EditText inputPassword;
    @InjectView(R.id.btnRegister) Button btnRegister;
    @InjectView(R.id.btnLinkToLoginScreen) Button btnLinkToLogin;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private RadioButton radioButton;
    // Shared Preferences
    public static SharedPreferences pref;

    SharedPreferences.Editor editor;

    public static final int REFERENCE_MODE_PRIVATE = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //ButterKnife.inject(this);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
// custom dialog
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.company_signup_dialog);
                dialog.setTitle("Merchant");

                // set the custom dialog components - text, image and button
                final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.option);



                Button dialogButton = (Button) dialog.findViewById(R.id.OkDialogbutton);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get selected radio button from radioGroup
                        int selectedId = radioGroup.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        radioButton = (RadioButton) dialog.findViewById(selectedId);



                        /*pref = getPreferences(REFERENCE_MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putString("category",radioButton.getText()+"");
                        editor.commit();*/
                        setDefaults("category",radioButton.getText()+"",v.getContext());

                        Toast.makeText(SignupActivity.this,
                                getDefaults("category",v.getContext()), Toast.LENGTH_SHORT).show();


                     /*  pref = getPreferences(REFERENCE_MODE_PRIVATE);
                        Toast.makeText(SignupActivity.this,
                                pref.getString("category","NO VALUE"), Toast.LENGTH_SHORT).show();*/

                        String name = inputFullName.getText().toString().trim();
                        String email = inputEmail.getText().toString().trim();
                        String password = inputPassword.getText().toString().trim();
                        String company = getDefaults("category",v.getContext()).trim();




                        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !company.isEmpty()) {
                            registerUser(name, email, password, company);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please enter your details!", Toast.LENGTH_LONG)
                                    .show();
                        }


                        dialog.dismiss();
                    }
                });

                dialog.show();


            }
        });








        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email, final String password, final String company) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        //String company = user.getString("company");
                        String created_at = user.getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email,/* company,*/ uid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("company", company);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

