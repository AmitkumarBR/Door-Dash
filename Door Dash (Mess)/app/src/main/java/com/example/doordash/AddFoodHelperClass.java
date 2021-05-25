package com.example.doordash;

public class AddFoodHelperClass {
    public String foodName, foodPrice, foodDesc, foodImage;

    public AddFoodHelperClass() {
    }

    public AddFoodHelperClass(String foodName, String foodPrice, String foodDesc, String foodImage) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodDesc = foodDesc;
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }
}
