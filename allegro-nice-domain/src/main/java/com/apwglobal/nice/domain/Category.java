package com.apwglobal.nice.domain;

public class Category {

    protected int catId;
    protected String catName;
    protected int catParent;

    public Category() { }

    private Category(Builder builder) {
        catId = builder.catId;
        catName = builder.catName;
        catParent = builder.catParent;
    }

    public int getCatId() {
        return catId;
    }
    public String getCatName() {
        return catName;
    }
    public int getCatParent() {
        return catParent;
    }

    public static final class Builder {
        private int catId;
        private String catName;
        private int catParent;

        public Builder() {
        }

        public Builder catId(int catId) {
            this.catId = catId;
            return this;
        }

        public Builder catName(String catName) {
            this.catName = catName;
            return this;
        }

        public Builder catParent(int catParent) {
            this.catParent = catParent;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }

    @Override
    public String toString() {
        return "Category{" +
                "catId=" + catId +
                ", catName='" + catName + '\'' +
                ", catParent=" + catParent +
                '}';
    }

}
