package sushi.amara.com.customarapp.pojo;

import java.io.Serializable;
import java.util.List;

public class ConfirmOrderMain implements Serializable {

    private String orderID;
    private List<OrderChart> chartList;
    private String location;
    private String system;
    private String tottalPrice;
    private String includeVatTotalPrice;
    private String store;
    private String time;
    private String timeNote;
    private String name;
    private String email;
    private String phone;
    private String group;
    private String note;
    private String distance;
    private String distancetime;
    private String payType;


    public ConfirmOrderMain() {
    }

    public ConfirmOrderMain(String orderID, List<OrderChart> chartList, String location, String system, String tottalPrice, String includeVatTotalPrice, String store, String time, String timeNote, String name, String email, String phone, String group, String note, String distance, String distancetime, String payType) {
        this.orderID = orderID;
        this.chartList = chartList;
        this.location = location;
        this.system = system;
        this.tottalPrice = tottalPrice;
        this.includeVatTotalPrice = includeVatTotalPrice;
        this.store = store;
        this.time = time;
        this.timeNote = timeNote;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.group = group;
        this.note = note;
        this.distance = distance;
        this.distancetime = distancetime;
        this.payType = payType;
    }

    public ConfirmOrderMain(String orderID, List<OrderChart> chartList, String location, String system, String tottalPrice, String includeVatTotalPrice, String store, String time, String timeNote, String name, String email, String phone, String group, String note, String distance, String distancetime) {
        this.orderID = orderID;
        this.chartList = chartList;
        this.location = location;
        this.system = system;
        this.tottalPrice = tottalPrice;
        this.includeVatTotalPrice = includeVatTotalPrice;
        this.store = store;
        this.time = time;
        this.timeNote = timeNote;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.group = group;
        this.note = note;
        this.distance = distance;
        this.distancetime = distancetime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getDistancetime() {
        return distancetime;
    }

    public void setDistancetime(String distancetime) {
        this.distancetime = distancetime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTimeNote() {
        return timeNote;
    }

    public void setTimeNote(String timeNote) {
        this.timeNote = timeNote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getIncludeVatTotalPrice() {
        return includeVatTotalPrice;
    }

    public void setIncludeVatTotalPrice(String includeVatTotalPrice) {
        this.includeVatTotalPrice = includeVatTotalPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public List<OrderChart> getChartList() {
        return chartList;
    }

    public void setChartList(List<OrderChart> chartList) {
        this.chartList = chartList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTottalPrice() {
        return tottalPrice;
    }

    public void setTottalPrice(String tottalPrice) {
        this.tottalPrice = tottalPrice;
    }
}
