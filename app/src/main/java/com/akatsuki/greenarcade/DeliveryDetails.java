package com.akatsuki.greenarcade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeliveryDetails extends AppCompatActivity {

    Button btnbuyerAdd,btnSellerAdd,btnTrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);
        btnbuyerAdd=findViewById(R.id.btn_buyerAdd);
        btnTrack=findViewById(R.id.btn_track);


    }

    public void onResume(){
        super.onResume();

        //buyer delivery details navigate
        btnbuyerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(DeliveryDetails.this,DeliverDetailsBuyer.class);
                startActivity(intent);

            }
        });

        //view seller address
        //track order
        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DeliveryDetails.this,TrackOrder.class);
                startActivity(intent);
            }
        });







    }






}