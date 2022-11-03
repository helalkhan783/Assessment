package com.example.test.database;

public class User {
    String id,   name, country, city, language, resume, dateOfBirth;

    public User(String id, String name, String country, String city, String language, String resume, String dateOfBirth) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.language = language;
        this.resume = resume;
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getLanguage() {
        return language;
    }

    public String getResume() {
        return resume;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
