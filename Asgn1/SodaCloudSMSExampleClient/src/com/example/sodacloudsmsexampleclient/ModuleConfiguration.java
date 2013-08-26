package com.example.sodacloudsmsexampleclient;

import org.magnum.soda.example.sms.SMSManager;
import org.magnum.soda.example.sms.SMSManagerImpl;

import android.content.Context;

public class ModuleConfiguration {

    private static Module configuration_ = new ModuleImpl<ModuleConfiguration>();
    private static Context mContext = null;

    public static void setConfig() {

        // configure the instance to return proper values.
        configuration_.setComponent(SMSManager.class, new SMSManagerImpl(
                mContext));
        configuration_.setComponent(ObjRefExtractor.class,
                new QRCodeObjRefExtractor());
    }

    public static void setContext(Context context) {
        mContext = context;
    }

}
