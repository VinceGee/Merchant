package com.vhg.empire.merchant.Cart.search.scanner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vhg.empire.merchant.FirstPageFragmentListener;
import com.vhg.empire.merchant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextingFragment extends Fragment {

    static FirstPageFragmentListener firstPageListener;

    public static TextingFragment newInstance(){
        return new TextingFragment();
    }


    public TextingFragment() {
        // Required empty public constructor
    }
   /* public TextingFragment(FirstPageFragmentListener listener) {
        firstPageListener = listener;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_texting, container, false);
    }
    public void backPressed() {
        firstPageListener.onSwitchToNextFragment();
    }
}
