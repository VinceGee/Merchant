package com.vhg.empire.merchant.Cart.search;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.vhg.empire.merchant.Cart.search.helper.AppController;
import com.vhg.empire.merchant.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by VinceGee on 03/16/2016.
 */
public class ListResult extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> idiomsList;

    // url to get the idiom list
    private static String url_search = "http://10.41.100.190/merchant/search.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_IDIOMS = "idioms";
/*    private static final String TAG_SUCCESS = "success";
    private static final String TAG_IDIOMS = "idioms";
   // private static final String TAG_ID = "id";
    private static final String TAG_ENTRY = "entry";
    private static final String TAG_MEANING = "meaning";*/

    private static final String TAG_ID = "id";
    private static final String TAG_BARCODE = "barcode";
    private static final String TAG_PNAME= "pname";
    private static final String TAG_PDESC = "pdesc";
    private static final String TAG_PPRICE = "pprice";
    private static final String TAG_IMAGE = "image";

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    // products JSONArray
    JSONArray idioms = null;
    //search key value
    public String searchkey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        Intent myIntent = getIntent();
        // gets the arguments from previously created intent
        searchkey = myIntent.getStringExtra("keyword");

        // Hashmap for ListView
        idiomsList = new ArrayList<HashMap<String, String>>();

        // Loading idioms in Background Thread
        new LoadIdioms().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single idioms
        // to do something
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String iid = ((TextView) view.findViewById(R.id.id)).getText()
                        .toString();

            }
        });

    }

    /**
     * Background Async Task to Load Idioms by making HTTP Request
     * */
    class LoadIdioms extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListResult.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Idioms from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //value captured from previous intent
            params.add(new BasicNameValuePair("keyword", searchkey));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_search, "GET", params);

            // Check your log cat for JSON response
//            Log.d("Search idioms: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    idioms = json.getJSONArray(TAG_IDIOMS);

                    // looping through All Products
                    for (int i = 0; i < idioms.length(); i++) {
                        JSONObject c = idioms.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String barcode = c.getString(TAG_BARCODE);
                        String name = c.getString(TAG_PNAME);
                        String desc = c.getString(TAG_PDESC);
                        String price = c.getString(TAG_PPRICE);
                        String image = c.getString(TAG_IMAGE);


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_BARCODE, barcode);
                        map.put(TAG_PNAME, name);
                        map.put(TAG_PDESC, desc);
                        map.put(TAG_PPRICE, price);
                        map.put(TAG_IMAGE, image);

                        // adding HashList to ArrayList
                        idiomsList.add(map);
                    }
                } else {
                    // no idioms found
                    //do something
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //return "success";
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting the related idioms
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            ListResult.this, idiomsList,
                            R.layout.list_view_yesearch, new String[] { TAG_ID, TAG_BARCODE, TAG_PNAME,TAG_PDESC,TAG_PPRICE,TAG_IMAGE},
                            new int[] { R.id.id, R.id.pname, R.id.pdesc,R.id.pprice, R.id.pimage});
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}
