package com.app.shopping.Model;

public class Admins {
    private String name, phone, password, image, store_address,Available_item,Available_item1,Available_item2,Available_item3,Available_item4,Producttype,Producttype1,store_name;
    public Admins()
    {

    }

    public Admins(String name, String phone, String password, String image, String store_address, String Available_item, String Available_item1, String Available_item2, String Available_item3, String Available_item4, String Producttype, String Producttype1,String store_name) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.store_address = store_address;
        this.Available_item = Available_item;
        this.Available_item1 = Available_item1;
        this.Available_item2 = Available_item2;
        this.Available_item3 = Available_item3;
        this.Available_item4 = Available_item4;
        this.Producttype = Producttype;
        this.Producttype1 = Producttype1;
        this.store_name = store_name;
        this.store_address = store_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return store_address;
    }

    public void setAddress(String address) {
        this.store_address = address;
    }
}

