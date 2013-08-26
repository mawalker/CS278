package com.example.sodacloudsmsexampleclient;

import org.magnum.soda.proxy.ObjRef;

import android.util.Log;

public class ExternalObjRefImpl implements ExternalObjRef {

    ObjRef objRef;
    String pubSubHost;

    @Override
    public ObjRef getObjRef() {
        // TODO Auto-generated method stub
        return objRef;
    }

    @Override
    public String getPubSubHost() {
        // TODO Auto-generated method stub
        return pubSubHost;
    }

    public void setObjRef(ObjRef objRef) {
        Log.d("OBJ IMPL", "objref =" + objRef);
        Log.d("OBJ IMPL", "objref =" + objRef.getHost());
        Log.d("OBJ IMPL", "objref =" + objRef.getUri());
        this.objRef = objRef;
    }

    public void setPubSubHost(String pubSubHost) {
        this.pubSubHost = pubSubHost;
    }

    @Override
    public String toString() {
        return getPubSubHost() + "|" + getObjRef().getUri();
    }
}
