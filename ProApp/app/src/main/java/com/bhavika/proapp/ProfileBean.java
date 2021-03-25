package com.bhavika.proapp;

import java.io.Serializable;

/**
 * Created by bhavika on 06-11-2016.
 */
public class ProfileBean implements Serializable {

    int id;
    String name, email, phone1, phone2, gender, city, candidShoots, cinemaShoots, studioShoots, preShoots,
            experience, payTerms, travelCost, username, password;

    public ProfileBean() {

    }

    public ProfileBean(int id, String name, String email, String phone1, String phone2, String gender, String city, String candidShoots, String cinemaShoots,
                       String studioShoots, String preShoots, String experience, String payTerms,
                       String travelCost, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.gender = gender;
        this.city = city;
        this.candidShoots = candidShoots;
        this.cinemaShoots = cinemaShoots;
        this.studioShoots = studioShoots;
        this.preShoots = preShoots;
        this.experience = experience;
        this.payTerms = payTerms;
        this.travelCost = travelCost;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "profileBean{" +
                "id=" + id +
                ", name='" + name + '\'' + "\n" +
                ", email='" + email + '\'' + "\n" +
                ", phone1='" + phone1 + '\'' + "\n" +
                ", phone2='" + phone2 + '\'' + "\n" +
                ", gender='" + gender + '\'' + "\n" +
                ", city='" + city + '\'' + "\n" +
                ", candidShoots='" + candidShoots + '\'' + "\n" +
                ", cinemaShoots='" + cinemaShoots + '\'' + "\n" +
                ", studioShoots='" + studioShoots + '\'' + "\n" +
                ", preShoots='" + preShoots + '\'' + "\n" +
                ", experience='" + experience + '\'' + "\n" +
                ", payTerms='" + payTerms + '\'' + "\n" +
                ", travelCost='" + travelCost + '\'' + "\n" +
                ", username='" + username + '\'' + "\n" +
                ", pass='" + password + '\'' + "\n" +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCandidShoots() {
        return candidShoots;
    }

    public void setCandidShoots(String candidShoots) {
        this.candidShoots = candidShoots;
    }

    public String getCinemaShoots() {
        return cinemaShoots;
    }

    public void setCinemaShoots(String cinemaShoots) {
        this.cinemaShoots = cinemaShoots;
    }

    public String getStudioShoots() {
        return studioShoots;
    }

    public void setStudioShoots(String studioShoots) {
        this.studioShoots = studioShoots;
    }

    public String getPreShoots() {
        return preShoots;
    }

    public void setPreShoots(String preShoots) {
        this.preShoots = preShoots;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPayTerms() {
        return payTerms;
    }

    public void setPayTerms(String payTerms) {
        this.payTerms = payTerms;
    }

    public String getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(String travelCost) {
        this.travelCost = travelCost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
