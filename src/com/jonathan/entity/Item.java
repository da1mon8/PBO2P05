package com.jonathan.entity;

/**
 *
 * @author 1772004 Jonathan Bernad
 */
public class Item {

    private int id;
    private String name;
    private double price;
    private String description;
    private boolean recommended;
    private String photo;
    private Category category;

    public Item() {
    }

    public Item(int id, String name, double price, String description, boolean recommended, String photo, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.recommended = recommended;
        this.photo = photo;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
