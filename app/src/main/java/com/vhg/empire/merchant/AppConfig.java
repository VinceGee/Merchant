package com.vhg.empire.merchant;

public class AppConfig {

    // Server base url
	public static final String BASE_URL = "http://10.41.100.190/merchant";

    // Server user login url
	public static final String URL_LOGIN = BASE_URL + "/login.php";

    // Server user register url
	public static final String URL_REGISTER = BASE_URL + "/register.php";

    // Server user place order url
	public static final String URL_INSERT = BASE_URL + "/inserting.php";

    //Sever address to search for products
	public static final String URL_SEARCH = BASE_URL + "/search.php";

    //Sever address to fetch products
	public static final String URL_PRODUCTS = BASE_URL + "/v1/products";


}
