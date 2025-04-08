package org.example;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Product {

    @Id
    private String productID;
    private String productName;
    private String productCategory;
    private int productQuantity;
    private String productUnit;

    @ManyToOne
    @JoinColumn(name = "dataWarehouse_id")
    @JsonBackReference
    private DataWarehouse dataWarehouse;

    // Getters and Setters
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public DataWarehouse getDataWarehouse() {
        return dataWarehouse;
    }

    public void setDataWarehouse(DataWarehouse dataWarehouse) {
        this.dataWarehouse = dataWarehouse;
    }
}
