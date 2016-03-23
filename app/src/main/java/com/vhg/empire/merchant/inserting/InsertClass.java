package com.vhg.empire.merchant.inserting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vhg.empire.merchant.MainActivity;
import com.vhg.empire.merchant.R;
import com.vhg.empire.merchant.login.AppConfig;
import com.vhg.empire.merchant.login.LoginActivity;
import com.vhg.empire.merchant.login.SQLiteHandler;
import com.vhg.empire.merchant.login.SessionManager;
import com.vhg.empire.merchant.search.helper.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

/**
 * Created by VinceGee on 03/22/2016.
 */
public class InsertClass extends Activity {
    private static final String TAG = InsertClass.class.getSimpleName();
    EditText inputPname;
    EditText inputPdesc;
    EditText inputQnty;
    Button btnOrder;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_class);
        //ButterKnife.inject(this);

        inputPname = (EditText) findViewById(R.id.input_pname);
        inputPdesc = (EditText) findViewById(R.id.input_pdesc);
        inputQnty = (EditText) findViewById(R.id.input_qnty);
        btnOrder = (Button) findViewById(R.id.btnOrder);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());


        // Register Button Click event
        btnOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String pname = inputPname.getText().toString().trim();
                String pdesc = inputPdesc.getText().toString().trim();
                String qnty = inputQnty.getText().toString().trim();

                if (!pname.isEmpty() && !pdesc.isEmpty() && !qnty.isEmpty()) {
                    insertIntoDB(pname, pdesc, qnty);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "You have to fill in all details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void insertIntoDB(final String pname, final String pdesc, final String qnty) {
        // Tag used to cancel the request
        String tag_string_req = "req_insert";

        pDialog.setMessage("Placing Order...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_INSERT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Insert Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String order_id = jObj.getString("order_id");

                        JSONObject product = jObj.getJSONObject("product");
                        String pname = product.getString("pname");
                        String pdesc = product.getString("pdesc");
                        String qnty = product.getString("qnty");

                        // Inserting row in users table
                        db.addUser(order_id,pname, pdesc,qnty);

                        Toast.makeText(getApplicationContext(), "Order has been placed successfully!", Toast.LENGTH_LONG).show();

                        /*// Launch login activity
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();*/
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
                Log.e(TAG, "Order Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pname", pname);
                params.put("pdesc", pdesc);
                params.put("qnty", qnty);

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

