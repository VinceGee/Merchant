package com.vhg.empire.merchant.newproduct.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vhg.empire.merchant.AppController;
import com.vhg.empire.merchant.R;
import com.vhg.empire.merchant.product.Product;
import com.vhg.empire.merchant.product.ShoppingCartHelper;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by maditsha on 3/9/2016.
 */
public class CartListAdapter extends BaseAdapter {
    private List<Product> productList = new ArrayList<>();
    private Activity activity;
    private LayoutInflater inflater;
    private boolean mShowQuantity;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public CartListAdapter(Activity activity, List<Product> feedItems,boolean showQuantity) {
        this.activity = activity;
        this.productList = feedItems;
        mShowQuantity = showQuantity;

    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int location) {
        return productList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_cart, null);
            item = new ViewItem();

//            item.productImageView = (ImageView) convertView
//                    .findViewById(R.id.ImageViewItem);

            item.productTitle = (TextView) convertView
                    .findViewById(R.id.cTextViewItem);

            item.productQuantity = (TextView) convertView
                    .findViewById(R.id.ctextViewQuantity);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        Product curProduct = productList.get(position);

        item.image = (NetworkImageView) convertView
                .findViewById(R.id.cImageViewItem);

        // user profile pic
        item.image.setImageUrl(curProduct.getProductImage(), imageLoader);

        //  item.productImageView.setImageDrawable(curProduct.productImage);
        item.productTitle.setText(curProduct.title);

        // Show the quantity in the cart or not
        if (mShowQuantity) {
            item.productQuantity.setText("Quantity: "
                    + curProduct.getQuantity());//ShoppingCartHelper.getProductQuantity(curProduct
        } else {
            // Hid the view
            item.productQuantity.setVisibility(View.GONE);
        }

        return convertView;

       /* if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item_cart, null);
        TextView name = (TextView) convertView.findViewById(R.id.cProName);
        TextView price = (TextView) convertView.findViewById(R.id.cPrice);
       // TextView quantity = (TextView) convertView.findViewById(R.id.cQnty);
        name.setText(productList.get(position).getName());
        //quantity.setText("Qnty: 1 * "+productList.get(position).getQuantity());
        price.setText("$ "+ productList.get(position).getPrice()+" * "+ productList.get(position).getQuantity());
        //image.setImageUrl(product.getImage(), imageLoader);
        return convertView;*/
    }
    private class ViewItem {
        NetworkImageView image;
        TextView productTitle;
        TextView productQuantity;
    }

}
