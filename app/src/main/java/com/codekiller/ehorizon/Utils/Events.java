package com.codekiller.ehorizon.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Events {
    String title, dept, startDate, endDate ,description, formLink, pictureUrl, coordinatorName, pushKey;
    List<String> participators;
    boolean isRegisterNeed;

    public Events() {
    }

    public Events(String title, String dept, String startDate, String endDate, String description, String formLink, String pictureUrl, String coordinatorName, boolean isRegisterNeed, String pushKey) {
        this.title = title;
        this.dept = dept;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.formLink = formLink;
        this.pictureUrl = pictureUrl;
//        this.registrationCloseDate = registrationCloseDate;
        this.coordinatorName = coordinatorName;
        this.isRegisterNeed = isRegisterNeed;
        this.pushKey = pushKey;
        this.participators = new ArrayList<>();
    }

    public List<String> getParticipators() {
        return participators;
    }

    public void setParticipators(List<String> participators) {
        this.participators = participators;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormLink() {
        return formLink;
    }

    public void setFormLink(String formLink) {
        this.formLink = formLink;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

//    public String getRegistrationCloseDate() {
//        return registrationCloseDate;
//    }
//
//    public void setRegistrationCloseDate(String registrationCloseDate) {
//        this.registrationCloseDate = registrationCloseDate;
//    }


    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatorName = coordinatorName;
    }

    public boolean isRegisterNeed() {
        return isRegisterNeed;
    }

    public void setRegisterNeed(boolean registerNeed) {
        isRegisterNeed = registerNeed;
    }
}
