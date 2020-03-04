package com.yasser.roknaapp.Model;

public class Promo {

    private Boolean allow_promo;
    private int codes_numbers,used_codes;

    public Promo() {
    }

    public Promo(Boolean allow_promo, int codes_numbers, int used_codes) {
        this.allow_promo = allow_promo;
        this.codes_numbers = codes_numbers;
        this.used_codes = used_codes;
    }

    public Boolean getAllow_promo() {
        return allow_promo;
    }

    public void setAllow_promo(Boolean allow_promo) {
        this.allow_promo = allow_promo;
    }

    public int getCodes_numbers() {
        return codes_numbers;
    }

    public void setCodes_numbers(int codes_numbers) {
        this.codes_numbers = codes_numbers;
    }

    public int getused_codes() {
        return used_codes;
    }

    public void setused_codes(int used_codes) {
        this.used_codes = used_codes;
    }
}
