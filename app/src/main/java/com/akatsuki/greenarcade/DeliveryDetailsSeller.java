package com.akatsuki.greenarcade;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class DeliveryDetailsSeller extends AppCompatActivity {


    EditText et_trackTel;
    RadioButton rb_toBeDispatched;
    RadioButton rb_dispatched;
    Button btn_submit;
    TrackOrderSeller tos;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details_seller);

        et_trackTel=findViewById(R.id.et_trackTel);
        rb_dispatched=findViewById(R.id.rb_dispatched);
        rb_toBeDispatched=findViewById(R.id.rb_toBedispatched);
        btn_submit=findViewById(R.id.btn_trackSave);



    }


    protected void onResume(){
        super.onResume();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






            }
        });




    }

















}