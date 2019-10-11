package com.vaadin.starter.skeleton.spring;

import com.vaadin.connect.VaadinService;

/**
 * Simple Vaadin Connect Service definition.
 */
@VaadinService
public class ConnectServices {
    
    public String hello(String name) {
        return "Hello, " + name + "!";
    }
    
}
