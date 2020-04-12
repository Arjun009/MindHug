package com.arjun.survey;

public class Category {

    private String Name;
    private String Image;
    private String Ques;

    public String getQues() {
        return Ques;
    }

    public void setQues(String ques) {
        Ques = ques;
    }

    public Category() {
    }

    public Category(String name, String image, String ques) {
        Name = name;
        Image = image;
        Ques = ques;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}