package com.dutinfo.gimeole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.biansemao.widget.ThermometerView;
import com.sccomponents.gauges.gr008.GR008;



public class MainActivity extends AppCompatActivity {
    //Création du modeProduction
    final ModeProduction modeProd = new ModeProduction();

    //Lien entre les jauges de l'interface et de l'activté
     GR008 jaugeVitesseRotation;
     GR008 jaugeTensionEnEntree;
     GR008 jaugeCourantEnEntree;
     GR008 jaugePuissanceFournie;
     RoundCornerProgressBar jaugeEnergieProduite;
     ThermometerView thermometreAlternateur;
     ThermometerView thermometreFrein;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Lien entre les jauges de l'interface et de l'activté
        jaugeVitesseRotation = findViewById(R.id.jaugeVitesseRotation);
        jaugeTensionEnEntree = findViewById(R.id.jaugeTensionEnEntree);
        jaugeCourantEnEntree = findViewById(R.id.jaugeCourantEnEntree);
        jaugePuissanceFournie = findViewById(R.id.jaugePuissanceFournie);
        jaugeEnergieProduite = findViewById(R.id.jaugeEnergieProduite);
        thermometreAlternateur = findViewById(R.id.thermometreAlternateur);
        thermometreFrein = findViewById(R.id.thermometreFrein);

        //Nom des jauges
        final String nomJaugeVitesseRotation = getResources().getString(R.string.nomJaugeVitesseDeRotation);
        final String nomJaugeTensionEnEntree = getResources().getString(R.string.nomJaugeTensionEnEntree);
        final String nomJaugeCourantEnEntree = getResources().getString(R.string.nomJaugeCourantEnEntree);
        final String nomJaugeEnergieProduite = getResources().getString(R.string.nomJaugeEnergieProduite);
        final String nomJaugePuissanceFournie = getResources().getString(R.string.nomJaugePuissanceFournie);
        final String nomJaugeTemperatureAlternateur = getResources().getString(R.string.nomJaugeTemperatureAlternateur);
        final String nomJaugeTemperatureFrein = getResources().getString(R.string.nomJaugeTemperatureFrein);


        //Lien entre les boutons de l'interface et l'activité
        Button boutonVitesseRotation = findViewById(R.id.boutonVitesseDeRotation);
        Button boutonTensionEnEntree = findViewById(R.id.boutonTensionEnEntree);
        Button boutonCourantEnEntree = findViewById(R.id.boutonCourantEnEntree);
        Button boutonPuissanceFournie = findViewById(R.id.boutonPuissanceFournie);
        Button boutonEnergieProduite = findViewById(R.id.boutonEnergieProduite);
        Button boutonTemperatureAlternateur = findViewById(R.id.boutonTemperatureAlternateur);
        Button boutonTemperatureFrein = findViewById(R.id.boutonTemperatureFrein);
        Button boutonReglageJauges = findViewById(R.id.boutonReglage);

        //Lien entre le nom de la jauge courante de l'interface et l'activité
        final TextView nomJaugeCourante = findViewById(R.id.nomJaugeCourante);

        /*C'est un test
        //ThermometerView.ThermometerBuilder essaie = new ThermometerView.ThermometerBuilder(this.getApplicationContext());
        //essaie.setMinScaleValue(26);
        //essaie.builder();
        */

        jaugeVitesseRotation.setMinValue(modeProd.getVitesseRotation().getValMinJauge());
        jaugeVitesseRotation.setMaxValue(modeProd.getVitesseRotation().getValMaxJauge());





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
                        jaugeEnergieProduite.setVisibility(View.INVISIBLE);
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
                        jaugeEnergieProduite.setVisibility(View.INVISIBLE);
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
                        jaugeEnergieProduite.setVisibility(View.INVISIBLE);
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
                        jaugeEnergieProduite.setVisibility(View.INVISIBLE);
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
                jaugeEnergieProduite.setVisibility(View.VISIBLE);
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
                        jaugeEnergieProduite.setVisibility(View.INVISIBLE);
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
                        jaugeEnergieProduite.setVisibility(View.INVISIBLE);
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

        boutonReglageJauges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent reglageActivity = new Intent(MainActivity.this, ReglageJauge.class);
                reglageActivity.putExtra("tabMinMax", modeProd.getMinMaxDesJauges());
                startActivityForResult(reglageActivity, 1);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            modeProd.setMinMaxDesJauges(data.getDoubleArrayExtra("tabMinMax"));
            jaugeVitesseRotation.setMinValue(modeProd.getVitesseRotation().getValMinJauge());
            jaugeVitesseRotation.setMaxValue(modeProd.getVitesseRotation().getValMaxJauge());
            jaugeTensionEnEntree.setMinValue(modeProd.getTensionEnEntree().getValMinJauge());
            jaugeTensionEnEntree.setMaxValue(modeProd.getTensionEnEntree().getValMaxJauge());
            jaugeCourantEnEntree.setMinValue(modeProd.getCourantEnEntree().getValMinJauge());
            jaugeCourantEnEntree.setMaxValue(modeProd.getCourantEnEntree().getValMaxJauge());
            jaugePuissanceFournie.setMinValue(modeProd.getPuissanceFournie().getValMinJauge());
            jaugePuissanceFournie.setMaxValue(modeProd.getPuissanceFournie().getValMaxJauge());
        }
    }

}
