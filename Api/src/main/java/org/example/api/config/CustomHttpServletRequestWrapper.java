package org.example.api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        customHeaders = new HashMap<>();
    }

    @Override
    public String getHeader(String name) {
        if ("X-auth-user-id".equals(name)) {
            return customHeaders.get("X-auth-user-id");
        }
        return super.getHeader(name);
    }

    public void setHeader(String userId) {
        customHeaders.put("X-auth-user-id", userId);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Enumeration<String> existingHeaderNames = super.getHeaderNames();
        Map<String, String> headersMap = new HashMap<>();

        while (existingHeaderNames.hasMoreElements()) {
            String headerName = existingHeaderNames.nextElement();
            headersMap.put(headerName, super.getHeader(headerName));
        }

        headersMap.put("X-auth-user-id", customHeaders.get("X-auth-user-id"));

        return java.util.Collections.enumeration(headersMap.keySet());
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        String s = customHeaders.get("X-auth-user-id");
        ArrayList<String> objects = new ArrayList<>();
        objects.add(s);
        if ("X-auth-user-id".equals(name)) {
            return java.util.Collections.enumeration(objects);
        }
        return super.getHeaders(name);
    }

}
