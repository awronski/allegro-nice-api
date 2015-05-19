package com.apwglobal.nice.login;

public class Credentials {

    private long clientId;
    private String username;
    private String passoword;
    private String key;


    public Credentials(long clientId, String username, String passowrd, String key) {
        this.clientId = clientId;
        this.username = username;
        this.passoword = passowrd;
        this.key = key;
    }

    public long getClientId() {
        return clientId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassoword() {
        return passoword;
    }
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Credentials{" +
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
        return key.equals(that.key);

    }

    @Override
    public int hashCode() {
        int result = (int) (clientId ^ (clientId >>> 32));
        result = 31 * result + username.hashCode();
        result = 31 * result + passoword.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }

}
