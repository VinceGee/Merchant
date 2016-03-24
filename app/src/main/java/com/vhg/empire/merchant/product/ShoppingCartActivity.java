package com.vhg.empire.merchant.product;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vhg.empire.merchant.MainActivity;
import com.vhg.empire.merchant.R;
import com.vhg.empire.merchant.login.AppConfig;
import com.vhg.empire.merchant.login.SQLiteHandler;
import com.vhg.empire.merchant.login.SessionManager;
import com.vhg.empire.merchant.search.helper.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartActivity extends Activity {
    private static final String TAG = ShoppingCartActivity.class.getSimpleName();

    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;
    private Button mCheckOut;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);

        mCheckOut = (Button) findViewById(R.id.checkOutButton);


        mCartList = ShoppingCartHelper.getCartList();
       /* int productIndex = getIntent().getExtras().getInt(
                ShoppingCartHelper.PRODUCT_INDEX);
        final Product selectedProduct = mCartList.get(productIndex);*/

        // Make sure to clear the selections
        for (int i = 0; i < mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }


        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true);
        listViewCatalog.setAdapter(mProductAdapter);

        listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent productDetailsIntent = new Intent(getBaseContext(), ProductDetailsActivity.class);
                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                startActivity(productDetailsIntent);
            }
        });

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        mCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                String pname = /*selectedProduct.title;*/ mCartList.get(0).title;
                String pdesc = mCartList.get(0).description;
                String qnty = ""+ProductDetailsActivity.mTotalQuantity;

                if (!pname.isEmpty() && !pdesc.isEmpty() && !qnty.isEmpty()) {
                    insertIntoDB(pname, pdesc, qnty);
                    Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Cart must not be empty!", Toast.LENGTH_LONG)
                            .show();
                }



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the data
        if (mProductAdapter != null) {
            mProductAdapter.notifyDataSetChanged();
        }

        double subTotal = 0;
        for (Product p : mCartList) {
            int quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.price * quantity;
        }

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
        productPriceTextView.setText("Subtotal: $" + subTotal);
    }



    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void insertIntoDB(final String pname, final String pdesc, final String qnty) {
        // Tag used to cancel the request
        String tag_string_req = "req_insert";

        pDialog.setMessage("Placing Order...");
       // showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_INSERT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Insert Response: " + response.toString());
              //  hideDialog();
                Toast.makeText(getApplicationContext(), "Order has been placed successfully!", Toast.LENGTH_LONG).show();
             /*   try {
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


                    } else {



                        Toast.makeText(getApplicationContext(), "Order has been placed successfully!", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Order Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
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

    /*private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if ((this.pDialog != null) && this.pDialog.isShowing()) {
            this.pDialog.dismiss();
        }
       *//* if (pDialog.isShowing())
            pDialog.dismiss();*//*
    }*/
}