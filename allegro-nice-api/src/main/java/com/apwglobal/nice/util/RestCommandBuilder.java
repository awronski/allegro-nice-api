package com.apwglobal.nice.util;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class RestCommandBuilder {

    private static final String SCHEMA = "https";
    private static final String HOST = "allegroapi.io";

    private String schema = SCHEMA;
    private String host = HOST;
    private String path;
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private String entity;

    public RestCommandBuilder schema(String val) {
        schema = val;
        return this;
    }

    public RestCommandBuilder host(String val) {
        host = val;
        return this;
    }

    public RestCommandBuilder path(String val) {
        path = val;
        return this;
    }

    public RestCommandBuilder addParam(String key, String val) {
        params.put(key, val);
        return this;
    }

    public RestCommandBuilder addHeader(String key, String val) {
        headers.put(key, val);
        return this;
    }

    public RestCommandBuilder entity(String val) {
        entity = val;
        return this;
    }

    public HttpGet buildGet() {
        URI uri = buildUri();
        HttpGet post = new HttpGet(uri);
        headers.forEach(post::setHeader);

        return post;
    }

    public HttpPost buildPost() {
        URI uri = buildUri();
        HttpPost post = new HttpPost(uri);
        headers.forEach(post::setHeader);
        setEntity(post);

        return post;
    }

    public HttpPut buildPut() {
        URI uri = buildUri();
        HttpPut put = new HttpPut(uri);
        headers.forEach(put::setHeader);
        setEntity(put);
        return put;
    }

    private void setEntity(HttpEntityEnclosingRequestBase put) {
        if (this.entity == null) {
            return;
        }

        try {
            put.setEntity(new StringEntity(entity));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private URI buildUri() {
        URIBuilder uriBuilder = new URIBuilder().setScheme(schema).setHost(host).setPath(path);
        params.forEach(uriBuilder::setParameter);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}
