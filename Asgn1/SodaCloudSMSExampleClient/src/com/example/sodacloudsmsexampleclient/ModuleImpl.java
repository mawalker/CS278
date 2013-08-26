package com.example.sodacloudsmsexampleclient;

import java.util.HashMap;

import android.util.Log;

public class ModuleImpl<T> implements Module {

    // Made list static and both methods synchronized so that way singleton
    // behavior exists, and only have to load configuration of modules once

    private static HashMap<Class<Object>, Object> list = new HashMap<Class<Object>, Object>();

    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    synchronized public <T> T getComponent(Class<T> type) {
        return (T) list.get(type);
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    synchronized public <T> void setComponent(Class<T> type, T component) {
        Log.d("MODULE IMPL", "size:" + list.size());
        list.put((Class<Object>) type, (T) component);
    }

}
