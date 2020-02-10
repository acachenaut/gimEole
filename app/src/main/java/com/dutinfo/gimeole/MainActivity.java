package com.dutinfo.gimeole;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.biansemao.widget.ThermometerView;
import com.sccomponents.gauges.gr008.GR008;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ThermometerView thermometre =findViewById(R.id.thermometre);

        final GR008 jauge = findViewById(R.id.JaugeCirculaire);

        Button boutonTemperatureAlternateur = findViewById(R.id.boutonTemperatureAlternateur);
        Button boutonVitesseRotation = findViewById(R.id.boutonVitesseDeRotation);

        boutonTemperatureAlternateur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                jauge.setVisibility(-1);
                thermometre.setVisibility(1);

            }
        });

        boutonVitesseRotation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                thermometre.setVisibility(-1);
                jauge.setVisibility(1);

            }
        });

    }
}
