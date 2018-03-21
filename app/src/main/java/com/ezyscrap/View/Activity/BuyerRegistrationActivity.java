package com.ezyscrap.View.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyscrap.AppUtils.AlertClass;
import com.ezyscrap.AppUtils.AppUtils;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtOrgName, edtContactNumber, edtAddress, edtCityName, edtZipCode, edtEmailId, edtPassword;
    Button btnRegister, btnUploadLic;
    TextView txtLoginHere;
    ImageView imgBackArrow,imgLicence;
    String orgName, contactNumber, address, city, zipcode, emailId, password;
    File f;
    String imgTag = "no_image";

    private static int RESULT_LOAD_IMAGE = 3;

    String path = Environment.getExternalStorageDirectory() + "/image.jpg";
    String network_status;
    String gender;
    private ListView list;
    private ListAdapter adapter;
    private Dialog activationDialog = null;
    private AutoCompleteTextView location;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Scrap";
    private static int GALLARY = 3;
    final int CAMERA_CAPTURE = 1;
    Bitmap tempBmp;
    UploadPhotoDialog mUploadPhotoDialog;
    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_register);
        init();
    }

    private void init() {
        txtLoginHere = (TextView) findViewById(R.id.txtLoginHere);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnUploadLic = (Button) findViewById(R.id.btnUploadLic);
        edtOrgName = (EditText) findViewById(R.id.edtOrgName);
        edtContactNumber = (EditText) findViewById(R.id.edtContactNumber);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtCityName = (EditText) findViewById(R.id.edtCityName);
        edtZipCode = (EditText) findViewById(R.id.edtZipCode);
        edtEmailId = (EditText) findViewById(R.id.edtEmailId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgBackArrow = (ImageView) findViewById(R.id.imgBackArrow);
        imgLicence = findViewById(R.id.imgLicence);

        btnRegister.setOnClickListener(this);
        txtLoginHere.setOnClickListener(this);
        imgBackArrow.setOnClickListener(this);
        btnUploadLic.setOnClickListener(this);
        imgLicence.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnRegister:

                registerWebservice();
                break;

            case R.id.imgBackArrow:

                finish();
                break;

            case R.id.txtLoginHere:

                finish();
                break;

            case R.id.btnUploadLic:

                uploadLicense();
                break;
            case R.id.imgLicence:
                uploadLicense();
                break;

        }
    }

    private void uploadLicense() {
        mUploadPhotoDialog = new UploadPhotoDialog(BuyerRegistrationActivity.this, mMediaDialogListener, imgTag);
        mUploadPhotoDialog.show();
    }

    private void registerWebservice() {

        if (AppUtils.isConnectingToInternet(this)) {
            try {
                if (validation()) {
                    WebServiceImage service = new WebServiceImage(callback);
                    MultipartEntity reqEntity = new MultipartEntity();
                    reqEntity.addPart("name", new StringBody(edtOrgName.getText().toString()));
                    reqEntity.addPart("email", new StringBody(edtEmailId.getText().toString()));
                    reqEntity.addPart("phone_no", new StringBody(edtContactNumber.getText().toString()));
                    reqEntity.addPart("address", new StringBody(edtAddress.getText().toString()));
                    reqEntity.addPart("password", new StringBody(edtPassword.getText().toString()));
                    reqEntity.addPart("city", new StringBody(edtCityName.getText().toString()));
                    reqEntity.addPart("zip", new StringBody(edtZipCode.getText().toString()));

                    if (f == null) {
                        //  FLAG_IMG = true;
                        reqEntity.addPart("image", new StringBody(""));
                    } else {
                        // FLAG_IMG = true;
                        reqEntity.addPart("image", new FileBody(f));
                    }

                    service.getService(this, AppConstants.BASE_URL + "register_new_buyer", reqEntity);
                }
            } catch (NullPointerException e) {
                System.out.println("Nullpointer Exception at Login Screen" + e);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            AlertClass alert = new AlertClass();
            alert.customDialogforAlertMessage(this, getResources().getString(R.string.no_internet), "Please check your internet connection");
        }

    }

    WebServiceImage.CallbackImage callback = new WebServiceImage.CallbackImage() {
        @Override
        public void onSuccessImage(int reqestcode, JSONObject rootjson) {
            System.out.println("++++++-result++++++" + rootjson);
            try {
                if (rootjson.get("status").toString().equals("success")) {
                    Toast.makeText(BuyerRegistrationActivity.this, rootjson.get("description").toString(), Toast.LENGTH_SHORT).show();
                    edtOrgName.setText("");
                    edtEmailId.setText("");
                    edtContactNumber.setText("");
                    edtAddress.setText("");
                    edtPassword.setText("");
                    edtCityName.setText("");
                    edtZipCode.setText("");
                    //finish();
                    //AppUtils.replaceFragment(new FragmentOrderConfirmation(), getFragmentManager(), null);
                    startActivity(new Intent(BuyerRegistrationActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(BuyerRegistrationActivity.this, rootjson.get("description").toString(), Toast.LENGTH_SHORT).show();
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

    private boolean isValidEmail(String email) {

        Pattern pattern = Pattern.compile(AppConstants.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    private boolean validation() {
        orgName = edtOrgName.getText().toString();
        contactNumber = edtContactNumber.getText().toString();
        address = edtAddress.getText().toString();
        city = edtCityName.getText().toString();
        zipcode = edtZipCode.getText().toString();
        emailId = edtEmailId.getText().toString();
        password = edtPassword.getText().toString();

        if (orgName.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (contactNumber.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter contact number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (contactNumber.length() < 10) {
            Toast.makeText(this, "please Enter valid mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (address.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (city.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter city", Toast.LENGTH_SHORT).show();
            return false;
        } else if (zipcode.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter zipcode", Toast.LENGTH_SHORT).show();
            return false;
        } else if (emailId.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter email Id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidEmail(emailId)) {
            edtEmailId.setError("Invalid email");
            return false;
        } else if (password.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(this, "Please enter min 6 characters password  ", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
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

                    imgLicence.setVisibility(View.VISIBLE);
                    imgLicence.setImageBitmap(tempBmp);
                    btnUploadLic.setVisibility(View.GONE);

                    System.out.println("Pic file Uri====" + fileUri.getPath() + ", Bitmap-------" + bitmap);

                    UploadPhotoDialog.profile_ = fileUri.getPath().toString();

                    f = new File(Environment.getExternalStorageDirectory() + "/_.png");
                    if (f != null) {
                        try {
                            f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        tempBmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
                        // save it in your external storage.
                        FileOutputStream fo = null;
                        try {
                            fo = new FileOutputStream(f);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            fo.write(byteArray);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    System.out.println("Exception camera click--" + e.toString());
                }

            } else if (requestCode == GALLARY && resultCode == RESULT_OK && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                try {
                    File f = null;
                    if (picturePath != null)
                        f = new File(picturePath);
                    else
                        Toast.makeText(BuyerRegistrationActivity.this, "Error while rendering image.", Toast.LENGTH_SHORT).show();
                    f.createNewFile();
                    UploadPhotoDialog.profile_ = f.toString();
                    cursor.close();
                } catch (FileNotFoundException e) {
                } catch (Exception e) {
                    Toast.makeText(BuyerRegistrationActivity.this, "Error while rendering image.", Toast.LENGTH_SHORT).show();
                }
                Bitmap bmp = UploadPhotoDialog.decodeSampledBitmapFromPath(picturePath, 150, 150);
                if (bmp != null)
                    tempBmp = bmp;

                imgLicence.setVisibility(View.VISIBLE);
                imgLicence.setImageBitmap(tempBmp);
                btnUploadLic.setVisibility(View.GONE);

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

                getServiceResponseForPhoto(tempBmp);
            }
        }
    }


    public void getServiceResponseForPhoto(Bitmap bitmap) {

        // create a file to write bitmap data.
        //profile_image.setImageBitmap(tempBmp);

        f = new File(Environment.getExternalStorageDirectory() + "/_camera.png");
        if (f != null) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
            // save it in your external storage.
            FileOutputStream fo = null;
            try {
                fo = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                fo.write(byteArray);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
