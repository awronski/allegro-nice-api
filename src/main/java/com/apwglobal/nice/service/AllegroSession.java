package com.apwglobal.nice.service;

public class AllegroSession {

    private long versionId;
    private String sessionId;
    private long userId;

    private AllegroSession(Builder builder) {
        versionId = builder.versionId;
        sessionId = builder.sessionId;
        userId = builder.userId;
    }

    public long getVersionId() {
        return versionId;
    }
    public String getSessionId() {
        return sessionId;
    }
    public long getUserId() {
        return userId;
    }

    public static final class Builder {
        private long versionId;
        private String sessionId;
        private long userId;

        public Builder() {
        }

        public Builder versionId(long versionId) {
            this.versionId = versionId;
            return this;
        }

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public AllegroSession build() {
            return new AllegroSession(this);
        }
    }
}
