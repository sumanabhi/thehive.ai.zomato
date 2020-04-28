package com.animusabhi.zomato_search.model;

public class Location {
    private String city_id;

    private String locality_verbose;

    private String address;

    private String country_id;

    private String zipcode;

    private String locality;

    private String longitude;

    private String latitude;

    private String city;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getLocality_verbose() {
        return locality_verbose;
    }

    public void setLocality_verbose(String locality_verbose) {
        this.locality_verbose = locality_verbose;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ClassPojo [city_id = " + city_id + ", locality_verbose = " + locality_verbose + ", address = " + address + ", country_id = " + country_id + ", zipcode = " + zipcode + ", locality = " + locality + ", longitude = " + longitude + ", latitude = " + latitude + ", city = " + city + "]";
    }
}

