package com.dutinfo.gimeole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReglageJaugeActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        //Permet de bloquer la flêche retour de l'appareil
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglage_jauge);

        //Récupération des valeurs précédentes des jauges du ModeProd
        Intent intent = getIntent();
        final double[] tabMinMax = intent.getDoubleArrayExtra("tabMinMax");


        //Lien entre les zones de saisies de l'interface et de l'activité
        final EditText minVitesseRotation = findViewById(R.id.minVitesseRotation);
        final EditText maxVitesseRotation = findViewById(R.id.maxVitesseRotation);
        final EditText minTensionEnEntree = findViewById(R.id.minTensionEnEntree);
        final EditText maxTensionEnEntree = findViewById(R.id.maxTensionEnEntree);
        final EditText minCourantEnEntree = findViewById(R.id.minCourantEnEntree);
        final EditText maxCourantEnEntree = findViewById(R.id.maxCourantEnEntree);
        final EditText minTensionEnSortie = findViewById(R.id.minTensionEnSortie);
        final EditText maxTensionEnSortie = findViewById(R.id.maxTensionEnSortie);
        final EditText minCourantEnSortie = findViewById(R.id.minCourantEnSortie);
        final EditText maxCourantEnSortie = findViewById(R.id.maxCourantEnSortie);
        final EditText minPuissanceFournie = findViewById(R.id.minPuissanceFournie);
        final EditText maxPuissanceFournie = findViewById(R.id.maxPuissanceFournie);
        final EditText minEnergieProduite = findViewById(R.id.minEnergieProduite);
        final EditText maxEnergieProduite = findViewById(R.id.maxEnergieProduite);
        final EditText minTemperatureAlternateur = findViewById(R.id.minTemperatureAlternateur);
        final EditText maxTemperatureAlternateur = findViewById(R.id.maxTemperatureAlternateur);
        final EditText minTemperatureFrein = findViewById(R.id.minTemperatureFrein);
        final EditText maxTemperatureFrein = findViewById(R.id.maxTemperatureFrein);

        //Affichage des valeurs prédéfinis du min max du modeTest actuel
        minVitesseRotation.setText(Double.toString(tabMinMax[0]));
        maxVitesseRotation.setText(Double.toString(tabMinMax[1]));
        minTensionEnEntree.setText(Double.toString(tabMinMax[2]));
        maxTensionEnEntree.setText(Double.toString(tabMinMax[3]));
        minCourantEnEntree.setText(Double.toString(tabMinMax[4]));
        maxCourantEnEntree.setText(Double.toString(tabMinMax[5]));
        minTensionEnSortie.setText(Double.toString(tabMinMax[6]));
        maxTensionEnSortie.setText(Double.toString(tabMinMax[7]));
        minCourantEnSortie.setText(Double.toString(tabMinMax[8]));
        maxCourantEnSortie.setText(Double.toString(tabMinMax[9]));
        minPuissanceFournie.setText(Double.toString(tabMinMax[10]));
        maxPuissanceFournie.setText(Double.toString(tabMinMax[11]));
        minEnergieProduite.setText(Double.toString(tabMinMax[12]));
        maxEnergieProduite.setText(Double.toString(tabMinMax[13]));
        minTemperatureAlternateur.setText(Double.toString(tabMinMax[14]));
        maxTemperatureAlternateur.setText(Double.toString(tabMinMax[15]));
        minTemperatureFrein.setText(Double.toString(tabMinMax[16]));
        maxTemperatureFrein.setText(Double.toString(tabMinMax[17]));


        Button boutonReglageJauges = findViewById(R.id.boutonReglageActiviteReglage);

        boutonReglageJauges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                String minVitesseRotationSaisie, maxVitesseRotationSaisie, minTensionEnEntreeSaisie, maxTensionEnEntreeSaisie, minCourantEnEntreeSasie, maxCourantEnEntreeSaisie, minTensionEnSortieSaisie, maxTensionEnSortieSaisie, minCourantEnSortieSasie, maxCourantEnSortieSaisie, minPuissanceFournieSaisie, maxPuissanceFournieSaisie, minEnergieProduiteSaisie, maxEnergieProduiteSaisie, minTemperatureAlternateurSaisie, maxTemperatureAlternateurSaisie, minTemperatureFreinSaisie, maxTemperatureFreinSaisie;
                minVitesseRotationSaisie=minVitesseRotation.getText().toString();
                maxVitesseRotationSaisie=maxVitesseRotation.getText().toString();
                minTensionEnEntreeSaisie=minTensionEnEntree.getText().toString();
                maxTensionEnEntreeSaisie=maxTensionEnEntree.getText().toString();
                minCourantEnEntreeSasie=minCourantEnEntree.getText().toString();
                maxCourantEnEntreeSaisie=maxCourantEnEntree.getText().toString();
                minTensionEnSortieSaisie=minTensionEnSortie.getText().toString();
                maxTensionEnSortieSaisie=maxTensionEnSortie.getText().toString();
                minCourantEnSortieSasie=minCourantEnSortie.getText().toString();
                maxCourantEnSortieSaisie=maxCourantEnSortie.getText().toString();
                minPuissanceFournieSaisie=minPuissanceFournie.getText().toString();
                maxPuissanceFournieSaisie=maxPuissanceFournie.getText().toString();
                minEnergieProduiteSaisie=minEnergieProduite.getText().toString();
                maxEnergieProduiteSaisie=maxEnergieProduite.getText().toString();
                minTemperatureAlternateurSaisie=minTemperatureAlternateur.getText().toString();
                maxTemperatureAlternateurSaisie=maxTemperatureAlternateur.getText().toString();
                minTemperatureFreinSaisie=minTemperatureFrein.getText().toString();
                maxTemperatureFreinSaisie=maxTemperatureFrein.getText().toString();

                if (!(minVitesseRotationSaisie.matches("") || maxVitesseRotationSaisie.matches("") || minTensionEnEntreeSaisie.matches("") || maxTensionEnEntreeSaisie.matches("") || minCourantEnEntreeSasie.matches("") || maxCourantEnEntreeSaisie.matches("") || minTensionEnSortieSaisie.matches("") || maxTensionEnSortieSaisie.matches("") || minCourantEnSortieSasie.matches("") || maxCourantEnSortieSaisie.matches("") || minPuissanceFournieSaisie.matches("") || maxPuissanceFournieSaisie.matches("") || minEnergieProduiteSaisie.matches("") || maxEnergieProduiteSaisie.matches("") || minTemperatureAlternateurSaisie.matches("")|| maxTemperatureAlternateurSaisie.matches("")|| minTemperatureFreinSaisie.matches("")|| maxTemperatureFreinSaisie.matches("")))
                {
                    tabMinMax[0]= Double.parseDouble(minVitesseRotationSaisie);
                    tabMinMax[1]= Double.parseDouble(maxVitesseRotationSaisie);
                    tabMinMax[2]= Double.parseDouble(minTensionEnEntreeSaisie);
                    tabMinMax[3]= Double.parseDouble(maxTensionEnEntreeSaisie);
                    tabMinMax[4]= Double.parseDouble(minCourantEnEntreeSasie);
                    tabMinMax[5]= Double.parseDouble(maxCourantEnEntreeSaisie);
                    tabMinMax[6]= Double.parseDouble(minTensionEnSortieSaisie);
                    tabMinMax[7]= Double.parseDouble(maxTensionEnSortieSaisie);
                    tabMinMax[8]= Double.parseDouble(minCourantEnSortieSasie);
                    tabMinMax[9]= Double.parseDouble(maxCourantEnSortieSaisie);
                    tabMinMax[10]= Double.parseDouble(minPuissanceFournieSaisie);
                    tabMinMax[11]= Double.parseDouble(maxPuissanceFournieSaisie);
                    tabMinMax[12]= Double.parseDouble(minEnergieProduiteSaisie);
                    tabMinMax[13]= Double.parseDouble(maxEnergieProduiteSaisie);
                    tabMinMax[14]= Double.parseDouble(minTemperatureAlternateurSaisie);
                    tabMinMax[15]= Double.parseDouble(maxTemperatureAlternateurSaisie);
                    tabMinMax[16]= Double.parseDouble(minTemperatureFreinSaisie);
                    tabMinMax[17]= Double.parseDouble(maxTemperatureFreinSaisie);

                    if(tableauCorrect(tabMinMax))
                    {
                        Intent modeProdActivity = new Intent();
                        modeProdActivity.putExtra("tabMinMax", tabMinMax);
                        setResult(1,modeProdActivity);
                        finish();
                    }
                    else
                    {
                        Toast messageTableauIncorrect = Toast.makeText(getApplicationContext(),
                                "Une ou plusieurs valeurs minimales sont supérieurs ou égal aux maximales !",
                                Toast.LENGTH_SHORT);

                        messageTableauIncorrect.show();
                    }
                }
                else
                {
                    Toast messageTableauIncomplet = Toast.makeText(getApplicationContext(),
                            "Une ou plusieurs zones de saisies sont vide !",
                            Toast.LENGTH_SHORT);

                    messageTableauIncomplet.show();
                }



            }
        });


    }

    public boolean tableauCorrect(double[] tab){
        for (int i = 0; i<=tab.length-2;i=i+2){
            if (tab[i]>=tab[i+1]){
                return false;
            }
        }
        return true;
    }
}
