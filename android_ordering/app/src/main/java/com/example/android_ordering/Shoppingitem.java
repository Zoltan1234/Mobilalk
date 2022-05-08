package com.example.android_ordering;

public class Shoppingitem {
    private String id;
    private String name;
    private String info;
    private String price;
    private float rated;
    private int imageRes;
    private int amount;

    public Shoppingitem(String id ,String name, String info, String price, float rated, int imageRes, int amount){
        this.id = id;
        this.imageRes = imageRes;
        this.name = name;
        this.rated = rated;
        this.info = info;
        this.price = price;
        this.amount = amount;


    }
    public Shoppingitem(){}

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName(){return name;}
    public String getInfo(){return info;}
    public String getPrice(){return price;}
    float getRated(){return rated;}
    public int getImageRes(){return imageRes;}

}
