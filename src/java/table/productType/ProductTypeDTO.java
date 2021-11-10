/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.productType;

import java.io.Serializable;

/**
 *
 * @author wifil
 */
public class ProductTypeDTO  implements Serializable{
    private String id;
    private String name;

    public ProductTypeDTO() {
    }

    public ProductTypeDTO(String id, String name) {
        this.id = id;
        this.name = name;
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

}
