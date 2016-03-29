package com.vhg.empire.merchant;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

/**
 * Created by VinceGee on 1/25/2016.
 */
public class Config {



        // PayPal app configuration
        public static final String PAYPAL_CLIENT_ID = "AcH6Jak-xJ5mPm-x02qczkG_8nK3F-DHjeDEkZwH36i5GnkTX4qxTcsFz5hfw_ZLzYll51reFeLYTaCv";
        public static final String PAYPAL_CLIENT_SECRET = "EGu54LWzbTxtKyBBcviVrJNjZ0PCIX1Pnb9Xw4ylS1kbblvgjqGiC50tKhP2lucKpYlkFf7wmTEC8sYF";

        public static final String PAYPAL_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
        public static final String PAYMENT_INTENT = PayPalPayment.PAYMENT_INTENT_SALE;
        public static final String DEFAULT_CURRENCY = "USD";

        // PayPal server urls
        public static final String URL_PRODUCTS = "http://10.41.100.190/merchant/v1/products";
        public static final String URL_VERIFY_PAYMENT = "http://10.41.100.190/merchant/v1/verifyPayment";



}
