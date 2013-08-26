package com.example.sodacloudsmsexampleclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    // constants for launching the barcode scanner
    private static final String SCAN_MODE = "SCAN_MODE";
    private static final String QR_CODE_MODE = "QR_CODE_MODE";
    private static final String SCANNER_CLIENT = "com.google.zxing.client.android.SCAN";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ModuleConfiguration.setContext(getApplicationContext());
        setupUI();

    }

    private void setupUI() {

        setContentView(R.layout.activity_main);

        objRef_ = (EditText) findViewById(R.id.objRef);
        server_ = (EditText) findViewById(R.id.server);

        connect_ = (Button) findViewById(R.id.connect);
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

        scan_ = (Button) findViewById(R.id.scan);
        scan_.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initiateScan();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void connect(String server, String oref) {
        makeLongToast("Connecting to: " + server + "...");
        Intent i = getConnectIntent(server, oref);
        startActivity(i);
    }

    private Intent getConnectIntent(String server, String oref) {
        Intent i = new Intent(this, SMSBridgeActivity.class);
        i.putExtra("ref", server + "|" + oref);
        return i;
    }

    private Intent getScanIntent() {
        Intent intent = new Intent(SCANNER_CLIENT);
        intent.putExtra(SCAN_MODE, QR_CODE_MODE);
        return intent;
    }

    public void initiateScan() {
        Intent intent = getScanIntent();
        startActivityForResult(intent, 0);
    }

    private void makeLongToast(String value) {
        Toast.makeText(this, value, Toast.LENGTH_LONG).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                ExternalObjRef ref = getExternalObjRefFromIntent(intent);
                connect(ref.getPubSubHost(), ref.getObjRef().getUri());

            } else if (resultCode == RESULT_CANCELED) {
                makeLongToast("BarCode Scanner Canceled.");
            }
        }
    }

    private ExternalObjRef getExternalObjRefFromIntent(Intent intent) {
        String contents = intent.getStringExtra("SCAN_RESULT");

        ObjRefExtractor extractor = new ModuleImpl<ObjRefExtractor>()
                .getComponent(ObjRefExtractor.class);

        return extractor.extract(contents);
    }
}
