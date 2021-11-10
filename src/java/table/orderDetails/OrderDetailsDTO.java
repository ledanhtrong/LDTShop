/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.orderDetails;

import java.io.Serializable;

/**
 *
 * @author wifil
 */
public class OrderDetailsDTO implements Serializable{
    private String id;
    private String name;
    private int quantity;
    private String unit;
    private double price;
    

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String id, String name, int quantity, String unit, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return getPrice() * getQuantity();
    }

}
