package com.apwglobal.nice.service;

public class Configuration {

    private int countryId;
    private long version;

    public Configuration(int countryId, long version) {
        this.countryId = countryId;
        this.version = version;
    }

    public int getCountryId() {
        return countryId;
    }

    public long getVersion() {
        return version;
    }
}
