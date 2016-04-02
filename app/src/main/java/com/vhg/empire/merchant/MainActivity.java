package com.vhg.empire.merchant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.vhg.empire.merchant.dialogs.SweetAlertDialog;
import com.vhg.empire.merchant.newproduct.ViewPagerAdapter;
import com.vhg.empire.merchant.search.Search;
import com.vhg.empire.merchant.search.fab.Fab;
import com.vhg.empire.merchant.search.fab.MaterialSheetFab;
import com.vhg.empire.merchant.search.fab.MaterialSheetFabEventListener;
import com.vhg.empire.merchant.search.scanner.FullScannerFragment;
import com.vhg.empire.merchant.login.LoginActivity;
import com.vhg.empire.merchant.login.SQLiteHandler;
import com.vhg.empire.merchant.login.SessionManager;

import com.vhg.empire.merchant.styling.SlidingTabLayout;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private int statusBarColor;


    // Declaring Your View and Variables
    ViewPager pager;
    ViewPagerAdapter mViewPagerAdapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Orders","Cart"};
    int Numboftabs =2;
    private SQLiteHandler db;
    private SessionManager session;
    private MaterialSheetFab materialSheetFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        setupActionBar();

        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mViewPagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(mViewPagerAdapter);
       // updatePage(pager.getCurrentItem());

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.text_black_87);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updatePage(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ////////////////////LOGOUT////////////////////////////////////////


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        setupFab();
    }

    /**
     * Sets up the action bar.
     */
    private void setupActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_redb));
        final android.support.v7.app.ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setTitle("Merchant");
        ab.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }


    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }




    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.logout){
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setCustomImage(R.mipmap.ic_launcher)
                    .setTitleText("Merchant")
                    .setContentText("Are you sure you want to logout?")
                    .setCancelText("No,cancel please!")
                    .setConfirmText("Logout!")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // reuse previous dialog instance, keep widget user state, reset them if you need
                            sDialog.setTitleText("Merchant")
                                    .setContentText("You have cancelled the logout")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            sDialog.dismiss();

                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            logoutUser();
                        }
                    })
                    .show();

        }

        return true;
    }

    private void setupFab() {

        Fab fab = (Fab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.background_card);
        int fabColor = getResources().getColor(R.color.accent);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(getResources().getColor(R.color.tabsScrollColor));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });

        TextView search = (TextView) findViewById(R.id.sheet_search);
        if (search != null) {
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent search = new Intent(getApplicationContext(), Search.class);
                    startActivity(search);
                    finish();
                }
            });
        }

        // Set material sheet item click listeners
        TextView scan = (TextView) findViewById(R.id.sheet_scan);
        if (scan != null) {
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent scan = new Intent(getApplicationContext(), FullScannerFragment.class);
                    startActivity(scan);
                }
            });
        }
        findViewById(R.id.sheet_scan).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_note).setOnClickListener(this);
         }

    /**
     * Called when the selected page changes.
     *
     * @param selectedPage selected page
*/
    private void updatePage(int selectedPage) {
        updateFab(selectedPage);
    }

    /**
     * Updates the FAB based on the selected page
     *
     * @param selectedPage selected page
*/
    private void updateFab(int selectedPage) {
        switch (selectedPage) {
            case 0:
                materialSheetFab.showFab();
                break;
            default:
                materialSheetFab.hideSheetThenFab();
                break;
        }
    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }


    @Override
    public void onClick(View v) {

    }

}