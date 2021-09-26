package com.app.shopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.shopping.Prevalent.Prevalent;
import com.app.shopping.Prevalent.Prevalents;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

//import de.hdodenhof.circleimageview.CircleImageView;


public class updatestoreActivity extends AppCompatActivity {
//    private CircleImageView profileImageView;
    private EditText OwnernameEdit, PasswordEdit, PhoneNumberEdit, StoreNameEdit, StoreAddressEdit;
    private CheckBox VegitablesEdit, FruitsEdit, SeedsEdit, HerbsEdit, FertilizersEdit, MaleEdit, FemaleEdit;
    private TextView profileChangeTextBtn, closeTextBtn, saveTextButton;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_store);
        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Store images");

//        profileImageView = (CircleImageView) findViewById(R.id.select_store_image);
        OwnernameEdit = (EditText) findViewById(R.id.owner_name);
        PasswordEdit = (EditText) findViewById(R.id.password);
        PhoneNumberEdit = (EditText) findViewById(R.id.phone_number);
//        profileChangeTextBtn = (TextView) findViewById(R.id.select_store_image);
        StoreNameEdit = (EditText) findViewById(R.id.store_name);
        StoreAddressEdit = (EditText) findViewById(R.id.store_address);
        VegitablesEdit = (CheckBox) findViewById(R.id.vegitables);
        FruitsEdit = (CheckBox) findViewById(R.id.fruits);
        SeedsEdit = (CheckBox) findViewById(R.id.seeds);
        HerbsEdit = (CheckBox) findViewById(R.id.herbs);
        FertilizersEdit = (CheckBox) findViewById(R.id.fertilizers);
        MaleEdit = (CheckBox) findViewById(R.id.male);
        FemaleEdit = (CheckBox) findViewById(R.id.female);
        closeTextBtn = (TextView) findViewById(R.id.close_settings_btn);
        saveTextButton = (TextView) findViewById(R.id.update_account_settings_btn);

        userInfoDisplay(PhoneNumberEdit, StoreNameEdit, OwnernameEdit, StoreAddressEdit,VegitablesEdit,FruitsEdit,SeedsEdit,HerbsEdit,FertilizersEdit,MaleEdit,FemaleEdit);

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")) {
                    uploadImage();
                } else {
                    updateOnlyUserInfo();
                }
            }
        });

