package com.apwglobal.nice.login;

import org.jetbrains.annotations.Nullable;

public class Credentials {

    private long clientId;
    private String username;
    private String passoword;
    private String key;

    @Nullable
    private String restClientId;
    @Nullable
    private String restClientSecret;
    @Nullable
    private String restClientApiKey;
    @Nullable
    private String restRedirectUri;

    public Credentials(long clientId, String username, String passowrd, String key) {
        this(clientId, username, passowrd, key, null, null, null, null);
    }

    public Credentials(long clientId, String username, String passowrd, String key, @Nullable String restClientId, @Nullable String restClientSecret, @Nullable String restClientApiKey, @Nullable String restRedirectUri) {
        this.clientId = clientId;
        this.username = username;
        this.passoword = passowrd;
        this.key = key;
        this.restClientId = restClientId;
        this.restClientSecret = restClientSecret;
        this.restClientApiKey = restClientApiKey;
        this.restRedirectUri = restRedirectUri;
    }

    public long getClientId() {
        return clientId;
    }
    String getUsername() {
        return username;
    }
    String getPassoword() {
        return passoword;
    }
    public String getKey() {
        return key;
    }
    @Nullable
    public String getRestClientId() {
        return restClientId;
    }
    @Nullable
    public String getRestClientSecret() {
        return restClientSecret;
    }
    @Nullable
    public String getRestClientApiKey() {
        return restClientApiKey;
    }
    @Nullable
    public String getRestRedirectUri() {
        return restRedirectUri;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "clientId='" + clientId + "\', " +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credentials that = (Credentials) o;

        if (clientId != that.clientId) return false;
        if (!username.equals(that.username)) return false;
        if (!passoword.equals(that.passoword)) return false;
        if (!key.equals(that.key)) return false;
        if (restClientId != null ? !restClientId.equals(that.restClientId) : that.restClientId != null) return false;
        if (restClientSecret != null ? !restClientSecret.equals(that.restClientSecret) : that.restClientSecret != null) return false;
        if (restClientApiKey != null ? !restClientApiKey.equals(that.restClientApiKey) : that.restClientApiKey != null) return false;
        return restRedirectUri != null ? restRedirectUri.equals(that.restRedirectUri) : that.restRedirectUri == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (clientId ^ (clientId >>> 32));
        result = 31 * result + username.hashCode();
        result = 31 * result + passoword.hashCode();
        result = 31 * result + key.hashCode();
        result = 31 * result + (restClientId != null ? restClientId.hashCode() : 0);
        result = 31 * result + (restClientSecret != null ? restClientSecret.hashCode() : 0);
        result = 31 * result + (restClientApiKey != null ? restClientApiKey.hashCode() : 0);
        result = 31 * result + (restRedirectUri != null ? restRedirectUri.hashCode() : 0);
        return result;
    }

}
