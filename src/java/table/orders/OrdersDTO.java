/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.orders;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import table.orderDetails.OrderDetailsDTO;

/**
 *
 * @author wifil
 */
public class OrdersDTO implements Serializable{
    private String username;
    private String buyer;
    private String receiver;
    private String address;
    private String phone;
    private Timestamp paidDay;
    private List<OrderDetailsDTO> details;

    public OrdersDTO() {
    }

    public OrdersDTO(String username, String buyer, String receiver, String address, String phone, Timestamp paidDay, List<OrderDetailsDTO> details) {
        this.username = username;
        this.buyer = buyer;
        this.receiver = receiver;
        this.address = address;
        this.phone = phone;
        this.paidDay = paidDay;
        this.details = details;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getPaidDay() {
        return paidDay;
    }

    public void setPaidDay(Timestamp paidDaye) {
        this.paidDay = paidDaye;
    }

    public List<OrderDetailsDTO> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailsDTO> details) {
        this.details = details;
    }
    
    public double getSubtotal() {
        double subtotal = 0.0;
        for (OrderDetailsDTO detail : details) {
            subtotal += detail.getTotal();
        }
        return subtotal;
    }
}
