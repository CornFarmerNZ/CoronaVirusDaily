/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.CovidStats.Models;

/**
 *
 * @author tim
 */
public class LocationStats {

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the latestTotal
     */
    public int getLatestTotal() {
        return latestTotal;
    }

    /**
     * @param latestTotal the latestTotal to set
     */
    public void setLatestTotal(int latestTotal) {
        this.latestTotal = latestTotal;
    }

    public void setIncrease(int increase) {
        this.increase = increase;
    }

    public int getIncrease() {
        return this.increase;
    }

    private String state;
    private String country;
    private int latestTotal;
    private int increase;


    public String toString() {
        return " " + this.country + " " + this.state + " " + this.latestTotal + "(+" + this.increase + ")";
    }

}
