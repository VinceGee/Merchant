package com.vhg.empire.merchant.Cart;

/**
 * Created by maditsha on 10/23/2015.
 */


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.vhg.empire.merchant.R;

import java.util.ArrayList;
import java.util.List;

public class CardViewFragment extends Fragment {

    private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Cart_properties> studentList;

    private Button btnSelection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cart_activity, container, false);
       // toolbar = (Toolbar) view.findViewById(R.id.cart_toolbar);
        //btnSelection = (Button) view.findViewById(R.id.btnShow);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btnShow);



        studentList = new ArrayList<Cart_properties>();

        /*for (int i = 1; i <= 15; i++) {
            Student st = new Student("Mazowe " + i, "Cream Soda " + i
                    + "", false);

            studentList.add(st);
        }*/
        studentList = new AddCartItems().getCartItems();


        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // create an Object for Adapter
        mAdapter = new CardViewDataAdapter(studentList);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data ="";
                List<Cart_properties> stList = ((CardViewDataAdapter) mAdapter)
                        .getStudentist();

                for (int i = 0; i < stList.size(); i++) {
                    Cart_properties singleStudent = stList.get(i);
                    if (singleStudent.isSelected() == true) {

                        data = data + "\n" + singleStudent.getName().toString();
                        stList.remove(i);

                    }


                }
                mAdapter.notifyDataSetChanged();
                if (data != null &&  !data.trim().isEmpty()  ) {
                    Toast.makeText(view.getContext(),
                            "Moved to Trash: \n" + data, Toast.LENGTH_SHORT)
                            .show();

                }/* else {
                    Toast.makeText(view.getContext(),
                            " Nothing selected !!!  \n" + data, Toast.LENGTH_SHORT)
                            .show();
                }*/


            }
        });
        return view;



        /*btnSelection.setOnClickListener(new OnClickListener() {




        return view;*/
    }



}
