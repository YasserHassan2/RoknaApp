package com.yasser.roknaapp.Model;

public class Product {

    String id,name,description,price,sale,imgURL1,imgURL2,imgURL3,imgURL4;
    int category_id;
    public Product(String id, String name, String description, String price, String sale, String imgURL1, String imgURL2, String imgURL3, String imgURL4) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sale = sale;
        this.imgURL1 = imgURL1;
        this.imgURL2 = imgURL2;
        this.imgURL3 = imgURL3;
        this.imgURL4 = imgURL4;
    }

    public Product(String id, String name, int category_id, String description, String price, String sale, String imgURL1, String imgURL2, String imgURL3, String imgURL4) {
        this.id = id;
        this.name = name;
        this.category_id = category_id;
        this.description = description;
        this.price = price;
        this.sale = sale;
        this.imgURL1 = imgURL1;
        this.imgURL2 = imgURL2;
        this.imgURL3 = imgURL3;
        this.imgURL4 = imgURL4;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public Product(String name, String description, String price, String sale, String imgURL1, String imgURL2, String imgURL3, String imgURL4) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sale = sale;
        this.imgURL1 = imgURL1;
        this.imgURL2 = imgURL2;
        this.imgURL3 = imgURL3;
        this.imgURL4 = imgURL4;
    }

    public Product(String name, String description, String price, String sale) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sale = sale;
    }

    public Product(String id, String name, String description, String price, String sale) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sale = sale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getImgURL1() {
        return imgURL1;
    }

    public void setImgURL1(String imgURL1) {
        this.imgURL1 = imgURL1;
    }

    public String getImgURL2() {
        return imgURL2;
    }

    public void setImgURL2(String imgURL2) {
        this.imgURL2 = imgURL2;
    }

    public String getImgURL3() {
        return imgURL3;
    }

    public void setImgURL3(String imgURL3) {
        this.imgURL3 = imgURL3;
    }

    public String getImgURL4() {
        return imgURL4;
    }

    public void setImgURL4(String imgURL4) {
        this.imgURL4 = imgURL4;
    }
}
