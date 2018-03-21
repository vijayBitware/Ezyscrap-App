package com.ezyscrap.View.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.AppUtils.UploadPhotoDialog;
import com.ezyscrap.Controller.ScrapTypeAdapter;
import com.ezyscrap.R;
import com.ezyscrap.View.Activity.ActivityNotification;
import com.ezyscrap.webservice.AppConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by bitware on 20/12/17.
 */

public class AddScrapFragment extends Fragment implements View.OnClickListener {

    View view;
    Button txt_next, btn_uploadScrapPhoto;
    Spinner spn_scrapType, spn_units;
    String[] scrap_type = {"kg", "ql", "ton"};
    String[] scrap_id = {"1", "2", "3", "4"};
    EditText edt_scrapDescription, edt_scrapAmout;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private static int GALLARY = 3;
    final int CAMERA_CAPTURE = 1;
    Bitmap tempBmp;
    UploadPhotoDialog mUploadPhotoDialog;
    Uri fileUri;
    //ImageView iv_scrapPhoto;
    String scrapType, scrapDesciption, scrapAmount, scrapId, scrapUnit;
    SharedPref sharedPref;
    ArrayList<String> arrScrapNames, arrScrapIDs;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount;
    RelativeLayout relativeNotCnt;
    ImageView ivImgView1, ivImgView2, ivImgView3, ivImgView4;
    LinearLayout linearImgView;
    String imgNo;
    String photo1 = "", photo2 = "", photo3 = "", photo4 = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_scrap, container, false);
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
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);

        linearImgView = view.findViewById(R.id.linearImgView);

        ivImgView1 = view.findViewById(R.id.ivImgView1);
        ivImgView2 = view.findViewById(R.id.ivImgView2);
        ivImgView3 = view.findViewById(R.id.ivImgView3);
        ivImgView4 = view.findViewById(R.id.ivImgView4);

        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.GONE);
        imgNotification.setVisibility(View.GONE);

        txtHeading.setText("Add Scrap");

        txt_next = view.findViewById(R.id.txt_next);
        spn_scrapType = view.findViewById(R.id.spn_scrapType);
        Log.e("TAG", "ezyscrap type length > " + AppConstants.ARR_SCRAP_TYPE.size());
        edt_scrapDescription = view.findViewById(R.id.edt_scrapDescription);
        edt_scrapAmout = view.findViewById(R.id.edt_scrapAmout);
        btn_uploadScrapPhoto = view.findViewById(R.id.btn_uploadScrapPhoto);
        //iv_scrapPhoto = view.findViewById(R.id.iv_scrapPhoto);
        //iv_scrapPhoto.setTag("no_image");
        sharedPref = new SharedPref(getContext());
        spn_units = view.findViewById(R.id.spn_units);

        ArrayAdapter unitAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, scrap_type);
        spn_units.setAdapter(unitAdapter);

        arrScrapNames = new ArrayList<>();
        for (int i = 0; i < AppConstants.ARR_SCRAP_TYPE.size(); i++) {
            arrScrapNames.add(AppConstants.ARR_SCRAP_TYPE.get(i).getType());
        }

        arrScrapIDs = new ArrayList<>();
        for (int i = 0; i < AppConstants.ARR_SCRAP_TYPE.size(); i++) {
            arrScrapIDs.add(AppConstants.ARR_SCRAP_TYPE.get(i).getId());
        }
        spn_scrapType.setAdapter(new ScrapTypeAdapter(getContext(), R.layout.row_scraptype, arrScrapNames));

        ivImgView1.setOnClickListener(this);
        ivImgView2.setOnClickListener(this);
        ivImgView3.setOnClickListener(this);
        ivImgView4.setOnClickListener(this);
        txt_next.setOnClickListener(this);
        btn_uploadScrapPhoto.setOnClickListener(this);
        //iv_scrapPhoto.setOnClickListener(this);

        spn_scrapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // AppUtils.toastMsg(getContext(),arrScrapIDs.get(i));
                scrapId = arrScrapIDs.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // AppUtils.toastMsg(getContext(), scrap_type[i]);
                scrapUnit = scrap_type[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_next:
                scrapType = spn_scrapType.getSelectedItem().toString();
                scrapDesciption = edt_scrapDescription.getText().toString();
                scrapAmount = edt_scrapAmout.getText().toString();
                if (!scrapType.isEmpty()) {
                    if (!scrapDesciption.isEmpty()) {
                        if (!scrapAmount.isEmpty()) {
                            if (!(tempBmp == null)) {
                                AppConstants.image1 = photo1;
                                AppConstants.image2 = photo2;
                                AppConstants.image3 = photo3;
                                AppConstants.image4 = photo4;

                                Bundle bundle = new Bundle();
                                bundle.putString("scrapType", scrapType);
                                bundle.putString("scrapDesc", scrapDesciption);
                                bundle.putString("scrapAmount", scrapAmount + " " + scrapUnit);
                                bundle.putString("scrapId", scrapId);
                                bundle.putString("photo1", photo1);
                                bundle.putString("photo2", photo2);
                                bundle.putString("photo3", photo3);
                                bundle.putString("photo4", photo4);

                                AppUtils.replaceFragment(new ScrapConfirmationFragment(), getFragmentManager(), bundle);
                            } else {
                                AppUtils.toastMsg(getContext(), "Please Select Image");
                            }
                        } else {
                            AppUtils.setErrorMsg(edt_scrapAmout, "Please Enter Amount");
                        }
                    } else {
                        AppUtils.setErrorMsg(edt_scrapDescription, "Please Enter Desciption");
                    }
                } else {
                    AppUtils.toastMsg(getContext(), "Please Select Scrap Type");
                }
                break;

            case R.id.ivImgView1:
                imgNo = "1";
                mUploadPhotoDialog = new UploadPhotoDialog(getActivity(), mMediaDialogListener, "no_image");
                mUploadPhotoDialog.show();
                break;
            case R.id.ivImgView2:
                imgNo = "2";
                mUploadPhotoDialog = new UploadPhotoDialog(getActivity(), mMediaDialogListener, "no_image");
                mUploadPhotoDialog.show();
                break;
            case R.id.ivImgView3:
                imgNo = "3";
                mUploadPhotoDialog = new UploadPhotoDialog(getActivity(), mMediaDialogListener, "no_image");
                mUploadPhotoDialog.show();
                break;
            case R.id.ivImgView4:
                imgNo = "4";
                mUploadPhotoDialog = new UploadPhotoDialog(getActivity(), mMediaDialogListener, "no_image");
                mUploadPhotoDialog.show();
                break;
        }
    }

    UploadPhotoDialog.onMediaDialogListener mMediaDialogListener = new UploadPhotoDialog.onMediaDialogListener() {

        @Override
        public void onGalleryClick() {
            // TODO Auto-generated method stub
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            startActivityForResult(i, GALLARY);
        }

        @Override
        public void onDeleteClick() {

        }

        @Override
        public void onCameraClick() {
            // TODO Auto-generated method stub
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            System.out.println("File URI------" + fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            // start the image capture Intent
            startActivityForResult(intent, CAMERA_CAPTURE);
        }

    };

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
    * returning image / video
    */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("Image Result code-------" + resultCode);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE) {
                try {
                    // bimatp factory
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // downsizing image as it throws OutOfMemory Exception for larger  // images
                    options.inSampleSize = 8;

                    final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                    tempBmp = bitmap;

                    ExifInterface exif = new ExifInterface(fileUri.getPath());
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    System.out.println("Orientation >>> " + orientation);

                    if (orientation == 6) {
                        Matrix matrix = new Matrix();
                        matrix.setRotate(90);
                        matrix.setRotate(90, (float) tempBmp.getWidth() / 2, (float) tempBmp.getHeight() / 2);
                        Bitmap rotatedBitmap = Bitmap.createBitmap(tempBmp, 0, 0, options.outWidth, options.outHeight, matrix, true);// Return result
                        tempBmp = rotatedBitmap;
                    }

                    if (imgNo.equalsIgnoreCase("1")) {
                        photo1 = fileUri.getPath();
                        ivImgView2.setVisibility(View.VISIBLE);
                        //ivImgView1.setImageBitmap(tempBmp);
                        AppConstants.bitImage1 = tempBmp;
                        Glide.with(getContext()).load(photo1).into(ivImgView1);
                    } else if (imgNo.equalsIgnoreCase("2")) {
                        photo2 = fileUri.getPath();
                        linearImgView.setVisibility(View.VISIBLE);
                        ivImgView3.setVisibility(View.VISIBLE);
                        //ivImgView2.setImageBitmap(tempBmp);
                        AppConstants.bitImage2 = tempBmp;
                        Glide.with(getContext()).load(photo2).into(ivImgView2);

                    } else if (imgNo.equalsIgnoreCase("3")) {
                        photo3 = fileUri.getPath();
                        ivImgView4.setVisibility(View.VISIBLE);
                        //ivImgView3.setImageBitmap(tempBmp);
                        AppConstants.bitImage3 = tempBmp;
                        Glide.with(getContext()).load(photo3).into(ivImgView3);

                    } else if (imgNo.equalsIgnoreCase("4")) {
                        photo4 = fileUri.getPath();
                        //ivImgView4.setImageBitmap(tempBmp);
                        AppConstants.bitImage4 = tempBmp;
                        Glide.with(getContext()).load(photo4).into(ivImgView4);

                    }

                    btn_uploadScrapPhoto.setVisibility(View.GONE);
                    System.out.println("Pic file Uri====" + fileUri.getPath() + ", Bitmap-------" + bitmap);
                    sharedPref.writeString("picturePath", fileUri.getPath());
                    UploadPhotoDialog.profile_ = fileUri.getPath().toString();

                    // getServiceResponseForPhoto(bitmap);

                } catch (Exception e) {
                    System.out.println("Exception camera click--" + e.toString());
                }

            } else if (requestCode == GALLARY && resultCode == RESULT_OK && null != data) {

                System.out.println("Picture path in GALLARY:");

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);

                System.out.println("Picture path :" + picturePath);
                sharedPref.writeString("picturePath", picturePath);

                try {
                    File f = null;
                    if (picturePath != null)
                        f = new File(picturePath);
                    else
                        Toast.makeText(getActivity(), "Error while rendering image.", Toast.LENGTH_SHORT).show();

                    f.createNewFile();
                    UploadPhotoDialog.profile_ = f.toString();
                    System.out.println("Picture path in UploadPhotoDialog.profile_:" + UploadPhotoDialog.profile_);
                    cursor.close();
                } catch (FileNotFoundException e) {
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error while rendering image.", Toast.LENGTH_SHORT).show();
                }
                Bitmap bmp = UploadPhotoDialog.decodeSampledBitmapFromPath(picturePath, 150, 150);

                if (bmp != null)
                    tempBmp = bmp;

                // bimatp factory
                BitmapFactory.Options options = new BitmapFactory.Options();
                // downsizing image as it throws OutOfMemory Exception for larger  // images
                options.inSampleSize = 8;

                final Bitmap bitmap = BitmapFactory.decodeFile(UploadPhotoDialog.profile_, options);
                tempBmp = bitmap;

                System.out.println("Picture path :" + UploadPhotoDialog.profile_);
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(UploadPhotoDialog.profile_);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                System.out.println("Orientation >>> " + orientation);

                if (orientation == 6) {
                    Matrix matrix = new Matrix();
                    matrix.setRotate(90);
                    matrix.setRotate(90, (float) tempBmp.getWidth() / 2, (float) tempBmp.getHeight() / 2);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(tempBmp, 0, 0, options.outWidth, options.outHeight, matrix, true);// Return result
                    tempBmp = rotatedBitmap;
                }

                if (imgNo.equalsIgnoreCase("1")) {
                    photo1 = picturePath;
                    ivImgView2.setVisibility(View.VISIBLE);
                    //ivImgView1.setImageBitmap(tempBmp);
                    AppConstants.bitImage1 = tempBmp;
                    Glide.with(getContext()).load(photo1).into(ivImgView1);

                } else if (imgNo.equalsIgnoreCase("2")) {
                    photo2 = picturePath;
                    linearImgView.setVisibility(View.VISIBLE);
                    ivImgView3.setVisibility(View.VISIBLE);
                    //ivImgView2.setImageBitmap(tempBmp);
                    AppConstants.bitImage2 = tempBmp;
                    Glide.with(getContext()).load(photo2).into(ivImgView2);

                } else if (imgNo.equalsIgnoreCase("3")) {
                    photo3 = picturePath;
                    ivImgView4.setVisibility(View.VISIBLE);
                    //ivImgView3.setImageBitmap(tempBmp);
                    AppConstants.bitImage3 = tempBmp;
                    Glide.with(getContext()).load(photo3).into(ivImgView3);

                } else if (imgNo.equalsIgnoreCase("4")) {
                    photo4 = picturePath;
                    //ivImgView4.setImageBitmap(tempBmp);
                    AppConstants.bitImage4 = tempBmp;
                    Glide.with(getContext()).load(photo4).into(ivImgView4);

                }

                btn_uploadScrapPhoto.setVisibility(View.GONE);

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (!AppConstants.image1.isEmpty()) {
                Glide.with(getContext()).load(AppConstants.image1).into(ivImgView1);
                ivImgView2.setVisibility(View.VISIBLE);
            }
            if (!AppConstants.image2.isEmpty()) {
                ivImgView2.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(AppConstants.image2).into(ivImgView2);
                linearImgView.setVisibility(View.VISIBLE);
                ivImgView3.setVisibility(View.VISIBLE);
            }
            if (!AppConstants.image3.isEmpty()) {
                linearImgView.setVisibility(View.VISIBLE);
                ivImgView3.setVisibility(View.VISIBLE);
                ivImgView4.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(AppConstants.image3).into(ivImgView3);
            }
            if (!AppConstants.image4.isEmpty()) {
                ivImgView4.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(AppConstants.image4).into(ivImgView4);
            }
        }catch (Exception e)
        {

        }

    }
}
