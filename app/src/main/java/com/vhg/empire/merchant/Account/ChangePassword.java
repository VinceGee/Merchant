package com.vhg.empire.merchant.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vhg.empire.merchant.MainActivity;
import com.vhg.empire.merchant.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by VinceGee on 10/23/2015.
 */
public class ChangePassword extends AppCompatActivity {

    private static final String TAG = "ChangePassword";
    private static final int REQUEST_PASSWORD = 0;


    @InjectView(R.id.current_password)    EditText _curpasswordText;
    @InjectView(R.id.new_password)        EditText _newpasswordText;
    @InjectView(R.id.conf_password)       EditText _confpasswordText;
    @InjectView(R.id.btn_update_pwd)      Button _updateButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chnge_password);
        ButterKnife.inject(this);

        _updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_password);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setTitle("Merchant");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*mActionBar.setNavigationIcon(getResources().getDrawable(R.drawable.left));
        mActionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
*/
        /*final android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.left);
        ab.setTitle("E-Mhare");
        ab.setDisplayHomeAsUpEnabled(true);*/
    }

    public void login() {
        Log.d(TAG, "Update");
        if (!validate()) {
            onLoginFailed();
            return;
        }

        _updateButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Changing Password...");
        progressDialog.show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        String cur_passwd = _curpasswordText.getText().toString();
        String new_passwd = _newpasswordText.getText().toString();
        String conf_passwd = _confpasswordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        //progressDialog.dismiss();
                        Toast.makeText(getBaseContext(),"Password Updated successfully",Toast.LENGTH_LONG).show();
                    }
                }, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PASSWORD) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _updateButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Failed to update password", Toast.LENGTH_LONG).show();
        _updateButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String cur_passwd = _curpasswordText.getText().toString();
        String new_passwd = _newpasswordText.getText().toString();
        String conf_passwd = _confpasswordText.getText().toString();

        if (cur_passwd.isEmpty() || cur_passwd.length() < 4 || cur_passwd.length() > 10) {
            _curpasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _curpasswordText.setError(null);
        }

        if (new_passwd.isEmpty() || new_passwd.length() < 4 || new_passwd.length() > 10)
        {
            _newpasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _newpasswordText.setError(null);
        }

        if (conf_passwd.isEmpty() || conf_passwd.length() < 4 || conf_passwd.length() > 10)
        {
            _confpasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _confpasswordText.setError(null);
        }

        if (new_passwd.equals(conf_passwd)){
            finish();
        }
        else{
            _confpasswordText.setError("Password mismatch.");
            valid = false;
        }
        return valid;
    }

}
