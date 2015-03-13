package com.apwglobal.nice.domain;

import java.util.Optional;

public class Address {

    protected int countryId;
    protected String street;
    protected String code;
    protected String city;
    protected String fullname;
    protected Optional<String> company;
    protected String phone;
    protected Optional<String> nip;

    public Address() { }

    private Address(Builder builder) {
        countryId = builder.countryId;
        street = builder.street;
        code = builder.code;
        city = builder.city;
        fullname = builder.fullname;
        company = builder.company;
        phone = builder.phone;
        nip = builder.nip;
    }

    public int getCountryId() {
        return countryId;
    }
    public String getStreet() {
        return street;
    }
    public String getCode() {
        return code;
    }
    public String getCity() {
        return city;
    }
    public String getFullname() {
        return fullname;
    }
    public Optional<String> getCompany() {
        return company;
    }
    public String getPhone() {
        return phone;
    }
    public Optional<String> getNip() {
        return nip;
    }

    public static final class Builder {
        private int countryId;
        private String street;
        private String code;
        private String city;
        private String fullname;
        private Optional<String> company;
        private String phone;
        private Optional<String> nip;

        public Builder() {
        }

        public Builder countryId(int countryId) {
            this.countryId = countryId;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder fullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public Builder company(String company) {
            if (company.isEmpty()) {
                this.company = Optional.empty();
            } else {
                this.company = Optional.of(company);
            }
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder nip(String nip) {
            if (nip.isEmpty()) {
                this.nip = Optional.empty();
            } else {
                this.nip = Optional.of(nip);
            }
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}