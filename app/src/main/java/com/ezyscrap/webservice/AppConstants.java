package com.ezyscrap.webservice;

import android.graphics.Bitmap;

import com.ezyscrap.Model.Login.SellerHomeResponse.LiveScrap;
import com.ezyscrap.Model.Login.SellerHomeResponse.ScrapType;
import com.ezyscrap.Model.Login.SellerHomeResponse.SoldScrap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitware on 2/9/17.
 */

public class AppConstants {
    //public static String BASE_URL = "http://103.224.243.227/codeigniter/scrap/Api_controller/";
    public static String BASE_URL = "http://139.59.70.57/scrap/Api_controller/";
    public static String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)$";
    public static String GET = "GET";
    public static String POST = "POST";
    public static String generateChecksum = BASE_URL + "generate_checksum";
    public static File file;

    public static final int API_LOGIN = 101;
    public static final int API_FORGETPASS = 102;
    public static final int API_REGISTRATION = 103;
    public static final int API_CHANGEPASS = 104;
    public static final int API_BUYER_INTEREST = 105;
    public static final int API_BUYER_REQ = 106;
    public static final int API_ADD_BID = 107;
    public static final int API_BUYER_REG = 108;
    public static final int API_SELLER_HOME = 109;
    public static final int API_UPDATE_BUYER = 110;
    public static final int API_UPDATE_SELLER = 111;
    public static final int API_NOTIFICATION = 112;
    public static final int API_SCRAP_DETAILS = 113;
    public static final int API_BUYER_ORDER_HISTORY = 114;
    public static final int API_PAYMENT_HISTORY = 115;
    public static final int API_PAYTM = 116;

    public static String seller_home_url = BASE_URL+"seller_home_screen";
    public static List<LiveScrap> LIVE_SCRAP_LIST = new ArrayList<>();
    public static List<SoldScrap> SOLD_SCRAP_LIST = new ArrayList<>();
    public static List<ScrapType> ARR_SCRAP_TYPE = new ArrayList<>();
    public static boolean SHOW_DATA = false;
    //public static String AVAL_BAL = "0";
    public static String USER_FLAG = "";
    public static String SCRAP_ID = "";
    public static String PAYU_REDIRECT = "";
    public static boolean INTEREST = false;
    public static String image1 = "", image2 = "", image3 = "", image4 = "";
    public static Bitmap bitImage1 = null, bitImage2 = null, bitImage3 = null, bitImage4 = null;

    //staging details for paytm
    /*public static String M_ID = "ShreeG17486641652876";
    public static String CHANNEL_ID = "WAP"; //Paytm Channel Id, got it in paytm credentials
    public static String INDUSTRY_TYPE_ID = "Retail"; //Paytm industry type got it in paytm credential
    public static String CUSTOMER_ID = "1112";
    public static String ORDER_ID = "11122";
    public static String WEBSITE = "APP_STAGING";
    public static String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";*/


    //production details for paytm
    public static String M_ID = "SHRGAJ95090720260165";
    public static String CHANNEL_ID = "WAP"; //Paytm Channel Id, got it in paytm credentials
    public static String INDUSTRY_TYPE_ID = "Retail109"; //Paytm industry type got it in paytm credential
    public static String CUSTOMER_ID = "";
    public static String ORDER_ID = "";
    public static String MOBILE_NO = "9096067571";
    public static String EMAIL = "djgajananenterprises@gmail.com";
    public static String WEBSITE = "SHRGAJWAP";
    public static String CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";

}
