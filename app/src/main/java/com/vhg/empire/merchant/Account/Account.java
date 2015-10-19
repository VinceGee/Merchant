package com.vhg.empire.merchant.Account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vhg.empire.merchant.R;

/**
 * Created by VinceGee on 9/11/2015.
 */
public class Account extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.account_tab,container,false);
        return v;
    }
}
