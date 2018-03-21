package com.ezyscrap.View.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Controller.NotificationAdapter;

import com.ezyscrap.Model.Login.Notification.NotificationResponse;
import com.ezyscrap.Model.NotificationModel;
import com.ezyscrap.R;
import com.ezyscrap.View.MyFirebaseMessagingService;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityNotification extends AppCompatActivity implements APIRequest.ResponseHandler {
    NotificationAdapter notificationAdapter;
    LinearLayoutManager lm;
    List<NotificationModel> notificationModelList;
    String notificationMessage, notificationDate;
    FrameLayout frame;
    RecyclerView recyclerView;
    View view;
    Context context;
    SharedPref sharedPref;
    String str_date, str_time;
    ImageView imgBack;
    String token_key;
    TextView txt_no_history;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        sharedPref = new SharedPref(this);
        init();
    }

    private void init() {
        //clears notification from statusbar
        MyFirebaseMessagingService notif = new MyFirebaseMessagingService();
        notif.cancelNotification();

        sharedPref.writeString(SharedPref.NOTIFICATION_COUNTER,"");

        notificationModelList = new ArrayList<>();
        //Intent intent=getIntent();
        frame = findViewById(R.id.frame);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        txt_no_history = (TextView) findViewById(R.id.txt_no_history);
        lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "").equalsIgnoreCase("buyer"))
        {
            frame.setBackgroundColor(getResources().getColor(R.color.blue));
        }else
        {
            frame.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        setNotificationData();
    }

    private void webserviceNotification() {

        token_key = SharedPref.getPreferences().getString(SharedPref.TOKEN, "");
        System.out.println("..............token......." + token_key);
        String Url = AppConstants.BASE_URL + "all_notifications";
        System.out.println("Notification Url >" + Url);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token_key);
            jsonObject.put("flag", SharedPref.getPreferences().getString(SharedPref.USER_FLAG, ""));

            Log.e("TAG", String.valueOf(jsonObject));
            new APIRequest(ActivityNotification.this, Url, jsonObject, this, AppConstants.API_NOTIFICATION, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setNotificationData() {
        webserviceNotification();

    }

    @Override
    public void onSuccess(BaseResponse response) {
        System.out.println("................response..........." + response);

        NotificationResponse notificationResponse = (NotificationResponse) response;

        System.out.println("#####" + notificationResponse.getStatus().toString());

        if (notificationResponse.getStatus().toString().equalsIgnoreCase("success")) {

            if (notificationResponse.getNotifData().size() == 0) {
                txt_no_history.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                txt_no_history.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < notificationResponse.getNotifData().size(); i++) {
                NotificationModel notificationModel = new NotificationModel();
                notificationMessage = notificationResponse.getNotifData().get(i).getNotification();
                notificationDate = notificationResponse.getNotifData().get(i).getDate();

                data = notificationDate.split("\\s+");
                str_date = data[0];
                str_time = data[1];

                notificationModel.setTxtNotificationMessage(notificationMessage);

                notificationModel.setTxtDate("Date: "+str_date);

                notificationModelList.add(notificationModel);
            }

            notificationAdapter = new NotificationAdapter(ActivityNotification.this, notificationModelList);
            recyclerView.setAdapter(notificationAdapter);

        } else {
           // Toast.makeText(ActivityNotification.this, notificationResponse.getDescription().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
