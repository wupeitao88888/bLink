package com.shiliuke.bean;

/**
 * Created by lc-php1 on 2015/11/26.
 */
public class LocationBD {
    private boolean lbs;
    private double latitude;
    private double longitude;
    private String city = "";
    private String district = "";
    private String street = "";

    public LocationBD() {
    }

    public LocationBD(boolean lbs, double latitude, double longitude, String city, String district, String street) {

        this.lbs = lbs;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.district = district;
        this.street = street;
    }

    public boolean isLbs() {

        return lbs;
    }

    public void setLbs(boolean lbs) {
        this.lbs = lbs;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
