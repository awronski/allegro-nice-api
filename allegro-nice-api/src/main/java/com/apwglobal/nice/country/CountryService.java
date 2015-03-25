package com.apwglobal.nice.country;

import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import com.google.common.cache.*;
import pl.allegro.webapi.CountryInfoType;
import pl.allegro.webapi.DoGetCountriesRequest;
import pl.allegro.webapi.DoGetCountriesResponse;
import pl.allegro.webapi.ServicePort;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toMap;

public class CountryService extends AbstractService {

    private static final String COUNTRIES_CACHE_KEY = "COUNTRIES_CACHE_KEY";
    private LoadingCache<String, Map<Integer, String>> countries;

    public CountryService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
        this.countries = CacheBuilder.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(getLoader());
    }

    private CacheLoader<String, Map<Integer, String>> getLoader() {
        return new CacheLoader<String, Map<Integer, String>>() {
            @Override
            public Map<Integer, String> load(String key) throws Exception {
                return retriveCountries();
            }
        };
    }

    public Map<Integer, String> getCountries() {
        return countries.getUnchecked(COUNTRIES_CACHE_KEY);
    }

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,25#method-output
     */
    private Map<Integer, String> retriveCountries() {
        DoGetCountriesRequest req = new DoGetCountriesRequest(conf.getCountryId(), cred.getKey());
        DoGetCountriesResponse res = AllegroExecutor.execute(() -> allegro.doGetCountries(req));
        return Collections.unmodifiableMap(
                res.getCountryArray().getItem()
                        .stream()
                        .collect(toMap(CountryInfoType::getCountryId, CountryInfoType::getCountryName))
        );
    }

}
