package com.apwglobal.nice.domain;

public class FormField {

    private int id;
    private String title;
    private boolean required;
    private FieldType type;
    private String desc;

    public FormField() { }

    private FormField(Builder builder) {
        id = builder.id;
        title = builder.title;
        required = builder.required;
        type = builder.type;
        desc = builder.desc;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public boolean isRequired() {
        return required;
    }
    public FieldType getType() {
        return type;
    }
    public String getDesc() {
        return desc;
    }

    public static final class Builder {
        private int id;
        private String title;
        private boolean required;
        private FieldType type;
        private String desc;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder required(int required) {
            this.required = required == 1;
            return this;
        }

        public Builder type(FieldType type) {
            this.type = type;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public FormField build() {
            return new FormField(this);
        }
    }

    @Override
    public String toString() {
        return "FormField{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", required=" + required +
                '}';
    }
}
