package com.oilfieldapps.allspark.strokescalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    ImageButton pump_output_btn;
    ImageButton snv_btn;
    ImageButton av_btn;
    ImageButton tank_button;
    Button pill;

    Button rateApp;
    Button otherApps;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getString(R.string.appId));

        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        FirstRunApp();

        rateApp = findViewById(R.id.snv_rateApp);
        otherApps = findViewById(R.id.snv_otherApps);

        pump_output_btn = findViewById(R.id.pump_output);
        snv_btn = findViewById(R.id.strokes_and_volume_calculations);
        av_btn = findViewById(R.id.annular_velocity);
        tank_button = findViewById(R.id.tank_volume);
        pill = findViewById(R.id.pill_spotting);

        pump_output_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent output_intent = new Intent(MainActivity.this, PumpOutput.class);
                startActivity(output_intent);
            }
        });

        snv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent snv_intent = new Intent(MainActivity.this, SandV_Free.class);
                startActivity(snv_intent);
            }
        });

        av_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent av_intent = new Intent(MainActivity.this, AnnularVelocity.class);
                startActivity(av_intent);
            }
        });

        tank_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tank_intent = new Intent(MainActivity.this, Tanks_Volume.class);
                startActivity(tank_intent);
            }
        });

        rateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rateIntent = new Intent(Intent.ACTION_VIEW);
                rateIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.oilfieldapps.allspark.mudBalance&rdid=com.oilfieldapps.allspark.mudBalance"));
                startActivity(rateIntent);
            }
        });

        otherApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherAppsIntent = new Intent(Intent.ACTION_VIEW);
                otherAppsIntent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Oilfield+Apps"));
                startActivity(otherAppsIntent);
            }
        });
    }

    private void FirstRunApp() {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if(sharedPreferences.getBoolean("FIRST_RUN", true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Please Read");
            builder.setMessage(R.string.first_run_message);
            builder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("FIRST_RUN", false);
                    editor.commit();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("FIRST_RUN", true);
                    editor.commit();
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }
}
