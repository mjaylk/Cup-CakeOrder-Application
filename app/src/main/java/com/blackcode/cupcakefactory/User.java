package com.blackcode.cupcakefactory;

public class User {
    public String userId;
    public String name;
    public String phoneNumber;
    public String email;
    public String profileImageUrl;
    public String fullName;
    public String address;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getFullName() {
        return fullName;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public User( String name, String phoneNumber, String email,String userId) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
