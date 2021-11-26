package sushi.amara.com.customarapp.pojo;

import java.io.Serializable;

public class PaymentPojo implements Serializable {

    private String id;
    private String type;
    private String totalpayment;
    private String paymetId;
    private String paymentStatus;

    public PaymentPojo() {
    }

    public PaymentPojo(String id, String type, String totalpayment, String paymetId, String paymentStatus) {
        this.id = id;
        this.type = type;
        this.totalpayment = totalpayment;
        this.paymetId = paymetId;
        this.paymentStatus = paymentStatus;
    }

    public String getPaymetId() {
        return paymetId;
    }

    public void setPaymetId(String paymetId) {
        this.paymetId = paymetId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotalpayment() {
        return totalpayment;
    }

    public void setTotalpayment(String totalpayment) {
        this.totalpayment = totalpayment;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
