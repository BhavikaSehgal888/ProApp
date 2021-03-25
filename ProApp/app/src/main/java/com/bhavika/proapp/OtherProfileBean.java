package com.bhavika.proapp;

import java.io.Serializable;

/**
 * Created by bhavika on 13-12-2016.
 */
public class OtherProfileBean implements Serializable {

    int id;
    String name,email,phone1,phone2,gender,city,candidShoots,cinemaShoots,studioShoots,preShoots,
    experience,payTerms,travelCost,profile,cover;

    public OtherProfileBean(){

    }

    public OtherProfileBean(String candidShoots, String cinemaShoots, String city, String cover, String email, String experience, String gender, int id, String name, String payTerms,
                            String phone1, String phone2, String preShoots, String profile, String studioShoots, String travelCost) {
        this.candidShoots = candidShoots;
        this.cinemaShoots = cinemaShoots;
        this.city = city;
        this.cover = cover;
        this.email = email;
        this.experience = experience;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.payTerms = payTerms;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.preShoots = preShoots;
        this.profile = profile;
        this.studioShoots = studioShoots;
        this.travelCost = travelCost;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getPayTerms() {
        return payTerms;
    }

    public void setPayTerms(String payTerms) {
        this.payTerms = payTerms;
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

    public String getPreShoots() {
        return preShoots;
    }

    public void setPreShoots(String preShoots) {
        this.preShoots = preShoots;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getStudioShoots() {
        return studioShoots;
    }

    public void setStudioShoots(String studioShoots) {
        this.studioShoots = studioShoots;
    }

    public String getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(String travelCost) {
        this.travelCost = travelCost;
    }
}
