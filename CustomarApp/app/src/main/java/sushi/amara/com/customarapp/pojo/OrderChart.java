package sushi.amara.com.customarapp.pojo;

import java.io.Serializable;

public class OrderChart implements Serializable {

    private String productId;
    private String productName;
    private String productDiscription;
    private String productQuantity;
    private String productPrice;
    private String note;

    public OrderChart() {
    }

    public OrderChart(String productId, String productName, String productDiscription, String productQuantity, String productPrice, String note) {
        this.productId = productId;
        this.productName = productName;
        this.productDiscription = productDiscription;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.note = note;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductDiscription() {
        return productDiscription;
    }

    public void setProductDiscription(String productDiscription) {
        this.productDiscription = productDiscription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "OrderChart{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDiscription='" + productDiscription + '\'' +
                ", productQuantity='" + productQuantity + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
