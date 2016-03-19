package com.vhg.empire.merchant.Account;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vhg.empire.merchant.R;

import java.util.ArrayList;
import java.util.List;
/*import butterknife.Bind;*/

/**
 * Created by VinceGee on 10/31/2015.
 */
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.RadioViewHolder>{
    private Context context;
    List<AccountMenu> menuitems;

   /* public static class AccountViewHolder extends RecyclerView.ViewHolder {
      *//*  CardView cv;
        TextView account_set_title;
        TextView account_set_sub_title;
        ImageView account_set_icon;*//*

        *//*AccountViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            account_set_title = (TextView)itemView.findViewById(R.id.account_set_title);
            account_set_sub_title = (TextView)itemView.findViewById(R.id.account_set_sub_title);
            account_set_icon = (ImageView)itemView.findViewById(R.id.account_set_icon);
        }*//*
    }
*/
   public AccountAdapter(Context context, List<AccountMenu> menuitems) {
       this.context = context;
       this.menuitems = new ArrayList<>(menuitems);
   }


   /* AccountAdapter(List<AccountMenu> menuitems){
        this.menuitems = menuitems;
    }*/

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_settings, viewGroup, false);
        RadioViewHolder pvh = new RadioViewHolder(v);
        return pvh;
    }
   /* @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card, parent, false);
        RadioViewHolder viewHolder = new RadioViewHolder(view);
        return viewHolder;
    }*/

    @Override
    public void onBindViewHolder(RadioViewHolder accountViewHolder, int i) {
        accountViewHolder.account_set_title.setText(menuitems.get(i).menu_title);
        accountViewHolder.account_set_sub_title.setText(menuitems.get(i).menu_sub_title);
        accountViewHolder.account_set_icon.setImageResource(menuitems.get(i).icon);
    }

    @Override
    public int getItemCount() {
        return menuitems.size();
    }
    public class RadioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView account_set_title;
        TextView account_set_sub_title;
        ImageView account_set_icon;

        private List<AccountMenu> mValues;
        private String className;
        /*List<AccountMenu> menuitems;*/

        public RadioViewHolder(View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            account_set_title = (TextView)itemView.findViewById(R.id.account_set_title);
            account_set_sub_title = (TextView)itemView.findViewById(R.id.account_set_sub_title);
            account_set_icon = (ImageView)itemView.findViewById(R.id.account_set_icon);
            itemView.setOnClickListener(this);

        }


       /* public Safety getValueAt(int position) {
            return mValues.get(position);
        }*/


        @Override
        public void onClick(View v) {


            Context context = v.getContext();
            switch (getAdapterPosition()){
                case 0:
                    Intent intent0 = new Intent(context, HistoryActivity.class);

                    context.startActivity(intent0);
                    break;
                case 1:
                    Intent intent1 = new Intent(context, ChangePassword.class);

                    context.startActivity(intent1);
                    break;
                default:
                    break;
            }

           /* Intent intent = new Intent(context, HistoryActivity.class);
           *//* intent.putExtra("_TITLE", safety.getSafety().getName());
            intent.putExtra("_DESCRIPTION", safety.getSafety().getDescription());
            intent.putExtra("_OVERVIEWIMAGE", safety.getSafety().getOverViewImage());*//*
            context.startActivity(intent);*/
        }
    }

}
