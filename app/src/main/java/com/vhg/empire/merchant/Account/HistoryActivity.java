package com.vhg.empire.merchant.Account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vhg.empire.merchant.Account.tablefixheaders.TableFixHeaders;
import com.vhg.empire.merchant.Account.tablefixheaders.adapters.MatrixTableAdapter;
import com.vhg.empire.merchant.R;

/**
 * Created by VinceGee on 10/28/2015.
 */
public class HistoryActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hist_act);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setTitle("Merchant");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table_hist_act);
        MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<String>(this, new String[][] {
                {
                        "Date","Reference", "Description", "Allocated to", "Debit", "Credit", "Closing Balance"
                },
                {
                        "2015-8-21", "1515875456", "ACCOMODATION FEE", "", "", "400","400 CR" },
                {

                        "2015-8-21", "INV000119077", "ACCOMODATION FEE", "", "400", "", "400 DR" },
                {

                        "2015-9-01", "1527717889", "TUITION", "", "", "603", "603 CR" },
                {

                        "2015-10-07", "INV000008911", "TUITION", "", "603", "", "0CR" },
                {
                        "2015-10-23", "", "BALANCE", "", "", "", "0DR" }
        });
        tableFixHeaders.setAdapter(matrixTableAdapter);
    }


}
