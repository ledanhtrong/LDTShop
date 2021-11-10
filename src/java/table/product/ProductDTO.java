/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.product;

import java.io.Serializable;

/**
 *
 * @author wifil
 */
public class ProductDTO implements Serializable {

    private final String DEFAULT_IMAGE = "./assets/img/default_image.png";
    
    private String id;
    private String name;
    private double price;
    private String unit;
    private int quantity;
    private String image;
    private String description;
    

    public ProductDTO() {
    }

    public ProductDTO(String id, String name, double price, String unit, int quantity, String image, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.image = image;
        if(image == null || image.isEmpty()) {
            this.image = DEFAULT_IMAGE;
        }
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

}
