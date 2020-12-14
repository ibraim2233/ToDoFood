package com.example.todofood;


public class RestoranMenuCard {
    private String name;
    private String description;
    private String price;
    private String image;

    public RestoranMenuCard(String Kname, String Kdescription, String Kprice, String Kimage){
        name = Kname;
        description = Kdescription;
        price = Kprice;
        image = Kimage;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
