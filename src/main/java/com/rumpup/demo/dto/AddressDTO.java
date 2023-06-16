package com.rumpup.demo.dto;

public class AddressDTO {
    private String street;
    private Integer houseNumber;
    private String neighborhood;
    private Integer zipCode;
    private String country;

    // Construtores, getters e setters

    public AddressDTO() {
    }

    public AddressDTO(String street, Integer houseNumber, String neighborhood, Integer zipCode, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

