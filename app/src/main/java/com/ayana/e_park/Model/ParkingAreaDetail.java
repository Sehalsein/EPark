package com.ayana.e_park.Model;

/**
 * Created by sehalsein on 05/11/17.
 */

public class ParkingAreaDetail {

    private String id;
    private String parkingName;
    private String areaName;
    private BikeDetail bikeDetail;
    private CarDetail carDetail;
    private BusDetail busDetail;

    public ParkingAreaDetail() {
    }

    public ParkingAreaDetail(String parkingName, String areaName, BikeDetail bikeDetail, CarDetail carDetail, BusDetail busDetail) {
        this.parkingName = parkingName;
        this.areaName = areaName;
        this.bikeDetail = bikeDetail;
        this.carDetail = carDetail;
        this.busDetail = busDetail;
    }

    public ParkingAreaDetail(String id, String parkingName, String areaName, BikeDetail bikeDetail, CarDetail carDetail, BusDetail busDetail) {
        this.id = id;
        this.parkingName = parkingName;
        this.areaName = areaName;
        this.bikeDetail = bikeDetail;
        this.carDetail = carDetail;
        this.busDetail = busDetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public BikeDetail getBikeDetail() {
        return bikeDetail;
    }

    public void setBikeDetail(BikeDetail bikeDetail) {
        this.bikeDetail = bikeDetail;
    }

    public CarDetail getCarDetail() {
        return carDetail;
    }

    public void setCarDetail(CarDetail carDetail) {
        this.carDetail = carDetail;
    }

    public BusDetail getBusDetail() {
        return busDetail;
    }

    public void setBusDetail(BusDetail busDetail) {
        this.busDetail = busDetail;
    }
}
