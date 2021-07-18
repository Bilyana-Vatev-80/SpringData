package com.example.jsonexercise.modul.dto;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserSoldDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Set<ProductsNameAndPriceBuyerFirstAndLastNameDto> soldProduct;

    public UserSoldDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ProductsNameAndPriceBuyerFirstAndLastNameDto> getSoldProduct() {
        return soldProduct;
    }

    public void setSoldProduct(Set<ProductsNameAndPriceBuyerFirstAndLastNameDto> soldProduct) {
        this.soldProduct = soldProduct;
    }
}
