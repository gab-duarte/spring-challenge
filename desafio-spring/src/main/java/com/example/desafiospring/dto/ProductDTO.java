package com.example.desafiospring.dto;

import javax.validation.constraints.NotBlank;

public class ProductDTO {

    @NotBlank(message = "Product name is mandatory")
    private String productName;
    @NotBlank(message = "Product type is mandatory")
    private String type;
    @NotBlank(message = "Product brand is mandatory")
    private String brand;
    private String color;
    private String notes;

    public ProductDTO(String productName, String type, String brand, String color, String notes) {
        this.productName = productName;
        this.type = type;
        this.brand = brand;
        this.color = color;
        this.notes = notes;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
