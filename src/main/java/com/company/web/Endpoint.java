package com.company.web;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class Endpoint {
    private final Uri uri;
    private final HttpMethod method;

    public Endpoint(Uri uri, HttpMethod method) {
        this.uri = uri;
        this.method = method;
    }

    public static Endpoint fromRequest(HttpServletRequest req) {
        Uri uri = Uri.extractUri(req);
        HttpMethod method = HttpMethod.valueOf(req.getMethod());
        return new Endpoint(uri, method);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return uri == endpoint.uri &&
                method == endpoint.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, method);
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "uri=" + uri +
                ", method=" + method +
                '}';
    }
}
