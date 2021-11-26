package sushi.amara.com.customarapp.pojo;

public class AddFood2 {

    private String foodId;
    private  String foodName;
    private  String foodNumber;
    private  String foodDescription;
    private  String foodPrice;
    private  String imageUrl;
    private  String allergens;
    private  String type;

    public AddFood2() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AddFood2(String foodId, String foodName, String foodNumber, String foodDescription, String foodPrice, String imageUrl, String allergens) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodNumber = foodNumber;
        this.foodDescription = foodDescription;
        this.foodPrice = foodPrice;
        this.imageUrl = imageUrl;
        this.allergens = allergens;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFoodNumber() {
        return foodNumber;
    }

    public void setFoodNumber(String foodNumber) {
        this.foodNumber = foodNumber;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }
}
