package com.sehalsein.e_park.Model;

/**
 * Created by sehalsein on 05/11/17.
 */

public class BusDetail {

    String capacity;
    String baseFare;
    String additionalFare;


    public BusDetail() {
    }

    public BusDetail(String capacity, String baseFare, String additionalFare) {
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
