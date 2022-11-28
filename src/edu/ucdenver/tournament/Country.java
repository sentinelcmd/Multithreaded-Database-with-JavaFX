package edu.ucdenver.tournament;

import java.io.Serializable;

/**
 * Country contains functions that allow getting and setting a countries name
 */
public class Country {
    private String countryName;

    public Country(String countryName) {
        this.countryName = countryName;
    }
    public String getCountryName() {
        return this.countryName;
    }

    @Override
    public String toString() {
        return String.format("Country: %s|",getCountryName());
    }
}
