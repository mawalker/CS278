package com.example.sodacloudsmsexampleclient;

import java.util.HashMap;

public class ModuleImpl<T> implements Module {

    // Idea for singleton was mine, but was unsure on how to do it so I used
    // http://www.coderanch.com/t/470696/java/java/Generic-Singleton-Class
    // as a source for a possible implementation.
    private static final ModuleImpl<?> instance = new ModuleImpl<Object>();

    private ModuleImpl() {
    }

    public static ModuleImpl<?> getInstance() {
        return instance;
    }

    HashMap<Class<Object>, Object> list = new HashMap<Class<Object>, Object>();

    @Override
    public <T> T getComponent(Class<T> type) {
        return (T) instance.list.get(type);
    }

    @Override
    public <T> void setComponent(Class<T> type, T component) {
        instance.list.put((Class<Object>) type, (T) component);
    }

}
