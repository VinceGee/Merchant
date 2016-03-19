package com.vhg.empire.merchant.Cart;

/**
 * Created by maditsha on 10/23/2015.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.vhg.empire.merchant.R;

import java.util.List;

public class CardViewDataAdapter extends
        RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {

    private List<Cart_properties> stList;

    public CardViewDataAdapter(List<Cart_properties> students) {
        this.stList = students;

    }

    // Create new views
    @Override
    public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cardview_row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final int pos = position;

        viewHolder.tvName.setText(stList.get(position).getName());

        viewHolder.tvEmailId.setText(stList.get(position).getDesc());

        viewHolder.tvQnty.setText("\tQnty : "+stList.get(position).getQnty());

        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

        viewHolder.chkSelected.setTag(stList.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Cart_properties contact = (Cart_properties) cb.getTag();

                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());

                Toast.makeText(
                        v.getContext(),
                        "Clicked: " + stList.get(position).getName()
                                , Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
       int items =  stList == null ? 0 : stList.size();

        return items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmailId;
        public TextView tvQnty;

        public CheckBox chkSelected;


        public Cart_properties singlestudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView.findViewById(R.id.tvName);

            tvEmailId = (TextView) itemLayoutView.findViewById(R.id.tvEmailId);
            tvQnty = (TextView) itemLayoutView.findViewById(R.id.tvQnty);
            chkSelected = (CheckBox) itemLayoutView
                    .findViewById(R.id.chkSelected);

        }

    }

    // method to access in activity after updating selection
    public List<Cart_properties> getStudentist() {
        return stList;
    }

}
