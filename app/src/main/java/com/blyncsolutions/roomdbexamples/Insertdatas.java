package com.blyncsolutions.roomdbexamples;

import android.Manifest;
import android.annotation.TargetApi;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laptopzone on 17-01-2018.
 */

public class Insertdatas extends AppCompatActivity {

    EditText et_name,et_mail,et_age,et_blood,et_address;
    Button insert,view,update;
    String str_name="",str_mail="",str_age="",str_blood="",str_address="";
    public static View parentLayout;
    public static AppDatabase db;
    String userChoosenTask="", intent_name="",intent_mail="",intent_age="",intent_blood="",intent_address="";
    int intent_id;
    Bitmap photo2 = null;
    private byte[] photo,intent_img=null,update_img=null;
    private Bitmap bp= null,Bpedit=null;
    private ImageView pic;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertdatas);




        parentLayout = findViewById(android.R.id.content);

        pic= (ImageView) findViewById(R.id.pic);
        et_name = (EditText)findViewById(R.id.name);
        et_mail = (EditText)findViewById(R.id.email);
        et_age = (EditText)findViewById(R.id.age);
        et_blood = (EditText)findViewById(R.id.blood);
        et_address = (EditText)findViewById(R.id.address);
        insert = (Button)findViewById(R.id.register);
        update = (Button)findViewById(R.id.update);
        view = (Button)findViewById(R.id.view);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertdatas();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedatas();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backto();
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkversion();
            }
        });


        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"production")
                .allowMainThreadQueries()
                .build();

        if(MainActivity.intentkey == 0)
        {
            update.setVisibility(View.GONE);
        }
        else
        {
            Bundle b = getIntent().getExtras();
            System.out.println("below lollipop");
            if (b != null) {

                intent_id = b.getInt("student_id");
                intent_name = b.getString("student_name");
                intent_mail = b.getString("student_mail");
                intent_age = b.getString("student_age");
                intent_blood = b.getString("student_blood");
                intent_address = b.getString("getAddress");
                intent_img = b.getByteArray("getImg");


                et_name.setText(intent_name);
                et_mail.setText(intent_mail);
                et_age.setText(intent_age);
                et_blood.setText(intent_blood);
                et_address.setText(intent_address);
                pic.setImageBitmap(convertToBitmap(intent_img));

                insert.setVisibility(View.GONE);
            }
            else
            {
                System.out.println("Bundle Error");
            }
        }

    }


    public void clearvalues()
    {
        str_name = "";
        str_mail = "";
        str_age = "";
        str_blood = "";
        str_address = "";
        et_name.setText("");
        et_mail.setText("");
        et_age.setText("");
        et_blood.setText("");
        et_address.setText("");
        photo = null;
        photo2 = null;
        bp = null;
    }

    public void onBackPressed() {
        backto();
    }



    public void backto()
    {
        Intent ii = new Intent(Insertdatas.this,MainActivity.class);
        startActivity(ii);
        finish();
    }

    public void insertdatas()
    {
        str_name = et_name.getText().toString();
        str_mail = et_mail.getText().toString();
        str_age = et_age.getText().toString();
        str_blood = et_blood.getText().toString();
        str_address = et_address.getText().toString();


        if(photo2 == null && bp==null)
        {
            Toast.makeText(Insertdatas.this ,"Please select image", Toast.LENGTH_SHORT).show();
        }
        else
        {

            if (photo2 != null) {
                System.out.println("bp is null");

                photo = profileImage(photo2);



                db.studentsDao().insertAll(new WorldPopulation(str_name,str_mail,str_age,str_blood,str_address,photo));
                final Snackbar snackbar = Snackbar.make(parentLayout, "Student Details Inserted Successfully", Snackbar.LENGTH_LONG);
                snackbar.show();

                Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();

                clearvalues();
                backto();

            } else if (bp != null) {
                System.out.println("photo2 is null");

                photo = profileImage(bp);

                db.studentsDao().insertAll(new WorldPopulation(str_name,str_mail,str_age,str_blood,str_address,photo));
                final Snackbar snackbar = Snackbar.make(parentLayout, "Student Details Inserted Successfully", Snackbar.LENGTH_LONG);
                snackbar.show();

                Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();

                clearvalues();
                backto();
            }


        }



    }

    public void updatedatas()
    {
        str_name = et_name.getText().toString();
        str_mail = et_mail.getText().toString();
        str_age = et_age.getText().toString();
        str_blood = et_blood.getText().toString();
        str_address = et_address.getText().toString();

        if(photo2 == null && bp==null)
        {
            db.studentsDao().updatebyId(intent_id,str_name,str_mail,str_age,str_blood,str_address,intent_img);

            clearvalues();

            backto();
        }
        else
        {
            if (photo2 != null) {
                System.out.println("bp is null");

                photo = profileImage(photo2);



                db.studentsDao().updatebyId(intent_id,str_name,str_mail,str_age,str_blood,str_address,photo);
                final Snackbar snackbar = Snackbar.make(parentLayout, "Student Details Updated Successfully", Snackbar.LENGTH_LONG);
                snackbar.show();

                Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();

                clearvalues();
                backto();

            } else if (bp != null) {
                System.out.println("photo2 is null");

                photo = profileImage(bp);

                db.studentsDao().updatebyId(intent_id,str_name,str_mail,str_age,str_blood,str_address,photo);
                final Snackbar snackbar = Snackbar.make(parentLayout, "Student Details Updated Successfully", Snackbar.LENGTH_LONG);
                snackbar.show();

                Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();

                clearvalues();
                backto();
            }
        }



    }

    private void checkversion() {

        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            System.out.println("above lollipop");
            getRuntimePermission();
        } else {
            // Pre-Marshmallow
            selectphoto();
            System.out.println("below lollipop");

        }


    }

    private void selectphoto() {


        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Insertdatas.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result=Utility.checkPermission(IncomeActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";

                    callCamera();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";

                    selectImage();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void callCamera() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    public void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {

            case 1:
                try{

                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        photo2 = null;
                        photo2 = (Bitmap) extras.get("data");
                        pic.setImageBitmap(photo2);
                    }

                }
                catch(Exception e)
                {
                    System.out.println("Bundle Erorr");
                }

                break;


            case 2:
                if(resultCode == RESULT_OK){
                    Uri choosenImage = data.getData();
                    String selectedPath = getPath(choosenImage);
                    if(choosenImage !=null){

                        bp = null;
                        bp=decodeUri(choosenImage, 400);
                        Bitmap orientedBitmap = ExifUtil.rotateBitmap(selectedPath, bp);
                        pic.setImageBitmap(orientedBitmap);
                        //editimagepro.setImageBitmap(bp);
                    }
                    else
                    {
                        Toast.makeText(this, "not choosen", Toast.LENGTH_SHORT).show();
                    }
                }



        }
    }


    private String getPath(Uri selectedImageUri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }


    //COnvert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }

    //permission coding

    @TargetApi(Build.VERSION_CODES.M)
    private void getRuntimePermission() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();

        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");

        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

        selectphoto();


    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial

                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);


                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    selectphoto();
                    // All Permissions Granted

                } else {
                    // Permission Denied
                    //Toast.makeText(SplashActivity_V15.this, "Some Permission is Denied", Toast.LENGTH_SHORT).show();
                    final Snackbar snackbar = Snackbar.make(parentLayout, "Please allow camera and storage permissions", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //snackbar.dismiss();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", getPackageName(), null));
                            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(intent,1);
                        }
                    });

                    snackbar.show();
                    //startActivity(new Intent(SplashActivity.this, SplashActivity.class));
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //end permission coding



}