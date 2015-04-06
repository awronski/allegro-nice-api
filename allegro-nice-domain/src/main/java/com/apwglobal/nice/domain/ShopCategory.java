package com.apwglobal.nice.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopCategory {

    private int id;
    private String name;
    private List<ShopCategory> children;
    private Optional<ShopCategory> parent;

    public ShopCategory() { }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<ShopCategory> getChildren() {
        return children;
    }
    public Optional<ShopCategory> getParent() {
        return parent;
    }
    public void setParent(Optional<ShopCategory> parent) {
        this.parent = parent;
    }
    public void addChild(ShopCategory child) {
        this.children.add(child);
    }

    private ShopCategory(Builder builder) {
        id = builder.id;
        name = builder.name;
        if (builder.children == null) {
            children = new ArrayList<>();
        } else {
            children = builder.children;
        }
        if (builder.parent == null) {
            parent = Optional.empty();
        } else {
            parent = builder.parent;
        }
    }


    public static final class Builder {
        private int id;
        private String name;
        private List<ShopCategory> children;
        private Optional<ShopCategory> parent;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder children(List<ShopCategory> children) {
            this.children = children;
            return this;
        }

        public Builder parent(Optional<ShopCategory> parent) {
            this.parent = parent;
            return this;
        }

        public ShopCategory build() {
            return new ShopCategory(this);
        }
    }

    @Override
    public String toString() {
        return "ShopCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children.size() +
                ", parent=" + (parent.isPresent() ? parent.get().id : "-") +
                '}';
    }
}
