package com.apwglobal.nice.country;

import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.Configuration;
import com.google.common.cache.*;
import pl.allegro.webapi.*;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toMap;

public class InfoService extends AbstractService {

    private static final String COUNTRIES_CACHE_KEY = "COUNTRIES_CACHE_KEY";
    private static final String SHIPPING_CACHE_KEY = "SHIPPING_CACHE_KEY";
    private LoadingCache<String, Map<Integer, String>> cache;

    public InfoService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(2)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(getLoader());
    }

    private CacheLoader<String, Map<Integer, String>> getLoader() {
        return new CacheLoader<String, Map<Integer, String>>() {
            @Override
            public Map<Integer, String> load(String key) throws Exception {
                switch (key) {
                    case COUNTRIES_CACHE_KEY:
                        return retriveCountries();
                    case SHIPPING_CACHE_KEY:
                        return retriveShipping();
                    default:
                        throw new IllegalArgumentException("Cannot find cache with key " + key);
                }
            }
        };
    }

    public Map<Integer, String> getCountries() {
        return cache.getUnchecked(COUNTRIES_CACHE_KEY);
    }

    public Map<Integer, String> getShipping() {
        return cache.getUnchecked(SHIPPING_CACHE_KEY);
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

    /**
     * http://allegro.pl/webapi/documentation.php/show/id,624#method-output
     */
    private Map<Integer,String> retriveShipping() {
        DoGetShipmentDataRequest req = new DoGetShipmentDataRequest(conf.getCountryId(), cred.getKey());
        DoGetShipmentDataResponse res = AllegroExecutor.execute(() -> allegro.doGetShipmentData(req));
        return Collections.unmodifiableMap(
                res.getShipmentDataList().getItem()
                        .stream()
                        .collect(toMap(ShipmentDataStruct::getShipmentId, ShipmentDataStruct::getShipmentName))
        );
    }

}
