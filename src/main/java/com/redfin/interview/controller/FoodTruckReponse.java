package com.redfin.interview.controller;

public class FoodTruckReponse {
    private String truckName;
    private String address;

    public FoodTruckReponse(String truckName, String address) {
        this.address = address;
        this.truckName = truckName;
    }

    public String getTruckName() {
        return truckName;
    }

    public String getAddress() {
        return address;
    }

}
