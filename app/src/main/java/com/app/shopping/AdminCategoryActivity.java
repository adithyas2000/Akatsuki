package com.app.shopping;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategoryActivity extends AppCompatActivity {
    private Button LogoutBtn, CheckOrdersBtn, UpdateStoreBtn, UpdateProductBtn;
    private ImageView Vegetables,fruits,seeds,herbs,fertilizers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);
        UpdateStoreBtn = (Button) findViewById(R.id.update_store_btn);
        UpdateProductBtn = (Button) findViewById(R.id.update_product_btn);


        UpdateProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,editproductactivity.class);
                startActivity(intent);
            }
        });

        UpdateStoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,updatestoreActivity.class);
                startActivity(intent);
            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });



        Vegetables = (ImageView) findViewById(R.id.Vegetables);
        fruits = (ImageView) findViewById(R.id.Fruits);
        seeds = (ImageView) findViewById(R.id.Seeds);
        herbs = (ImageView) findViewById(R.id.Herbs);
        fertilizers = (ImageView) findViewById(R.id.Fertilizers);




        Vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.app.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Vegetables");
                startActivity(intent);
            }
        });
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.app.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Fruits");
                startActivity(intent);
            }
        });
        seeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.app.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Seeds");
                startActivity(intent);
            }
        });
        herbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.app.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Herbs");
                startActivity(intent);
            }
        });
        fertilizers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.app.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Fertilizers");
                startActivity(intent);
            }
        });

    }
}
