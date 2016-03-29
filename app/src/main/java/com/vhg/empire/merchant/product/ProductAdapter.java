package com.vhg.empire.merchant.product;

/**
 * Created by maditsha on 3/14/2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vhg.empire.merchant.AppController;
import com.vhg.empire.merchant.R;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private static List<Product> mProductList;
    private LayoutInflater mInflater;
    private boolean mShowQuantity;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ProductAdapter(Activity activity, List<Product> list, LayoutInflater inflater, boolean showQuantity) {
        mProductList = list;
        mInflater = inflater;
        mShowQuantity = showQuantity;
    }

    public static List<Product> getProductList(){
        return mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item, null);
            item = new ViewItem();

//            item.productImageView = (ImageView) convertView
//                    .findViewById(R.id.ImageViewItem);

            item.productTitle = (TextView) convertView
                    .findViewById(R.id.TextViewItem);

            item.productQuantity = (TextView) convertView
                    .findViewById(R.id.textViewQuantity);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        Product curProduct = mProductList.get(position);

        item.image = (NetworkImageView) convertView
                .findViewById(R.id.ImageViewItem);

        // user profile pic
        item.image.setImageUrl(curProduct.getProductImage(), imageLoader);

      //  item.productImageView.setImageDrawable(curProduct.productImage);
        item.productTitle.setText(curProduct.title);

        // Show the quantity in the cart or not
        if (mShowQuantity) {
            item.productQuantity.setText("Quantity: "
                    + ShoppingCartHelper.getProductQuantity(curProduct));
        } else {
            // Hid the view
            item.productQuantity.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewItem {
        NetworkImageView image;
        TextView productTitle;
        TextView productQuantity;
    }

}
