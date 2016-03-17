package com.apwglobal.nice.service;

import java.util.Date;

public class AllegroSession {

    private String sessionId;
    private long userId;
    private Date lastLoginDate;

    private AllegroSession(Builder builder) {
        sessionId = builder.sessionId;
        userId = builder.userId;
    }

    public String getSessionId() {
        if (sessionId == null) {
            throw new IllegalStateException("Session was not initalized. You need to use .login() first");
        }
        return sessionId;
    }
    public long getUserId() {
        return userId;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public static final class Builder {
        private String sessionId;
        private long userId;
        private Date lastLoginDate;

        public Builder() {
        }

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder lastLoginDate(Date lastLoginDate) {
            this.lastLoginDate = lastLoginDate;
            return this;
        }

        public AllegroSession build() {
            return new AllegroSession(this);
        }
    }
}
