package com.ezyscrap.View.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Model.Login.RegisterResponse;
import com.ezyscrap.R;
import com.ezyscrap.View.Activity.AboutUsActivity;
import com.ezyscrap.View.Activity.ActivityNotification;
import com.ezyscrap.View.Activity.ContactUsActivity;
import com.ezyscrap.View.Activity.LoginActivity;
import com.ezyscrap.View.Activity.PrivacyPolicyActivity;
import com.ezyscrap.View.Activity.ReturnPolicyActivity;
import com.ezyscrap.View.Activity.UpdateProfileActivity;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bitware on 21/12/17.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener, APIRequest.ResponseHandler {
    Fragment fragment;
    FragmentManager fm;
    View view,lineAfterHistory;
    SharedPref sharedPref;
    TextView txt_sellerName, txt_contactNumber, txt_address, txtEmail, txtEdit, txtChangePswd, txtContactUs, txtHistory, txtPrivacyAndTerms, txtLogout;
    EditText edt_oldPassord, edt_password, edt_confirmePass;
    Dialog dialog;
    String oldPass, newPass, cofirmPass;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount, txtReturnPolicy, txtAboutUs;
    RelativeLayout relativeNotCnt;
    public int screenWidth, screenHeight;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_new, container, false);
        init();
        if (SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("") ||
                SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("0")) {
            txtNotCount.setText("");
            txtNotCount.setVisibility(View.GONE);
            relativeNotCnt.setVisibility(View.GONE);
            imgNotification.setVisibility(View.VISIBLE);

        } else {
            txtNotCount.setText(SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, ""));
            txtNotCount.setVisibility(View.VISIBLE);
            relativeNotCnt.setVisibility(View.VISIBLE);
            imgNotification.setVisibility(View.VISIBLE);
        }
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ActivityNotification.class);
                startActivity(i);
            }
        });
        return view;
    }

    private void init() {
        AppConstants.INTEREST = false;
        fm = getActivity().getSupportFragmentManager();
        sharedPref = new SharedPref(getActivity());
        //  btn_logout = view.findViewById(R.id.btn_logout);
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);
        screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();

        if (SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "").equalsIgnoreCase("buyer")) {
            imgBack.setVisibility(View.GONE);
            imgNotification.setVisibility(View.VISIBLE);
            if (SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("") ||
                    SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("0")) {
                txtNotCount.setText("");
                txtNotCount.setVisibility(View.GONE);
                relativeNotCnt.setVisibility(View.GONE);

            } else {
                txtNotCount.setText(SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, ""));
                txtNotCount.setVisibility(View.VISIBLE);
                relativeNotCnt.setVisibility(View.VISIBLE);

            }

        } else {
            txtNotCount.setVisibility(View.GONE);
            relativeNotCnt.setVisibility(View.GONE);
            imgBack.setVisibility(View.GONE);
            imgNotification.setVisibility(View.GONE);
        }

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ActivityNotification.class);
                startActivity(i);
            }
        });
        txtHeading.setText("Profile");
        txt_sellerName = view.findViewById(R.id.txt_sellerName);
        txt_contactNumber = view.findViewById(R.id.txt_contactNumber);
        txt_address = view.findViewById(R.id.txt_address);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtEdit = view.findViewById(R.id.txtEdit);
        txtChangePswd = view.findViewById(R.id.txtChangePswd);
        txtContactUs = view.findViewById(R.id.txtContactUs);
        txtHistory = view.findViewById(R.id.txtHistory);
        txtPrivacyAndTerms = view.findViewById(R.id.txtPrivacyAndTerms);
        txtReturnPolicy = view.findViewById(R.id.txtReturnPolicy);
        txtAboutUs = view.findViewById(R.id.txtAboutUs);
        txtLogout = view.findViewById(R.id.txtLogout);
        lineAfterHistory = view.findViewById(R.id.lineAfterHistory);

        //  btn_changePassword = view.findViewById(R.id.btn_changePassword);
        edt_oldPassord = view.findViewById(R.id.edt_oldPassord);
        edt_password = view.findViewById(R.id.edt_password);
        edt_confirmePass = view.findViewById(R.id.edt_confirmePass);

        txtContactUs.setOnClickListener(this);
        txtPrivacyAndTerms.setOnClickListener(this);
        txtReturnPolicy.setOnClickListener(this);
        txtAboutUs.setOnClickListener(this);
        txtHistory.setOnClickListener(this);
        txtEdit.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
        txtChangePswd.setOnClickListener(this);


    }

    private void setData() {
        if (SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "").equalsIgnoreCase("Buyer")) {
            txt_sellerName.setText(SharedPref.getPreferences().getString(SharedPref.BUYER_NAME, ""));
        } else {
            txt_sellerName.setText(SharedPref.getPreferences().getString(SharedPref.ORG_NAME, ""));
        }

        txt_contactNumber.setText(SharedPref.getPreferences().getString(SharedPref.PHONE_NO, ""));
