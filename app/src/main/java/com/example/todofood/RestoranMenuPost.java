package com.example.todofood;

public class RestoranMenuPost {

    private int quantity;           //количество

    private String _id;

    private String image;

    private String title;       //название блюда

    private String description;     //состав блюда

    private String tag;

    private int price;

    public int getQuantity() {
        return quantity;
    }

    public String get_id() {
        return _id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getTag() {
        return tag;
    }
}