//        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checker = "clicked";
//
//                CropImage.activity(imageUri)
//                        .setAspectRatio(1, 1)
//                        .start(updatestoreActivity.this);
//            }
//        });


    }

    private void updateOnlyUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admins");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("Available_item", VegitablesEdit.getText().toString());
        userMap.put("Available_item1", FruitsEdit.getText().toString());
        userMap.put("Available_item2", SeedsEdit.getText().toString());
        userMap.put("Available_item3", HerbsEdit.getText().toString());
        userMap.put("Available_item4", FertilizersEdit.getText().toString());
        userMap.put("Producttype", MaleEdit.getText().toString());
        userMap.put("Producttype1", FemaleEdit.getText().toString());
        userMap.put("store_address", StoreAddressEdit.getText().toString());
        userMap.put("store_name", StoreNameEdit.getText().toString());
        userMap.put("phone", PhoneNumberEdit.getText().toString());
        userMap.put("password", PasswordEdit.getText().toString());
        userMap.put("name", OwnernameEdit.getText().toString());
        ref.child(Prevalents.currentOnlineUsers.getphone()).updateChildren(userMap);
        startActivity(new Intent(updatestoreActivity.this, updatestoreActivity.class));
        Toast.makeText(updatestoreActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

//            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(updatestoreActivity.this, SettinsActivity.class));
            finish();
        }
    }

    private void userInfoSaved()
    {
        if (TextUtils.isEmpty(StoreAddressEdit.getText().toString()))
        {
            Toast.makeText(this, "Address is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(StoreNameEdit.getText().toString()))
        {
            Toast.makeText(this, " Store Name is address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PhoneNumberEdit.getText().toString()))
        {
            Toast.makeText(this, "Phone Number is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PasswordEdit.getText().toString()))
        {
            Toast.makeText(this, "Password is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(OwnernameEdit.getText().toString()))
        {
            Toast.makeText(this, "Owner Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))
        {
            uploadImage();
        }
    }

    private void uploadImage() {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Update Profile");
                progressDialog.setMessage("Please wait, while we are updating your account information");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
        if (imageUri != null) {
            final StorageReference fileRef = storageProfilePrictureRef
                    .child(Prevalent.currentOnlineUser.getPhone() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admins");
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("Available_item", VegitablesEdit.getText().toString());
                        userMap.put("Available_item1", FruitsEdit.getText().toString());
                        userMap.put("Available_item2", SeedsEdit.getText().toString());
                        userMap.put("Available_item3", HerbsEdit.getText().toString());
                        userMap.put("Available_item4", FertilizersEdit.getText().toString());
                        userMap.put("Producttype", MaleEdit.getText().toString());
                        userMap.put("Producttype1", FemaleEdit.getText().toString());
                        userMap.put("store_address", StoreAddressEdit.getText().toString());
                        userMap.put("store_name", StoreNameEdit.getText().toString());
                        userMap.put("phone", PhoneNumberEdit.getText().toString());
                        userMap.put("password", PasswordEdit.getText().toString());
                        userMap.put("name", OwnernameEdit.getText().toString());
                        userMap.put("image", myUrl);
                        ref.child(Prevalents.currentOnlineUsers.getphone()).updateChildren(userMap);
                        progressDialog.dismiss();
                        startActivity(new Intent(updatestoreActivity.this, AdminCategoryActivity.class));
                        Toast.makeText(updatestoreActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(updatestoreActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
                    Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();

        }

    }

    private void userInfoDisplay(final EditText phoneNumberEdit, final EditText StoreNameEdit, final EditText OwnernameEdit, final EditText StoreAddressEdit, final CheckBox VegitablesEdit, final  CheckBox FruitsEdit, final  CheckBox SeedsEdit, final  CheckBox HerbsEdit, final  CheckBox FertilizersEdit, final  CheckBox MaleEdit,final CheckBox FemaleEdit ) {
        DatabaseReference AdminsRef = FirebaseDatabase.getInstance().getReference().child("Admins").child(Prevalents.currentOnlineUsers.getphone());
        AdminsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("phone").exists()) {
                        String storename = dataSnapshot.child("store_name").getValue().toString();
                        String phonenumber = dataSnapshot.child("phone").getValue().toString();
                        String ownername = dataSnapshot.child("name").getValue().toString();
                        String storeaddress = dataSnapshot.child("store_address").getValue().toString();
                        String vegetable = dataSnapshot.child("Available_item").getValue().toString();
                        String fruits = dataSnapshot.child("Available_item1").getValue().toString();
                        String seeds = dataSnapshot.child("Available_item2").getValue().toString();
                        String herbs = dataSnapshot.child("Available_item3").getValue().toString();
                        String fertilizers = dataSnapshot.child("Available_item4").getValue().toString();
                        String male = dataSnapshot.child("Producttype").getValue().toString();
                        String female = dataSnapshot.child("Producttype1").getValue().toString();
                        String checked ="";


                        StoreNameEdit.setText(storename);
                        phoneNumberEdit.setText(phonenumber);
                        OwnernameEdit.setText(ownername);
                        StoreAddressEdit.setText(storeaddress);
                        if(vegetable == "Vegitables"){
                            VegitablesEdit.setText("CHECKED");
                        }
                        if(fruits == "Fruits"){
                            FruitsEdit.setText("true");
                        }
                        if(seeds == checked){
                            SeedsEdit.setText("true");
                        }
                        if(herbs == checked){
                            HerbsEdit.setText("true");
                        }
                        if(fertilizers == checked){
                            FertilizersEdit.setText("true");
                        }
                        if(male == checked){
                            MaleEdit.setText("true");
                        }
                        if(female == checked){
                            FemaleEdit.setText("true");
                        }



                    }
                }

            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }





}