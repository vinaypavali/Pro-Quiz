package com.quiz.proquiz.Adapters;

public class CategoryModel {

    private String categoryId;
    private String categoryName;
    private String categoryImage;

    public CategoryModel()
    {

    }

    public CategoryModel(String categoryId,String categoryName,String categoryImage) {
        this.categoryId=categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getCategoryId() { return categoryId;}

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

}

