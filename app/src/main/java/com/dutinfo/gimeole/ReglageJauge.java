package com.dutinfo.gimeole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReglageJauge extends AppCompatActivity {
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
        final EditText minPuissanceFournie = findViewById(R.id.minPuissanceFournie);
        final EditText maxPuissanceFournie = findViewById(R.id.maxPuissanceFournie);
        final EditText minEnergieProduite = findViewById(R.id.minEnergieProduite);
        final EditText maxEnergieProduite = findViewById(R.id.maxEnergieProduite);
        final EditText minTemperatureAlternateur = findViewById(R.id.minTemperatureAlternateur);
        final EditText maxTemperatureAlternateur = findViewById(R.id.maxTemperatureAlternateur);
        EditText minTemperatureFrein = findViewById(R.id.minTemperatureFrein);
        final EditText maxTemperatureFrein = findViewById(R.id.maxTemperatureFrein);

        //Affichage des valeurs prédéfinis du min max du modeProd actuel
        minVitesseRotation.setText(Double.toString(tabMinMax[0]));
        maxVitesseRotation.setText(Double.toString(tabMinMax[1]));
        minTensionEnEntree.setText(Double.toString(tabMinMax[2]));
        maxTensionEnEntree.setText(Double.toString(tabMinMax[3]));
        minCourantEnEntree.setText(Double.toString(tabMinMax[4]));
        maxCourantEnEntree.setText(Double.toString(tabMinMax[5]));
        minPuissanceFournie.setText(Double.toString(tabMinMax[6]));
        maxPuissanceFournie.setText(Double.toString(tabMinMax[7]));
        minEnergieProduite.setText(Double.toString(tabMinMax[8]));
        maxEnergieProduite.setText(Double.toString(tabMinMax[9]));
        minTemperatureAlternateur.setText(Double.toString(tabMinMax[10]));
        maxTemperatureAlternateur.setText(Double.toString(tabMinMax[11]));
        minTemperatureFrein.setText(Double.toString(tabMinMax[12]));
        maxTemperatureFrein.setText(Double.toString(tabMinMax[13]));


        Button boutonReglageJauges = findViewById(R.id.boutonReglageActiviteReglage);

        boutonReglageJauges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                String minVitesseRotationSaisie, maxVitesseRotationSaisie, minTensionEnEntreeSaisie, maxTensionEnEntreeSaisie, minCourantEnEntreeSasie, maxCourantEnEntreeSaisie, minPuissanceFournieSaisie, maxPuissanceFournieSaisie, minEnergieProduiteSaisie, maxEnergieProduiteSaisie, minTemperatureAlternateurSaisie, maxTemperatureAlternateurSaisie, minTemperatureFreinSaisie, maxTemperatureFreinSaisie;
                minVitesseRotationSaisie=minVitesseRotation.getText().toString();
                maxVitesseRotationSaisie=maxVitesseRotation.getText().toString();
                minTensionEnEntreeSaisie=minTensionEnEntree.getText().toString();
                maxTensionEnEntreeSaisie=maxTensionEnEntree.getText().toString();
                minCourantEnEntreeSasie=minCourantEnEntree.getText().toString();
                maxCourantEnEntreeSaisie=maxCourantEnEntree.getText().toString();
                minPuissanceFournieSaisie=minPuissanceFournie.getText().toString();
                maxPuissanceFournieSaisie=maxPuissanceFournie.getText().toString();
                minEnergieProduiteSaisie=minEnergieProduite.getText().toString();
                maxEnergieProduiteSaisie=maxEnergieProduite.getText().toString();
                minTemperatureAlternateurSaisie=minTemperatureAlternateur.getText().toString();
                maxTemperatureAlternateurSaisie=maxTemperatureAlternateur.getText().toString();
                minTemperatureFreinSaisie=maxTemperatureFrein.getText().toString();
                maxTemperatureFreinSaisie=maxTemperatureFrein.getText().toString();

                if (!(minVitesseRotationSaisie.matches("") || maxVitesseRotationSaisie.matches("") || minTensionEnEntreeSaisie.matches("") || maxTensionEnEntreeSaisie.matches("") || minCourantEnEntreeSasie.matches("") || maxCourantEnEntreeSaisie.matches("") || minPuissanceFournieSaisie.matches("") || maxPuissanceFournieSaisie.matches("") || minEnergieProduiteSaisie.matches("") || maxEnergieProduiteSaisie.matches("") || minTemperatureAlternateurSaisie.matches("")|| maxTemperatureAlternateurSaisie.matches("")|| minTemperatureFreinSaisie.matches("")|| maxTemperatureFreinSaisie.matches("")))
                {
                    tabMinMax[0]= Double.parseDouble(minVitesseRotationSaisie);
                    tabMinMax[1]= Double.parseDouble(maxVitesseRotationSaisie);
                    tabMinMax[2]= Double.parseDouble(minTensionEnEntreeSaisie);
                    tabMinMax[3]= Double.parseDouble(maxTensionEnEntreeSaisie);
                    tabMinMax[4]= Double.parseDouble(minCourantEnEntreeSasie);
                    tabMinMax[5]= Double.parseDouble(maxCourantEnEntreeSaisie);
                    tabMinMax[6]= Double.parseDouble(minPuissanceFournieSaisie);
                    tabMinMax[7]= Double.parseDouble(maxPuissanceFournieSaisie);
                    tabMinMax[8]= Double.parseDouble(minEnergieProduiteSaisie);
                    tabMinMax[9]= Double.parseDouble(maxEnergieProduiteSaisie);
                    tabMinMax[10]= Double.parseDouble(minTemperatureAlternateurSaisie);
                    tabMinMax[11]= Double.parseDouble(maxTemperatureAlternateurSaisie);
                    tabMinMax[12]= Double.parseDouble(minTemperatureFreinSaisie);
                    tabMinMax[13]= Double.parseDouble(maxTemperatureFreinSaisie);

                    if(tableauCorrect(tabMinMax))
                    {
                        Intent modeProdActivity = new Intent(ReglageJauge.this, MainActivity.class);
                        modeProdActivity.putExtra("tabMinMax", tabMinMax);
                        startActivity(modeProdActivity);
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
