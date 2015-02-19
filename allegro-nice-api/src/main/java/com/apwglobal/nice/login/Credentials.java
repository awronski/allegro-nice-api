package com.apwglobal.nice.login;

public class Credentials {

    private String username;
    private String passowrd;
    private String key;


    public Credentials(String username, String passowrd, String key) {
        this.username = username;
        this.passowrd = passowrd;
        this.key = key;
    }

    public String getUsername() {
        return username;
    }
    public String getPassowrd() {
        return passowrd;
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
        if (!(o instanceof Credentials)) return false;

        Credentials that = (Credentials) o;

        return key.equals(that.key) && passowrd.equals(that.passowrd) && username.equals(that.username);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + passowrd.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }

}
