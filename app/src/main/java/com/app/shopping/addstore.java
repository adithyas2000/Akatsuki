package com.app.shopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
public class addstore extends AppCompatActivity {
    private String store_name, store_address,saveCurrentDate, saveCurrentTime, owner_name, password, phone_number;
    private ImageView InputStoreImage;
    private EditText InputStoreName, InputStoreAddress,InputOwnerName,InputStorePassword,InputPhoneNumber;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String storeRandomKey, downloadImageUrl;
    private StorageReference StoreImagesRef;
    private DatabaseReference AdminsRef;
    private ProgressDialog loadingBar;
    private CheckBox Vegitables,Fruits,Seeds,Herbs,Fertilizers;
    private CheckBox organic,inorganic;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_store);


//        store_name = getIntent().getExtras().get("store_name").toString();
        StoreImagesRef = FirebaseStorage.getInstance().getReference().child("Store Images");
        AdminsRef = FirebaseDatabase.getInstance().getReference().child("Admins");


        Button addNewStoreButton = findViewById(R.id.add_new_store);
        InputStoreImage = (ImageView) findViewById(R.id.select_store_image);
        InputStoreAddress = (EditText) findViewById(R.id.store_address);
        InputStoreName = (EditText) findViewById(R.id.store_name);
        InputOwnerName = (EditText) findViewById(R.id.owner_name);
        InputStorePassword = (EditText) findViewById(R.id.password);
        InputPhoneNumber = (EditText) findViewById(R.id.phone_number);
        Vegitables = (CheckBox)findViewById(R.id.vegitables);
        Vegitables.setTag("Vegitables");

        Fruits = (CheckBox)findViewById(R.id.fruits);
        Fruits.setTag("Fruits");

        Seeds = (CheckBox)findViewById(R.id.seeds);
        Seeds.setTag("Seeds");

        Herbs = (CheckBox)findViewById(R.id.herbs);
        Herbs.setTag("Herbs");

        Fertilizers = (CheckBox)findViewById(R.id.fertilizers);
        Fertilizers.setTag("Fertilizers");
        organic = (CheckBox) findViewById(R.id.male);
        organic.setTag("organic");
        inorganic = (CheckBox) findViewById(R.id.female);
        inorganic.setTag("inorganic");

        loadingBar = new ProgressDialog(this);


        InputStoreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });


        addNewStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();
            }
        });
    }


    private void OpenGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputStoreImage.setImageURI(ImageUri);
        }
    }
    private void ValidateProductData() {
        store_address = InputStoreAddress.getText().toString();
        store_name = InputStoreName.getText().toString();
        owner_name = InputOwnerName.getText().toString();
        password = InputStorePassword.getText().toString();
        phone_number = InputPhoneNumber.getText().toString();


         if (TextUtils.isEmpty(store_address))
        {
            Toast.makeText(this, "Please write store address...", Toast.LENGTH_SHORT).show();
        }
         else if(TextUtils.isEmpty(store_name))
         {
             Toast.makeText(this, "Please write store name...",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(owner_name))
         {
             Toast.makeText(this, "Please write Owner name...",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(password))
         {
             Toast.makeText(this, "Please write password...",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(phone_number))
         {
             Toast.makeText(this, "Please write phone number...",Toast.LENGTH_SHORT).show();
         }
        else
        {
            StoreProductInformation();
        }

    }
    private void StoreProductInformation()
    {
        loadingBar.setTitle("Add New Store");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new store.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

//        Calendar calendar = Calendar.getInstance();
//
//        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
//        saveCurrentDate = currentDate.format(calendar.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime = currentTime.format(calendar.getTime());

        storeRandomKey = phone_number;


        final StorageReference filePath = StoreImagesRef.child(ImageUri.getLastPathSegment() + storeRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(addstore.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(addstore.this, "Store Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(addstore.this, "got the Store image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveStoreInfoToDatabase();
                        }
                    }
                });
            }
        });

    }
    private void SaveStoreInfoToDatabase()
    {
//        Log.e("ITEMS","TEST");

        HashMap<String, Object> AdminsMap = new HashMap<>();
        AdminsMap.put("date", saveCurrentDate);
        AdminsMap.put("time", saveCurrentTime);
        AdminsMap.put("store_address", store_address);
        AdminsMap.put("image", downloadImageUrl);
        AdminsMap.put("store_name", store_name);
        AdminsMap.put("name", owner_name);
        AdminsMap.put("phone", phone_number);
        AdminsMap.put("password", password);
        String Ivegi = "";
        String Ifru = "";
        String Iseed = "";
        String Iherb = "";
        String Ifert = "";
        String m1 = "";
        String m2 = "";
        try {
            if(Vegitables.isChecked()){
                Ivegi = Vegitables.getTag().toString();
                Log.e("Veg", String.valueOf(Vegitables));
            }
            if(Fruits.isChecked()){
                Ifru = Fruits.getTag().toString();

            }
            if(Seeds.isChecked()){
                Iseed = Seeds.getTag().toString();

            }
            if(Herbs.isChecked()){
                Iherb = Herbs.getTag().toString();

            }
            if(Fertilizers.isChecked()){
                Ifert = Fertilizers.getTag().toString();

            }
        }catch (Exception e){

        }

        AdminsMap.put("Available_item",Ivegi);
        AdminsMap.put("Available_item1",Ifru);
        AdminsMap.put("Available_item2",Iseed);
        AdminsMap.put("Available_item3",Iherb);
        AdminsMap.put("Available_item4",Ifert);
        try {
            if (organic.isChecked()) {
                m1 = organic.getTag().toString();
            }
            if (inorganic.isChecked()) {
                m2 = inorganic.getTag().toString();
            }
        }catch (Exception e){

        }
        AdminsMap.put("Producttype",m1);
        AdminsMap.put("Producttype1",m2);

        Log.e("ITEMS",Ivegi);
        AdminsRef.child(storeRandomKey).updateChildren(AdminsMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(addstore.this, MainActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(addstore.this, "Store is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(addstore.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            Log.e("FB ERROR",message);
                        }
                    }
                });
    }
}
