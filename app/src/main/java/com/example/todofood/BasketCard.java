package com.example.todofood;

public class BasketCard {

    private String name;
    private String price;
    private String image;
    private String dishCount;

    public BasketCard(String Kname, String Kprice, String Kimage,String KdishCount){
        name = Kname;
        price = Kprice;
        image = Kimage;
        dishCount = KdishCount;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
    public String getDishCount() {
        return dishCount;
    }
}
