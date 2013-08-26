package com.example.sodacloudsmsexampleclient;

import java.util.HashMap;

public class ModuleImpl<T> implements Module {

    HashMap<Class<Object>, Object> list = new HashMap<Class<Object>, Object>();

    @Override
    public <T> T getComponent(Class<T> type) {
        return (T) list.get(type);
    }

    @Override
    public <T> void setComponent(Class<T> type, T component) {
        list.put((Class<Object>) type, (T) component);
    }

}
