package com.ezyscrap.View.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.AppUtils.UploadPhotoDialog;
import com.ezyscrap.AppUtils.WebServiceImage;
import com.ezyscrap.R;
import com.ezyscrap.webservice.AppConstants;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by bitware on 20/12/17.
 */

public class ScrapConfirmationFragment extends Fragment implements View.OnClickListener {

    View view;
    Button btn_submit;
    ImageView iv_scrapImage1, iv_scrapImage2, iv_scrapImage3, iv_scrapImage4;
    SharedPref pref;
    //String picturePath;
    Bitmap tempBmp;
    TextView tv_scrapType, tv_scrapDescription, tv_scrapAmount;
    File f1 = null, f2 = null, f3 = null, f4 = null;
    String scrapType, scrapDesciption, scrapAmount, scrapId;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount;
    RelativeLayout relativeNotCnt;
    Fragment fragment;
    FragmentManager fm;
    LinearLayout linearimgView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scrap_confirmation, container, false);
        init();

        //picturePath = pref.getString("picturePath", "");

        return view;
    }

    private void init() {
        fm = getActivity().getSupportFragmentManager();
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);

        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        imgNotification.setVisibility(View.GONE);
        txtHeading.setText("Scrap Confirmation");

        pref = new SharedPref(getContext());
        linearimgView = view.findViewById(R.id.linearimgView);
        btn_submit = view.findViewById(R.id.btn_submit);
        iv_scrapImage1 = view.findViewById(R.id.iv_scrapImage1);
        iv_scrapImage2 = view.findViewById(R.id.iv_scrapImage2);
        iv_scrapImage3 = view.findViewById(R.id.iv_scrapImage3);
        iv_scrapImage4 = view.findViewById(R.id.iv_scrapImage4);
        tv_scrapType = view.findViewById(R.id.tv_scrapType);
        tv_scrapDescription = view.findViewById(R.id.tv_scrapDescription);
        tv_scrapAmount = view.findViewById(R.id.tv_scrapAmount);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStackImmediate();
            }
        });
        btn_submit.setOnClickListener(this);

        Bundle bundle = getArguments();
        scrapType = bundle.getString("scrapType");
        scrapDesciption = bundle.getString("scrapDesc");
        scrapAmount = bundle.getString("scrapAmount");
        scrapId = bundle.getString("scrapId");

        tv_scrapType.setText(bundle.getString("scrapType"));
        tv_scrapDescription.setText(bundle.getString("scrapDesc"));
        tv_scrapAmount.setText(bundle.getString("scrapAmount"));

        if (!bundle.getString("photo1").equalsIgnoreCase("")) {

            f1 = new File(Environment.getExternalStorageDirectory() + "/_camera1.png");
            if (f1 != null) {
                try {
                    f1.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                AppConstants.bitImage1.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
                // save it in your external storage.
                FileOutputStream fo = null;
                try {
                    fo = new FileOutputStream(f1);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fo.write(byteArray);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            Bitmap bmp = UploadPhotoDialog.decodeSampledBitmapFromPath(bundle.getString("photo1"), 150, 150);
            if (bmp != null)
                tempBmp = bmp;

            // iv_scrapImage1.setImageBitmap(AppConstants.bitImage1);
            Glide.with(getContext()).load(AppConstants.image1).into(iv_scrapImage1);
        }

        if (!bundle.getString("photo2").equalsIgnoreCase("")) {
            iv_scrapImage2.setVisibility(View.VISIBLE);

            f2 = new File(Environment.getExternalStorageDirectory() + "/_camera2.png");
            if (f2 != null) {
                try {
                    f2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                AppConstants.bitImage2.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
                // save it in your external storage.
                FileOutputStream fo = null;
                try {
                    fo = new FileOutputStream(f2);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fo.write(byteArray);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Bitmap bmp = UploadPhotoDialog.decodeSampledBitmapFromPath(bundle.getString("photo2"), 150, 150);

            if (bmp != null)
                tempBmp = bmp;

            //iv_scrapImage2.setImageBitmap(AppConstants.bitImage2);
            Glide.with(getContext()).load(AppConstants.image2).into(iv_scrapImage2);
        }

        if (!bundle.getString("photo3").equalsIgnoreCase("")) {
            linearimgView.setVisibility(View.VISIBLE);
            iv_scrapImage3.setVisibility(View.VISIBLE);

            f3 = new File(Environment.getExternalStorageDirectory() + "/_camera3.png");
            if (f3 != null) {
                try {
                    f3.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                AppConstants.bitImage3.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
                // save it in your external storage.
                FileOutputStream fo = null;
                try {
                    fo = new FileOutputStream(f3);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fo.write(byteArray);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Bitmap bmp = UploadPhotoDialog.decodeSampledBitmapFromPath(bundle.getString("photo3"), 150, 150);

            if (bmp != null)
                tempBmp = bmp;

            //iv_scrapImage3.setImageBitmap(AppConstants.bitImage3);
            Glide.with(getContext()).load(AppConstants.image3).into(iv_scrapImage3);
        }

        if (!bundle.getString("photo4").equalsIgnoreCase("")) {
            iv_scrapImage4.setVisibility(View.VISIBLE);

            f4 = new File(Environment.getExternalStorageDirectory() + "/_camera4.png");
            if (f4 != null) {
                try {
                    f4.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                AppConstants.bitImage4.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
                // save it in your external storage.
                FileOutputStream fo = null;
                try {
                    fo = new FileOutputStream(f4);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fo.write(byteArray);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Bitmap bmp = UploadPhotoDialog.decodeSampledBitmapFromPath(bundle.getString("photo4"), 150, 150);

            if (bmp != null)
                tempBmp = bmp;

            //iv_scrapImage4.setImageBitmap(AppConstants.bitImage4);
            Glide.with(getContext()).load(AppConstants.image4).into(iv_scrapImage4);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (AppUtils.isConnectingToInternet(getContext())) {
                    callSubmitScrapApi();
                } else {
                    AppUtils.toastMsg(getContext(), getResources().getString(R.string.no_internet));
                }

                break;
        }
    }

    private void callSubmitScrapApi() {
        try {
            String token = pref.getString("token", "");
            System.out.println("Token is >" + token);
            System.out.println("amount is > " + scrapAmount);

            //SharedPref shared_pref = new SharedPref(AddPostActivity.this);
            WebServiceImage service = new WebServiceImage(callback);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("type_id", new StringBody(scrapId));
            reqEntity.addPart("desc", new StringBody(scrapDesciption));
            reqEntity.addPart("quantity", new StringBody(scrapAmount));
            reqEntity.addPart("token", new StringBody(token));

            if (f1 == null) {
                //  FLAG_IMG = true;
                reqEntity.addPart("image1", new StringBody(""));
            } else {
                // FLAG_IMG = true;
                reqEntity.addPart("image1", new FileBody(f1));
            }

            if (f2 == null) {
                //  FLAG_IMG = true;
                reqEntity.addPart("image2", new StringBody(""));
            } else {
                // FLAG_IMG = true;
                reqEntity.addPart("image2", new FileBody(f2));
            }

            if (f3 == null) {
                //  FLAG_IMG = true;
                reqEntity.addPart("image3", new StringBody(""));
            } else {
                // FLAG_IMG = true;
                reqEntity.addPart("image3", new FileBody(f3));
            }

            if (f4 == null) {
                //  FLAG_IMG = true;
                reqEntity.addPart("image4", new StringBody(""));
            } else {
                // FLAG_IMG = true;
                reqEntity.addPart("image4", new FileBody(f4));
            }

            service.getService(getActivity(), AppConstants.BASE_URL + "add_scrap", reqEntity);

        } catch (NullPointerException e) {
            System.out.println("Nullpointer Exception at Login Screen" + e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    WebServiceImage.CallbackImage callback = new WebServiceImage.CallbackImage() {
        @Override
        public void onSuccessImage(int reqestcode, JSONObject rootjson) {
            System.out.println("++++++-result++++++" + rootjson);
            try {
                if (rootjson.get("status").toString().equals("success")) {
                    AppConstants.bitImage1 = null;
                    AppConstants.bitImage2 = null;
                    AppConstants.bitImage3 = null;
                    AppConstants.bitImage4 = null;

                    AppConstants.image1 = "";
                    AppConstants.image2 = "";
                    AppConstants.image3 = "";
                    AppConstants.image4 = "";
                    Bundle bundle = new Bundle();
                    bundle.putString("order_id", rootjson.get("order_id").toString());
                    AppUtils.replaceFragment(new FragmentOrderConfirmation(), getFragmentManager(), bundle);
                }

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("++++++-Exception++++++" + e);
            }
        }

        @Override
        public void onErrorImage(int reqestcode, String error) {
        }

    };
}