//        txt_zipcode.setText(SharedPref.getPreferences().getString(SharedPref.ZIP, ""));
        txtEmail.setText(SharedPref.getPreferences().getString(SharedPref.EMAIL, ""));
        txt_address.setText(SharedPref.getPreferences().getString(SharedPref.ADDRESS, ""));

        if (SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "").equalsIgnoreCase("Buyer")){
            txtHistory.setVisibility(View.VISIBLE);
            lineAfterHistory.setVisibility(View.VISIBLE);
        }else  if (SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "").equalsIgnoreCase("Seller")){
            txtHistory.setVisibility(View.GONE);
            lineAfterHistory.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.txtReturnPolicy:
               i = new Intent(getActivity(), ReturnPolicyActivity.class);
               startActivity(i);
                break;
            case R.id.txtPrivacyAndTerms:
                i = new Intent(getActivity(), PrivacyPolicyActivity.class);
                startActivity(i);
                break;
            case R.id.txtAboutUs:
                i = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(i);
                break;
            case R.id.txtContactUs:
                i = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(i);
                break;
            case R.id.txtHistory:
                fragment = new FragmentHistory();
                AppUtils.replaceFragment(fragment, fm, null);
                break;
            case R.id.txtLogout:
                showLogoutDialog();
                break;
            case R.id.txtChangePswd:
                showForgetPasswordDialog();
                break;
            case R.id.txtEdit:
                startActivity(new Intent(getContext(), UpdateProfileActivity.class));
                break;
        }
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext())
                .setTitle("Log Out?")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPref.writeString(SharedPref.USER_ID, "");
                        sharedPref.writeString(SharedPref.WALLET_BAL, "");
                        sharedPref.writeString(SharedPref.NOTIFICATION_COUNTER, "");
                        AppConstants.LIVE_SCRAP_LIST = new ArrayList<>();
                        AppConstants.SOLD_SCRAP_LIST = new ArrayList<>();
                        AppConstants.USER_FLAG = SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "");
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Exit", true);
                        startActivity(intent);

                        //startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showForgetPasswordDialog() {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_changepass);

        final EditText edt_oldPass, edt_pass, edt_confirmPass;
        TextView txt_submit, txt_cancel;

        edt_oldPass = dialog.findViewById(R.id.edt_oldPassord);
        edt_pass = dialog.findViewById(R.id.edt_password);
        edt_confirmPass = dialog.findViewById(R.id.edt_confirmePass);
        txt_submit = dialog.findViewById(R.id.txt_submit);
        txt_cancel = dialog.findViewById(R.id.txt_cancel);

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPass = edt_oldPass.getText().toString();
                newPass = edt_pass.getText().toString();
                String cofirmPass = edt_confirmPass.getText().toString();

                if (!oldPass.isEmpty()) {
                    if (!newPass.isEmpty()) {
                        if (!cofirmPass.isEmpty()) {
                            if (newPass.equalsIgnoreCase(cofirmPass)) {
                                callChangePAssswordApi();
                            } else {
                                AppUtils.toastMsg(getContext(), "Password Dosen't Match");
                            }
                        } else {
                            AppUtils.setErrorMsg(edt_confirmPass, "Please Enter New Password");
                        }
                    } else {
                        AppUtils.setErrorMsg(edt_pass, "Please Enter New Password");
                    }
                } else {
                    AppUtils.setErrorMsg(edt_oldPass, "Please Enter Old Password");
                }

            }
        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

    private void callChangePAssswordApi() {
        String url = AppConstants.BASE_URL + "change_password";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("old_pwd", oldPass);
            jsonObject.put("new_pwd", newPass);
            jsonObject.put("flag", SharedPref.getPreferences().getString(SharedPref.USER_FLAG, ""));
            jsonObject.put("token", SharedPref.getPreferences().getString(SharedPref.TOKEN, ""));
            Log.e("TAG", "Request > " + jsonObject);

            new APIRequest(getContext(), url, jsonObject, this, AppConstants.API_CHANGEPASS, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        RegisterResponse registerResponse = (RegisterResponse) response;
        if (registerResponse.getStatus().equalsIgnoreCase("success")) {
            dialog.dismiss();
            AppUtils.toastMsg(getContext(), registerResponse.getDescription());
        } else {
            AppUtils.toastMsg(getContext(), registerResponse.getDescription());
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onResume() {
        super.onResume();
        AppConstants.image1 = null;
        AppConstants.image2 = null;
        AppConstants.image3 = null;
        AppConstants.image4 = null;
        setData();
    }
}
