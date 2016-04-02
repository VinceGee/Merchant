package com.vhg.empire.merchant.product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.vhg.empire.merchant.AppConfig;
import com.vhg.empire.merchant.AppController;
import com.vhg.empire.merchant.MainActivity;
import com.vhg.empire.merchant.R;
import com.vhg.empire.merchant.dialogs.SweetAlertDialog;
import com.vhg.empire.merchant.login.SQLiteHandler;
import com.vhg.empire.merchant.login.SessionManager;
import com.vhg.empire.merchant.newproduct.helper.CartListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartFragment extends Fragment {
    private static final String TAG = ShoppingCartFragment.class.getSimpleName();

    private List<Product> mCartList;
    private CartListAdapter mProductAdapter;
    private Button mCheckOut;

    private int i = -1;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    public static ListView mListViewCatalog;

    public String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.shoppingcart);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shoppingcart, container, false);
        mCheckOut = (Button) view.findViewById(R.id.checkOutButton);


        mCartList = ProductDetailsActivity.productsInCart;
        Log.e("Check this size", "" + mCartList.size());
        //ShoppingCartHelper.getCartList();
       /* int productIndex = getIntent().getExtras().getInt(
                ShoppingCartHelper.PRODUCT_INDEX);
        final Product selectedProduct = mCartList.get(productIndex);*/

        // Make sure to clear the selections
        for (int i = 0; i < mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }


        // Create the list
        mListViewCatalog = (ListView) view.findViewById(R.id.listViewCatalog);
        mProductAdapter = new CartListAdapter(getActivity(), mCartList, true);
        mListViewCatalog.setAdapter(mProductAdapter);

        mListViewCatalog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // int prodPos = mCartList.get(position).getPos();
                Intent productDetailsIntent = new Intent(getContext(), CartDetailsActivity.class);
                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                productDetailsIntent.putExtra("currQuantity", mCartList.get(position).getQuantity());
                startActivity(productDetailsIntent);
            }
        });

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);


        // Session manager
        session = new SessionManager(getActivity());

        // SQLite database handler
        db = new SQLiteHandler(getActivity());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        username = user.get("name");

        mCheckOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setCustomImage(R.mipmap.ic_launcher)
                        .setTitleText("Merchant")
                        .setContentText("Submit Order?")
                        .setCancelText("No,wait!")
                        .setConfirmText("Submit!")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance, keep widget user state, reset them if you need
                                sDialog.setTitleText("Merchant")
                                        .setContentText("Keep making orders")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                if (mCartList.size() != 0) {


                                    for (int i = 0; i < mCartList.size(); i++) {
                                        String pname = mCartList.get(i).title;
                                        String pdesc = mCartList.get(i).description;
                                        String qnty = "" + mCartList.get(i).getQuantity();
                                       // String username = username.trim();
                                        if (!pname.isEmpty() && !pdesc.isEmpty() && !qnty.isEmpty()) {
                                            insertIntoDB(pname, pdesc, qnty);
                                        } else {
                                            Toast.makeText(getActivity(), "Cart must not be empty!", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                } else {
                                    Toast.makeText(getActivity(), "Cart must not be empty!", Toast.LENGTH_SHORT).show();
                                }
                                sDialog.dismiss();
                            }


                        })

                        .show();
            }

        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the data
        if (mProductAdapter != null) {
            //mProductAdapter = new ProductAdapter(getActivity(),mCartList,getActivity().getLayoutInflater(), true);
            mProductAdapter.notifyDataSetChanged();
        }

        double subTotal = 0;
        for (Product p : mCartList) {
            int quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.price * quantity;
        }

        TextView productPriceTextView = (TextView) getActivity().findViewById(R.id.TextViewSubtotal);
        productPriceTextView.setText("Total Items In Cart :" + mCartList.size());
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private void insertIntoDB(final String pname, final String pdesc, final String qnty) {
        // Tag used to cancel the request
        String tag_string_req = "req_insert";

        pDialog.setMessage("Placing Order...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_INSERT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e(TAG, "Insert Response: " + response.toString());
                hideDialog();
                for (int i = 0; i < mCartList.size(); i++) {
                    mCartList.remove(i);
                }
                Toast.makeText(getActivity(), "Order has been placed successfully!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
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
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to insert url
                Map<String, String> params = new HashMap<String, String>();

                params.put("pname", pname);
                params.put("pdesc", pdesc);
                params.put("qnty", qnty);
                params.put("username",username);

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
        if ((this.pDialog != null) && this.pDialog.isShowing()) {
            this.pDialog.dismiss();
        }
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}