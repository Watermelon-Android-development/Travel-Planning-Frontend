package com.example.travelplan.bean;

public class Site {
    public String siteName;
    public int siteImage;
    public String siteDesc;
    public int siteRating;
    public String siteType;
    public String siteLocation;
    public char siteContact;
    public String siteOpentime;
    public boolean siteFav;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getSiteImage() {
        return siteImage;
    }

    public void setSiteImage(int siteImage) {
        this.siteImage = siteImage;
    }

    public String getSiteDesc() {
        return siteDesc;
    }

    public void setSiteDesc(String siteDesc) {
        this.siteDesc = siteDesc;
    }

    public int getSiteRating() {
        return siteRating;
    }

    public void setSiteRating(int siteRating) {
        this.siteRating = siteRating;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getSiteLocation() {
        return siteLocation;
    }

    public void setSiteLocation(String siteLocation) {
        this.siteLocation = siteLocation;
    }

    public char getSiteContact() {
        return siteContact;
    }

    public void setSiteContact(char siteContact) {
        this.siteContact = siteContact;
    }

    public String getSiteOpentime() {
        return siteOpentime;
    }

    public void setSiteOpentime(String siteOpentime) {
        this.siteOpentime = siteOpentime;
    }

    public boolean isSiteFav() {
        return siteFav;
    }

    public void setSiteFav(boolean siteFav) {
        this.siteFav = siteFav;
    }
}
