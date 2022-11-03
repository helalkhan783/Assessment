package com.example.test.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Datum {
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("populationCounts")
    @Expose
    private List<PopulationCount> populationCounts = null;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<PopulationCount> getPopulationCounts() {
        return populationCounts;
    }

    public void setPopulationCounts(List<PopulationCount> populationCounts) {
        this.populationCounts = populationCounts;
    }
}
