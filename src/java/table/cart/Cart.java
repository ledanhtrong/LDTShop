/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wifil
 */
public class Cart implements Serializable {

    private String username;
    private Map<String, Integer> items;

    public Cart() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void addToCart(String id) {
        addToCart(id, 1);
    }

    private void addToCart(String id, int quantity) {
        if (items == null) {
            items = new HashMap<>();
        }

        if (items.containsKey(id)) {
            quantity = items.get(id) + 1;
        }

        items.put(id, quantity);
    }

    public void removeItem(String id) {
        if (items == null) {
            return;
        }

        if (items.containsKey(id)) {
            items.remove(id);
        }

        if (items.isEmpty()) {
            items = null;
        }
    }

    public void addItems(List<CartDTO> list) {
        list.forEach((CartDTO cartDTO) -> {
            String id = cartDTO.getProductId();
            int quantity = cartDTO.getQuantity();
            addToCart(id, quantity);
        });
    }

    public int getTotalQuantity() {
        if (items == null) {
            return 0;
        }
        
        int quantity = 0;
        for (String id : items.keySet()) {
            quantity += items.get(id);
        }

        return quantity;
    }

    public boolean isEmpty() {
        return items == null;
    }
}
