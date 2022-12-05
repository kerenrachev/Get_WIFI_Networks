package com.example.wificloseservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView main_TXT_wifilst;
    private WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        IntentFilter intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        this.registerReceiver(wifiScanReceiver, intentFilter);
        wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        boolean success = wifiManager.startScan();
        if (!success) {
            // scan failure handling
            scanFailure();
        }
    }

    private void findViews(){
        main_TXT_wifilst = findViewById(R.id.main_TXT_wifilst);
    }
    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            boolean success = intent.getBooleanExtra(
                    WifiManager.EXTRA_RESULTS_UPDATED, false);
            if (success) {
                scanSuccess();
            } else {
                // scan failure handling
                scanFailure();
            }
        }
    };

    private void scanSuccess() {
        List<ScanResult> results = wifiManager.getScanResults();
        String result ="";
        for(ScanResult curr: results){
            result = result+"SSID: "+ curr.SSID + curr.level+" dBm\n" ;
        }
        main_TXT_wifilst.setText(result);
    }
    private void scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        List<ScanResult> results = wifiManager.getScanResults();

    }

}