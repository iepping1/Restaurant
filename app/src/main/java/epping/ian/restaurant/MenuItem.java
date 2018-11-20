package epping.ian.restaurant;

import java.io.Serializable;

public class MenuItem implements Serializable{

    //create class for all menu fields
    private String name, description, imageURL, category;
    private double price;

    //store the fields of the menu
    public MenuItem(String name, String description, String imageURL, Double price, String category) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
        this.category = category;
    }

    // retrieve the fields
    String getName() { return name; }

    String getDescription() { return description; }

    String getImageURL() { return imageURL; }

    Double getPrice() { return price; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {this.imageURL = imageURL; }
}
