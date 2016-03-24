package com.vhg.empire.merchant.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.vhg.empire.merchant.R;


/**
 * Created by VinceGee on 03/16/2016.
 */
public class Search extends Fragment implements View.OnClickListener{
    private EditText mTxtkeyword;
    private Button mBtnsearch;
    private Button mScan;
    private Button insertbtn;
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
        mTxtkeyword =(EditText)view.findViewById(R.id.txtkeyword);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mBtnsearch =(Button)view.findViewById(R.id.btnsearch);
        mScan =(Button)view.findViewById(R.id.scan);
        mBtnsearch.setOnClickListener(this);
        mScan.setOnClickListener(this);

//        insertbtn=(Button)view.findViewById(R.id.insertbtn);
//        insertbtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnsearch){
            Intent searchIntent = new Intent(getActivity(), ListResult.class);
            //send the keyword to the next screen
            searchIntent.putExtra("keyword", mTxtkeyword.getText().toString());
            //call the screen for listing
            startActivity(searchIntent);
        }else if(v.getId()==R.id.scan){
            firstPageListener.onSwitchToNextFragment();


        }/*else if(v.getId()==R.id.insertbtn){
            Intent insert = new Intent(getActivity(), InsertClass.class);
            startActivity(insert);
        }*/

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }*/

}

