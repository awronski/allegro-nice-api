package com.apwglobal.nice.message;

import java.time.LocalDateTime;

public class AllegroMessage {

    private int catId;
    private String title;
    private String body;
    private LocalDateTime date;

    private AllegroMessage(Builder builder) {
        catId = builder.catId;
        title = builder.title;
        body = builder.body;
        date = builder.date;
    }

    public int getCatId() {
        return catId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getDate() {
        return date;
    }


    public static final class Builder {
        private int catId;
        private String title;
        private String body;
        private LocalDateTime date;

        public Builder() {
        }

        public Builder catId(int catId) {
            this.catId = catId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public AllegroMessage build() {
            return new AllegroMessage(this);
        }
    }

}
