package com.smkpgri2.alaska.core.commons;

import java.io.Serializable;

/**
 * Created by smkpgri2 on 13/05/16.
 */
public class Address implements Serializable{
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zip;

    public Address() {}

    public Address(String street1, String street2, String city, String state, String zip) {
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Address(Address address) {
        this(address.getStreet1(), address.getStreet2(), address.getCity(), address.getState(), address.getZip());
    }

    /**
     * @return the street1
     */
    public String getStreet1() {
        return street1;
    }

    /**
     * @param street1
     *            the street1 to set
     */
    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    /**
     * @return the street2
     */
    public String getStreet2() {
        return street2;
    }

    /**
     * @param street2
     *            the street2 to set
     */
    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip
     *            the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

}
