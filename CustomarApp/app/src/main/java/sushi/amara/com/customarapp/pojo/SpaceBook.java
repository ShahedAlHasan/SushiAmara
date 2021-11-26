package sushi.amara.com.customarapp.pojo;

public class SpaceBook {

    private String id;
    private String store;
    private String guest;
    private String date;
    private String time;
    private String foodchoice;
    private String name;
    private String fonNumber;
    private String mail;
    private String status;



    public SpaceBook() {
    }

    public SpaceBook(String id, String store, String guest, String date, String time, String foodchoice, String name, String fonNumber, String mail, String status) {
        this.id = id;
        this.store = store;
        this.guest = guest;
        this.date = date;
        this.time = time;
        this.foodchoice = foodchoice;
        this.name = name;
        this.fonNumber = fonNumber;
        this.mail = mail;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFoodchoice() {
        return foodchoice;
    }

    public void setFoodchoice(String foodchoice) {
        this.foodchoice = foodchoice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFonNumber() {
        return fonNumber;
    }

    public void setFonNumber(String fonNumber) {
        this.fonNumber = fonNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
