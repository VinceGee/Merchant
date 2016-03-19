package com.vhg.empire.merchant.Account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vhg.empire.merchant.styling.DividerItemDecoration;
import com.vhg.empire.merchant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VinceGee on 10/28/2015.
 */

public class AccountFragment extends Fragment{

    private List<AccountMenu> menuitems;
    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recyclerview_frag, container, false);

        rv = (RecyclerView) view.findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL_LIST);
        rv.addItemDecoration(itemDecoration);

        initializeData();
        initializeAdapter();

        return view;
    }

    private void initializeData(){
        menuitems = new ArrayList<>();
        menuitems.add(new AccountMenu("History", "View Your Account History", R.drawable.history));
        menuitems.add(new AccountMenu("Change Password", "Change your Merchant account password", R.drawable.password));
    }

    private void initializeAdapter(){
        AccountAdapter adapter = new AccountAdapter(getContext(),menuitems);
        rv.setAdapter(adapter);
    }

}

/*public class AccountFragment extends Fragment{

    private List<AccountMenu> menuitems;
    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recyclerview_frag, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        initializeData();
        initializeAdapter();

        return view;
    }

    private void initializeData(){
        menuitems = new ArrayList<>();
        menuitems.add(new AccountMenu("History", "View Your Account History", R.drawable.history));
        menuitems.add(new AccountMenu("Change Password", "Change your Merchant account password", R.drawable.password));
    }

    private void initializeAdapter(){
        AccountAdapter adapter = new AccountAdapter(menuitems);
        rv.setAdapter(adapter);
    }
}*/



/*
public class AccountFragment extends Fragment {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getActivity().getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new SettingsFragment())
                    .commit();
        }
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
        }
    }
}
*/






/*
public class AccountFragment extends Fragment implements View.OnClickListener {

    private Button historyButton;
    private Button passwdButton;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_tab, container, false);
        historyButton = (Button) view.findViewById(R.id.btn_history);
        historyButton.setOnClickListener(this);

        passwdButton = (Button) view.findViewById(R.id.btn_password);
        passwdButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.btn_history:
                Intent history = new Intent(getActivity(), HistoryActivity.class);
                startActivity(history);
                break;
            case R.id.btn_password:
                Intent password = new Intent(getActivity(), ChangePassword.class);
                startActivity(password);
                break;

        }
    }

}
*/
