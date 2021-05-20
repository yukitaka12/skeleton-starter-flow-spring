package org.vaadin.example.productview;

import java.util.stream.Stream;

public class Product {

    private String name;
    private String details;
    private int quantity;

    public Product(String name, String details, int quantity) {
        this.name = name;
        this.details = details;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
