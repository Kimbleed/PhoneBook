package com.example.awesoman.phonebook.Entity;

/**
 * Created by Awesome on 2016/9/29.
 */
public class PhoneTypeEntity {
    String phoneType;
    String phoneName;
    String phoneNum;

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
