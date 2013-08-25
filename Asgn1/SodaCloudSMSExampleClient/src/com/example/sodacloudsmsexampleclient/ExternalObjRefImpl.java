package com.example.sodacloudsmsexampleclient;

import org.magnum.soda.proxy.ObjRef;

public class ExternalObjRefImpl implements ExternalObjRef {

    ObjRef objRef;
    String pubSubHost;

    @Override
    public ObjRef getObjRef() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPubSubHost() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setObjRef(ObjRef objRef) {
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
