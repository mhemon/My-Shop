package com.xploreict.myshop;

public class HorizontalProductScrollModel {

    private int productImage;
    private String productTitle;
    private String productDescreption;
    private String productPrice;

    public HorizontalProductScrollModel(int productImage, String productTitle, String productDescreption, String productPrice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productDescreption = productDescreption;
        this.productPrice = productPrice;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescreption() {
        return productDescreption;
    }

    public void setProductDescreption(String productDescreption) {
        this.productDescreption = productDescreption;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
