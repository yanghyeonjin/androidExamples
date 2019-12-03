package com.yanghyeonjin.androidexamples.model;

public class Example {
    private String image;
    private String category;
    private String title;

    public Example() {}

    public Example(String image, String category, String title) {
        this.image = image;
        this.category = category;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
