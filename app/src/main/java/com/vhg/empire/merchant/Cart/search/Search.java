package com.vhg.empire.merchant.Cart.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vhg.empire.merchant.Cart.search.scanner.FullScannerFragment;
import com.vhg.empire.merchant.Cart.search.scanner.TextingFragment;
import com.vhg.empire.merchant.FirstPageFragmentListener;
import com.vhg.empire.merchant.R;

/**
 * Created by VinceGee on 03/16/2016.
 */
public class Search extends Fragment implements View.OnClickListener{
    private EditText txtkeyword;
    private Button btnsearch;
    private Button scan;
    static FirstPageFragmentListener firstPageListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static Search newInstance() {
        return new Search(firstPageListener);
    }
    public Search(FirstPageFragmentListener listener) {
        firstPageListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
        txtkeyword=(EditText)view.findViewById(R.id.txtkeyword);
        btnsearch=(Button)view.findViewById(R.id.btnsearch);
        scan=(Button)view.findViewById(R.id.scan);
        btnsearch.setOnClickListener(this);
        scan.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnsearch){
            Intent searchIntent = new Intent(getActivity(), ListResult.class);
            //send the keyword to the next screen
            searchIntent.putExtra("keyword",txtkeyword.getText().toString());
            //call the screen for listing
            startActivity(searchIntent);
        }else if(v.getId()==R.id.scan){
            firstPageListener.onSwitchToNextFragment();

            /*FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.replace(R.id.pager, TextingFragment.newInstance());
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            trans.addToBackStack(null);
            trans.commit();*/

            /*Intent searchIntent = new Intent(getActivity(), ListResult.class);
            //send the keyword to the next screen
            searchIntent.putExtra("keyword",txtkeyword.getText().toString());
            //call the screen for listing
            startActivity(searchIntent);*/
        }

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }*/

}

