package com.vhg.empire.merchant.newproduct.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vhg.empire.merchant.AppConfig;
import com.vhg.empire.merchant.AppController;
import com.vhg.empire.merchant.R;
import com.vhg.empire.merchant.login.SQLiteHandler;
import com.vhg.empire.merchant.login.SessionManager;
import com.vhg.empire.merchant.login.SignupActivity;
import com.vhg.empire.merchant.product.Product;
import com.vhg.empire.merchant.product.ProductAdapter;
import com.vhg.empire.merchant.product.ProductDetailsActivity;
import com.vhg.empire.merchant.product.ShoppingCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment  {

    private static final int FRAGMENT_CONTAINER = R.id
            .pager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //  private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PAYMENT = 1;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private Button btnCheckout;

    private SessionManager session;
    private SQLiteHandler db;
    public String username;


    // To store all the products
    private List<Product> productsList;
    private ProductAdapter adapter;
    // Progress dialog
    private ProgressDialog pDialog;

    private OnFragmentInteractionListener mListener;
    private int itemQuantity = 1;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        // Inflate the layout for this fragment

        listView = (ListView) view.findViewById(R.id.list);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        productsList = new ArrayList<Product>();
        //   adapter = new ProductListAdapter(getActivity(), productsList, this);
        adapter = new ProductAdapter(getActivity(), productsList, getActivity().getLayoutInflater(), false);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent productDetailsIntent = new Intent(getActivity(),ProductDetailsActivity.class);
                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                startActivity(productDetailsIntent);
            }
        });


        // Fetching products from server
        fetchProducts();


        // Session manager
        session = new SessionManager(getActivity());

        // SQLite database handler
        db = new SQLiteHandler(getActivity());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        username = user.get("name");

        return view;
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void fetchProducts() {
        // Showing progress dialog before making request

        pDialog.setMessage("Fetching products...");

        showpDialog();

        //getting shared preferences category
       // SignupActivity.pref = getActivity().getPreferences(SignupActivity.REFERENCE_MODE_PRIVATE);

    //     String prefCategory = SignupActivity.getDefaults("category",getActivity());
       // String prefCategory = SignupActivity.pref.getString("category","NO VALUE");

       //Log.e("Preference name",prefCategory);

//        Toast.makeText(SignupActivity.this,
//                pref.getString("category","NO VALUE"), Toast.LENGTH_SHORT).show();

        //
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("category","Laptops" );
        params.put("username", username);



        //making a request
        JSONObject requestObject = new JSONObject(params);

        // Making json object request
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, AppConfig.URL_PRODUCTS, requestObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("This ProductFragment", response.toString());

                Resources res = ProductFragment.this.getResources();

                try {
                    JSONArray products = response.getJSONArray("products");

                    // looping through all product nodes and storing
                    // them in array list
                    for (int i = 0; i < products.length(); i++) {

                        JSONObject product = (JSONObject) products.get(i);

                        String id = product.getString("id");
                        String name = product.getString("name");
                        String description = product.getString("description");
                        String image = product.getString("image");
                        double price = Double.parseDouble(product.getString("price"));
                        String sku = product.getString("sku");

                        Product p = new Product(id, name,image/*res.getDrawable(R.drawable.deadoralive)*/, description, price);

                        productsList.add(p);
                    }

                    // notifying adapter about data changes, so that the
                    // list renders with new data
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                // hiding the progress dialog
                hidepDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ProductFragment", "Error: " + error.getMessage());

                Toast.makeText(getActivity(),"Merchant could not connect to the server. Please try again later.", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
