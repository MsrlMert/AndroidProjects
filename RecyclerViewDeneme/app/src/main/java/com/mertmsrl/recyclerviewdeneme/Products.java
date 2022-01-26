package com.mertmsrl.recyclerviewdeneme;

public class Products {
    private String productName, productBrand;
    private int productPrice, productYear;


    public Products(String productName, String productBrand, int productPrice, int productYear) {
        this.productName = productName;
        this.productBrand = productBrand;
        this.productPrice = productPrice;
        this.productYear = productYear;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductYear() {
        return productYear;
    }

    public void setProductYear(int productYear) {
        this.productYear = productYear;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productName='" + productName + '\'' +
                ", productBrand='" + productBrand + '\'' +
                ", productPrice=" + productPrice +
                ", productYear=" + productYear +
                '}';
    }
}
