package com.donishchenko.testgame.resources;

import java.util.Map;

public class Resources {

    private Map<String, Object> resources;

    Resources(Map<String, Object> resources) {
        this.resources = resources;
    }

    public <T> T get(String name) {
        return (T) resources.get(name);
    }
}
