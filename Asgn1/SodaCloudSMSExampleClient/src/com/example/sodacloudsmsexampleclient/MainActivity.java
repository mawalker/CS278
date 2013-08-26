package com.example.sodacloudsmsexampleclient;

import org.magnum.soda.example.sms.SMSManager;
import org.magnum.soda.example.sms.SMSManagerImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button connect_;
    private Button scan_;
    private EditText objRef_;
    private EditText server_;

    /**
     * Asgn Step 5: Instantiate an instance of your Module implementation and
     * assign it to the configuration variable. Note, your module instance
     * should be configured to map:
     * 
     * SMSManager --> SMSManagerImpl ObjRefExtractor --> QRCodeObjRefExtractor
     * 
     */
    private Module configuration_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the Module instance
        configuration_ = new ModuleImpl<ObjRefExtractor>();
        // configure the instance to return proper values.
        configuration_.setComponent(SMSManager.class, new SMSManagerImpl(
                getApplicationContext()));
        configuration_.setComponent(ObjRefExtractor.class,
                new QRCodeObjRefExtractor());

        connect_ = (Button) findViewById(R.id.connect);
        scan_ = (Button) findViewById(R.id.scan);
        objRef_ = (EditText) findViewById(R.id.objRef);
        server_ = (EditText) findViewById(R.id.server);

        // Testing Function, makes it a LOT easier to test
        // connection w/o camera on remote physical device
        // setTestSettings();

        connect_.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String server = server_.getText().toString();
                String oref = objRef_.getText().toString();
                if (server.length() > 0 && oref.length() > 0) {
                    connect(server, oref);
                }
            }
        });

        scan_.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initiateScan();
            }
        });
    }

    private void setTestSettings() {
        server_.setText("192.168.1.4");
        objRef_.setText("soda://544c4fe2-8ed9-47bf-8830-4a59f1c1b18c#33833f5f-bfae-4bbb-bc8a-7b2cee9d2a2b");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void connect(String server, String oref) {
        Toast.makeText(this, "Connecting to: " + server + "...",
                Toast.LENGTH_LONG).show();

        if ((server == null) || (oref == null)) {
            Log.w("MAINACTIVITY", "ref was null");
        } else {
            Log.w("MAINACTIVITY", "ref pub sub host :" + server);
            Log.w("MAINACTIVITY", "ref pub sub host :" + oref);
        }

        Log.d("MAIN", "");
        Intent i = new Intent(this, SMSBridgeActivity.class);
        i.putExtra("ref", server + "|" + oref);
        startActivity(i);
    }

    public void initiateScan() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");

                ObjRefExtractor extractor = configuration_
                        .getComponent(ObjRefExtractor.class);
                if (extractor == null) {
                    Log.w("MAIN ACTIVITY", "Extractor was null");
                }
                ExternalObjRef ref = extractor.extract(contents);

                connect(ref.getPubSubHost(), ref.getObjRef().getUri());

            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
}
