/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ezyscrap.View;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ezyscrap.AppUtils.MyApplication;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.R;
import com.ezyscrap.View.Activity.ActivityChooser;
import com.ezyscrap.View.Activity.ActivityNotification;
import com.ezyscrap.webservice.AppConstants;

import java.util.Random;

public class

MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    Context mContext;
    //static Context context;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("message"));

            sendNotification(remoteMessage.getData().get("message").toString());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        } else {
            Log.d(TAG, "Message Notification Body: remoteMessage null");
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private void sendNotification(String message) {

        int count;
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        Context context = MyApplication.getContext();
        String PREFS = "MyPrefs";
        NotificationManager notificationManager;
        Notification notification;
        String title;
        SharedPref shared_pref = new SharedPref(context);
        int icon = R.drawable.icon_logo;
        long when = System.currentTimeMillis();
        String[] chat_notification_message = null;
        if (SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "").equalsIgnoreCase("buyer")) {
            if (message.contains("www")) {
                if (SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("") ||
                        SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("null")) {
                    shared_pref.writeString(SharedPref.NOTIFICATION_COUNTER, "0");
                }

                String counter = SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "");
                count = Integer.parseInt(counter) + 1;

                shared_pref.writeString(SharedPref.NOTIFICATION_COUNTER, String.valueOf(count));
                chat_notification_message = message.split("www");
                if (chat_notification_message[0].equalsIgnoreCase("scrap_approve")) {

                    notificationManager = (NotificationManager)
                            context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notifin = new Notification(icon, chat_notification_message[2], when);
                    title = context.getString(R.string.app_name);
                    AppConstants.SCRAP_ID = chat_notification_message[3];

                    if (!SharedPref.getPreferences().getString(SharedPref.USER_ID, "")
                            .equals("")) {
                        // if (MyApplication.isActivityVisible()) {
                        System.out.println("************not1*******");
                        Intent notificationIntent = null;
                        notificationIntent = new Intent(context, ActivityChooser.class);
                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
                        Notification.Builder builder = new Notification.Builder(context);
                        builder.setSmallIcon(R.drawable.note_icon_circle)
                                .setContentTitle(title)
                                .setContentText(chat_notification_message[2])
                                .setContentIntent(intent);
                        notification = builder.getNotification();
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                        notification.defaults |= Notification.DEFAULT_SOUND;
                        notification.defaults |= Notification.DEFAULT_VIBRATE;
                        notificationManager.notify(m, notification);

                    }
                } else {
                    notificationManager = (NotificationManager)
                            context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notifin = new Notification(icon, chat_notification_message[2], when);
                    title = context.getString(R.string.app_name);
                    //AppConstants.SCRAP_ID = chat_notification_message[2];

                    if (!chat_notification_message[2].equalsIgnoreCase("")) {

                        shared_pref.writeString(SharedPref.WALLET_BAL, chat_notification_message[2]);
                    } else {
                        shared_pref.writeString(SharedPref.WALLET_BAL, "0");
                    }
                    if (!SharedPref.getPreferences().getString(SharedPref.USER_ID, "")
                            .equals("")) {
                        // if (MyApplication.isActivityVisible()) {
                        System.out.println("************not1*******");
                        Intent notificationIntent = null;
                        notificationIntent = new Intent(context, ActivityNotification.class);
                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
                        Notification.Builder builder = new Notification.Builder(context);
                        builder.setSmallIcon(R.drawable.note_icon_circle)
                                .setContentTitle(title)
                                .setContentText(chat_notification_message[2])
                                .setContentIntent(intent);
                        notification = builder.getNotification();
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                        notification.defaults |= Notification.DEFAULT_SOUND;
                        notification.defaults |= Notification.DEFAULT_VIBRATE;
                        notificationManager.notify(m, notification);

                    }
                }
                updateCounter(context);

            }
        } else {
            if (message.contains("www")) {
                if (SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("") ||
                        SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("null")) {
                    shared_pref.writeString(SharedPref.NOTIFICATION_COUNTER, "0");
                }

                String counter = SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "");
                count = Integer.parseInt(counter) + 1;

                shared_pref.writeString(SharedPref.NOTIFICATION_COUNTER, String.valueOf(count));
                chat_notification_message = message.split("www");
                if (chat_notification_message[0].equalsIgnoreCase("scrap_approve_seller")) {

                    notificationManager = (NotificationManager)
                            context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notifin = new Notification(icon, chat_notification_message[2], when);
                    title = context.getString(R.string.app_name);

                    if (!SharedPref.getPreferences().getString(SharedPref.USER_ID, "")
                            .equals("")) {
                        System.out.println("************not1*******");
                        Intent notificationIntent = null;
                        notificationIntent = new Intent(context, ActivityChooser.class);
                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
                        Notification.Builder builder = new Notification.Builder(context);
                        builder.setSmallIcon(R.drawable.note_icon_circle)
                                .setContentTitle(title)
                                .setContentText(chat_notification_message[2])
                                .setContentIntent(intent);
                        notification = builder.getNotification();
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                        notification.defaults |= Notification.DEFAULT_SOUND;
                        notification.defaults |= Notification.DEFAULT_VIBRATE;
                        notificationManager.notify(m, notification);

                    }
                }
            }
            updateCounter(context);
        }
    }

    static void updateCounter(Context context) {
        Intent intent = new Intent("homeScreen");
        //send broadcast
        context.sendBroadcast(intent);
    }

    public static void cancelNotification() {
        Context context = MyApplication.getContext();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
