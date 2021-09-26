package com.akatsuki.greenarcade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackOrder extends AppCompatActivity {

    EditText et_orTel;
    Button btn_orSubmit;
    TextView tv_amount;
    TextView tv_status;
    DatabaseReference dbRef;
    TrackOrderClass toc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        et_orTel=findViewById(R.id.et_orTel);
        btn_orSubmit=findViewById(R.id.btn_orSubmit);
        tv_amount=findViewById(R.id.tv_trackAmount);
        tv_status=findViewById(R.id.tv_trackStatus);

        toc=new TrackOrderClass();



    }
    //clear data
    private void ClearControls(){
        et_orTel.setText("");
    }






    protected void onResume(){
        super.onResume();

        btn_orSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (TextUtils.isEmpty(et_orTel.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please order placed number", Toast.LENGTH_SHORT).show();
                    } else {
                        toc.setTel(Integer.parseInt(et_orTel.getText().toString().trim()));
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Invalid contact number", Toast.LENGTH_SHORT).show();
                }


                DatabaseReference readRef= FirebaseDatabase.getInstance().getReference().child("").child(toc.getTel().toString());
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            tv_amount.setText(snapshot.child("amount").toString());
                            tv_status.setText(snapshot.child("cStatus").toString());
                            ClearControls();
                        }else{
                            Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






            }
        });

    }













}