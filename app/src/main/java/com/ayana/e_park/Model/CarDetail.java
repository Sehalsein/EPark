package com.ayana.e_park.Model;

/**
 * Created by sehalsein on 08/11/17.
 */

public class CarDetail {

    String capacity;
    String baseFare;
    String additionalFare;


    public CarDetail() {
    }

    public CarDetail(String capacity, String baseFare, String additionalFare) {
        this.capacity = capacity;
        this.baseFare = baseFare;
        this.additionalFare = additionalFare;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(String baseFare) {
        this.baseFare = baseFare;
    }

    public String getAdditionalFare() {
        return additionalFare;
    }

    public void setAdditionalFare(String additionalFare) {
        this.additionalFare = additionalFare;
    }
}

