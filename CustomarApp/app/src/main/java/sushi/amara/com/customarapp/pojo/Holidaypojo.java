package sushi.amara.com.customarapp.pojo;

public class Holidaypojo {

    private String id;
    private String holi;

    public Holidaypojo(String id, String holi) {
        this.id = id;
        this.holi = holi;
    }

    public Holidaypojo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoli() {
        return holi;
    }

    public void setHoli(String holi) {
        this.holi = holi;
    }
}
