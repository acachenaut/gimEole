package com.dutinfo.gimeole;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.biansemao.widget.ThermometerView;
import com.sccomponents.gauges.gr008.GR008;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Nom des jauges
        final String nomJaugeTensionEnEntree = getResources().getString(R.string.nomJaugeTensionEnEntree);
        final String nomJaugeVitesseRotation = getResources().getString(R.string.nomJaugeVitesseDeRotation);
        final String nomJaugeCourantEnEntree = getResources().getString(R.string.nomJaugeCourantEnEntree);
        final String nomJaugeEnergieProduite = getResources().getString(R.string.nomJaugeEnergieProduite);
        final String nomJaugePuissanceFournie = getResources().getString(R.string.nomJaugePuissanceFournie);
        final String nomJaugeTemperatureAlternateur = getResources().getString(R.string.nomJaugeTemperatureAlternateur);
        final String nomJaugeTemperatureFrein = getResources().getString(R.string.nomJaugeTemperatureFrein);


        //Création du modeProduction
        final ModeProduction modeProd = new ModeProduction();

        //Lien entre les jauges de l'interface et de l'activté
        final GR008 jaugeVitesseRotation = findViewById(R.id.jaugeVitesseRotation);
        final GR008 jaugeTensionEnEntree = findViewById(R.id.jaugeTensionEnEntree);
        final GR008 jaugeCourantEnEntree = findViewById(R.id.jaugeCourantEnEntree);
        final GR008 jaugePuissanceFournie = findViewById(R.id.jaugePuissanceFournie);
        //A compléter
        final ThermometerView thermometreAlternateur =findViewById(R.id.thermometreAlternateur);
        final ThermometerView thermometreFrein =findViewById(R.id.thermometreFrein);

        //Lien entre les boutons de l'interface et l'activité
        Button boutonVitesseRotation = findViewById(R.id.boutonVitesseDeRotation);
        Button boutonTensionEnEntree = findViewById(R.id.boutonTensionEnEntree);
        Button boutonCourantEnEntree = findViewById(R.id.boutonCourantEnEntree);
        Button boutonPuissanceFournie = findViewById(R.id.boutonPuissanceFournie);
        Button boutonEnergieProduite = findViewById(R.id.boutonEnergieProduite);
        Button boutonTemperatureAlternateur = findViewById(R.id.boutonTemperatureAlternateur);
        Button boutonTemperatureFrein = findViewById(R.id.boutonTemperatureFrein);

        //Lien
        final TextView nomJaugeCourante = findViewById(R.id.nomJaugeCourante);



        boutonVitesseRotation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                switch (modeProd.getEtatModeProduction()){
                    case jaugeTensionEnEntree:
                        jaugeTensionEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugePuissanceFournie:
                        jaugePuissanceFournie.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeCourantEnEntree:
                        jaugeCourantEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeEnergieProduite:
                        //A compléter
                        break;
                    case jaugeTemperatureAlternateur:
                        thermometreAlternateur.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTemperatureFrein:
                        thermometreFrein.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeVitesseRotation);
                jaugeVitesseRotation.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeVitesseRotation);

            }
        });

        boutonTensionEnEntree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                switch (modeProd.getEtatModeProduction()){
                    case jaugeVitesseRotation:
                        jaugeVitesseRotation.setVisibility(View.INVISIBLE);
                        break;
                    case jaugePuissanceFournie:
                        jaugePuissanceFournie.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeCourantEnEntree:
                        jaugeCourantEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeEnergieProduite:
                        //A compléter
                        break;
                    case jaugeTemperatureAlternateur:
                        thermometreAlternateur.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTemperatureFrein:
                        thermometreFrein.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeTensionEnEntree);
                jaugeTensionEnEntree.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTensionEnEntree);


                //A compléter

            }
        });

        boutonCourantEnEntree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                switch (modeProd.getEtatModeProduction()){
                    case jaugeVitesseRotation:
                        jaugeVitesseRotation.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTensionEnEntree:
                        jaugeTensionEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugePuissanceFournie:
                        jaugePuissanceFournie.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeEnergieProduite:
                        //A compléter
                        break;
                    case jaugeTemperatureAlternateur:
                        thermometreAlternateur.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTemperatureFrein:
                        thermometreFrein.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeCourantEnEntree);
                jaugeCourantEnEntree.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeCourantEnEntree);

                //A compléter

            }
        });

        boutonPuissanceFournie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                switch (modeProd.getEtatModeProduction()){
                    case jaugeVitesseRotation:
                        jaugeVitesseRotation.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTensionEnEntree:
                        jaugeTensionEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeCourantEnEntree:
                        jaugeCourantEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeEnergieProduite:
                        //A compléter
                        break;
                    case jaugeTemperatureAlternateur:
                        thermometreAlternateur.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTemperatureFrein:
                        thermometreFrein.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugePuissanceFournie);
                jaugePuissanceFournie.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugePuissanceFournie);

            }
        });

        boutonEnergieProduite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                switch (modeProd.getEtatModeProduction()){
                    case jaugeVitesseRotation:
                        jaugeVitesseRotation.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTensionEnEntree:
                        jaugeTensionEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeCourantEnEntree:
                        jaugeCourantEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugePuissanceFournie:
                        jaugePuissanceFournie.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTemperatureAlternateur:
                        thermometreAlternateur.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTemperatureFrein:
                        thermometreFrein.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeEnergieProduite);
                //A compléter
                nomJaugeCourante.setText(nomJaugeEnergieProduite);


            }
        });

        boutonTemperatureAlternateur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                switch (modeProd.getEtatModeProduction()){
                    case jaugeVitesseRotation:
                        jaugeVitesseRotation.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTensionEnEntree:
                        jaugeTensionEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeCourantEnEntree:
                        jaugeCourantEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeEnergieProduite:
                        //A compléter
                        break;
                    case jaugePuissanceFournie:
                        jaugePuissanceFournie.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTemperatureFrein:
                        thermometreFrein.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeTemperatureAlternateur);
                thermometreAlternateur.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTemperatureAlternateur);

            }
        });

        boutonTemperatureFrein.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                switch (modeProd.getEtatModeProduction()){
                    case jaugeVitesseRotation:
                        jaugeVitesseRotation.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTensionEnEntree:
                        jaugeTensionEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeCourantEnEntree:
                        jaugeCourantEnEntree.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeEnergieProduite:
                        //A compléter
                        break;
                    case jaugePuissanceFournie:
                        jaugePuissanceFournie.setVisibility(View.INVISIBLE);
                        break;
                    case jaugeTemperatureAlternateur:
                        thermometreAlternateur.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeTemperatureFrein);
                thermometreFrein.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTemperatureFrein);

            }
        });



    }
}
