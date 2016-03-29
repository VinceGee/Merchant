package com.vhg.empire.merchant.product;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.paypal.android.sdk.payments.PayPalItem;
import com.vhg.empire.merchant.AppController;
import com.vhg.empire.merchant.R;
import com.vhg.empire.merchant.newproduct.fragment.ProductFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends Activity {
    private int itemQuantity = 0;
    public static int mTotalQuantity;

    // To store the products those are added to cart
    public static List<Product> productsInCart = new ArrayList<Product>();

    //@Bind(R.id.checkout_fragment_item_details_text_view_item_quantity)
    TextView mTextViewItemQuantity;
    Button onButtonPlus;
    Button onButtonMinus;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    //@OnClick(R.id.checkout_fragment_item_details_button_plus)
    public void onButtonPlusClick(View view) {
        itemQuantity++;
        mTextViewItemQuantity.setText(itemQuantity + "");
    }

    // @OnClick(R.id.checkout_fragment_item_details_button_minus)
    public void onButtonMinusClick(View view) {
        itemQuantity--;
        mTextViewItemQuantity.setText(itemQuantity + "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetails);

        mTextViewItemQuantity = (TextView) findViewById(R.id.checkout_fragment_item_details_text_view_item_quantity);
        onButtonPlus = (Button) findViewById(R.id.checkout_fragment_item_details_button_plus);
        onButtonMinus = (Button) findViewById(R.id.checkout_fragment_item_details_button_minus);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

       // ProductFragment.productsInCart
      //  List<Product> catalog = ShoppingCartHelper.getCatalog(getResources());

        final int productIndex = getIntent().getExtras().getInt(
                ShoppingCartHelper.PRODUCT_INDEX);
        final Product selectedProduct =ProductAdapter.getProductList().get(productIndex);
        //final Product selectedProduct = catalog.get(productIndex);

        // Set the proper image and text
        NetworkImageView image = (NetworkImageView)findViewById(R.id.productImage);

        // user profile pic
        image.setImageUrl(selectedProduct.getProductImage(), imageLoader);
       // ImageView productImageView = (ImageView) findViewById(R.id.ImageViewProduct);
       // productImageView.setImageDrawable(selectedProduct.productImage);


        TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);
        productTitleTextView.setText(selectedProduct.title);
        TextView productDetailsTextView = (TextView) findViewById(R.id.TextViewProductDetails);
        productDetailsTextView.setText(selectedProduct.description);

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewProductPrice);
        productPriceTextView.setText("$" + selectedProduct.price);

        mTextViewItemQuantity.setText(itemQuantity + "");
        onButtonPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPlusClick(v);
            }
        });

        onButtonMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonMinusClick(v);
            }
        });

        // Update the current quantity in the cart
        TextView textViewCurrentQuantity = (TextView) findViewById(R.id.textViewCurrentlyInCart);
        textViewCurrentQuantity.setText("Currently in Cart: "
                + ShoppingCartHelper.getProductQuantity(selectedProduct));


        // Save a reference to the quantity edit text
        // final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);

        Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
        addToCartButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // Check to see that a valid quantity was entered
                int quantity = 0;
                try {
                    quantity = itemQuantity;

                    if (quantity < 0) {
                        Toast.makeText(getBaseContext(),
                                "Please enter a quantity of 0 or higher",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }


                } catch (Exception e) {

                    Toast.makeText(getBaseContext(),
                            "Please enter a numeric quantity",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                // If we make it here, a valid quantity was entered
                ShoppingCartHelper.setQuantity(selectedProduct, quantity);
                mTotalQuantity = quantity;
                //add products to cart
                Product productInCrt = new Product(selectedProduct.getId(),selectedProduct.getTitle(),selectedProduct.getProductImage()
                ,selectedProduct.getDescription(),selectedProduct.getPrice(),productIndex,mTotalQuantity);
                productsInCart.add(productInCrt);
                ((BaseAdapter) ShoppingCartActivity.listViewCatalog.getAdapter()).notifyDataSetChanged();
                Log.e("SHOW product szie",productsInCart.size()+"");

                // Close the activity

                finish();
            }
        });

    }

}