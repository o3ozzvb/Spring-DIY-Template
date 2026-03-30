package com.diy.framework.web.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {

    private final Map<String, Controller> mappings = new HashMap<>();

    public void registerHandler(String url, Controller controller) {
        mappings.put(url, controller);
    }

public Object getHandler(HttpServletRequest request) {
        String key = request.getMethod() + " " + request.getRequestURI();
        return mappings.get(key);
    }
}
