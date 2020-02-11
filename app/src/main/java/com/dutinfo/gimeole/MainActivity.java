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

        //Lien entre les boutons de l'interface et l'activité
        Button boutonTemperatureAlternateur = findViewById(R.id.boutonTemperatureAlternateur);
        Button boutonVitesseRotation = findViewById(R.id.boutonVitesseDeRotation);

        boutonTemperatureAlternateur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                jauge.setVisibility(View.INVISIBLE);
                thermometre.setVisibility(View.VISIBLE);

            }
        });

        boutonVitesseRotation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                thermometre.setVisibility(View.INVISIBLE);
                jauge.setVisibility(View.VISIBLE);

            }
        });

    }
}
