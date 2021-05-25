package com.example.doordash;

public class AddMessHelperClass {
    String messName, messPhone, messRatings, messImage;

    public AddMessHelperClass() {
    }

    public AddMessHelperClass(String messName, String messPhone, String messRatings, String messImage) {
        this.messName = messName;
        this.messPhone = messPhone;
        this.messRatings = messRatings;
        this.messImage = messImage;
    }

    public String getMessName() {
        return messName;
    }

    public void setMessName(String messName) {
        this.messName = messName;
    }

    public String getMessPhone() {
        return messPhone;
    }

    public void setMessPhone(String messPhone) {
        this.messPhone = messPhone;
    }

    public String getMessRatings() {
        return messRatings;
    }

    public void setMessRatings(String messRatings) {
        this.messRatings = messRatings;
    }

    public String getMessImage() {
        return messImage;
    }

    public void setMessImage(String messImage) {
        this.messImage = messImage;
    }

}
