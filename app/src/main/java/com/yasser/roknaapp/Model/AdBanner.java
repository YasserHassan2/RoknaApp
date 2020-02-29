package com.yasser.roknaapp.Model;

public class AdBanner {
    String ad_url,ad_img,add_snppit;

    public AdBanner(String ad_url, String ad_img, String add_snppit) {
        this.ad_url = ad_url;
        this.ad_img = ad_img;
        this.add_snppit = add_snppit;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public String getAd_img() {
        return ad_img;
    }

    public void setAd_img(String ad_img) {
        this.ad_img = ad_img;
    }

    public String getAdd_snppit() {
        return add_snppit;
    }

    public void setAdd_snppit(String add_snppit) {
        this.add_snppit = add_snppit;
    }
}
