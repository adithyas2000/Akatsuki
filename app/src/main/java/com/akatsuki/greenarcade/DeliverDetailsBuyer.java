package com.akatsuki.greenarcade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeliverDetailsBuyer extends AppCompatActivity {

    EditText etEmail,etName,etTel,etAddress,etNotice;
    Button btnUpdate;
    DatabaseReference dbref;
    BuyerDelivery bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_details_buyer);

        etEmail=findViewById(R.id.et_email);
        etName=findViewById(R.id.et_name);
        etTel=findViewById(R.id.et_tel);
        etAddress=findViewById(R.id.et_address);
        btnUpdate=findViewById(R.id.btn_update);

        bd=new BuyerDelivery();





    }

    private void ClearControls(){
        etEmail.setText("");
        etName.setText("");
        etTel.setText("");
        etAddress.setText("");

    }

    public void onResume(){
        super.onResume();

        //update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference upRef=FirebaseDatabase.getInstance().getReference().child("DeliveryDetails");
                upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        DatabaseReference upRef=FirebaseDatabase.getInstance().getReference().child("");
                        upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                try {
                                    if (snapshot.hasChild(bd.getTel().toString())) {

                                        bd.setUserEmail(etEmail.getText().toString().trim());
                                        bd.setName(etName.getText().toString().trim());
                                        bd.setAddress(etAddress.getText().toString().trim());

                                        dbref = FirebaseDatabase.getInstance().getReference().child("").child(bd.getTel().toString());
                                        dbref.setValue(bd);
                                        //success message
                                        Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();

                                    } else {

                                        Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();

                                    }

                                }catch (NumberFormatException e){
                                        Toast.makeText(getApplicationContext(), "Invalid contact number", Toast.LENGTH_SHORT).show();
                                    }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });













    }















}