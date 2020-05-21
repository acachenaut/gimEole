package com.dutinfo.gimeole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ReglageJaugeActivity extends AppCompatActivity {

    Spinner listeDeroulanteVitesseRotation, listeDeroulanteTensionEnEntree, listeDeroulanteCourantEnEntree, listeDeroulanteTensionEnSortie, listeDeroulanteCourantEnSortie, listeDeroulantePuissanceFournie, listeDeroulanteEnergiePorduite, listeDeroulanteTemperatureAlternateur, listeDeroulanteTemperatureFrein;

    Button boutonReglageJauges;

    double[] tabMinMax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglage_jauge);

        //Récupération des valeurs précédentes des jauges du ModeProduction
        Intent intent = getIntent();
        tabMinMax = intent.getDoubleArrayExtra("tabMinMax");


        //Lien entre les listes déroulantes de l'interface et de l'activité
        listeDeroulanteVitesseRotation = findViewById(R.id.listeDeroulanteVitesseDeRotation);
        listeDeroulanteTensionEnEntree = findViewById(R.id.listeDeroulanteTensionEnEntree);
        listeDeroulanteCourantEnEntree = findViewById(R.id.listeDeroulanteCourantEnEntree);
        listeDeroulanteTensionEnSortie = findViewById(R.id.listeDeroulanteTensionEnSortie);
        listeDeroulanteCourantEnSortie = findViewById(R.id.listeDeroulanteCourantEnSortie);
        listeDeroulantePuissanceFournie = findViewById(R.id.listeDeroulantePuissanceFournie);
        listeDeroulanteEnergiePorduite = findViewById(R.id.listeDeroulanteEnergieProduite);
        listeDeroulanteTemperatureAlternateur = findViewById(R.id.listeDeroulanteTemperatureAlternateur);
        listeDeroulanteTemperatureFrein=findViewById(R.id.listeDeroulanteTemperatureFrein);


        //Faire apparaître les différentes possibilités dans les listes déroulantes
        chargerListeDeroulante(listeDeroulanteVitesseRotation, 1, 500, 4000, 500);
        chargerListeDeroulante(listeDeroulanteTensionEnEntree, 3, 50, 200, 50);
        chargerListeDeroulante(listeDeroulanteCourantEnEntree, 5, 5, 50, 5);
        chargerListeDeroulante(listeDeroulanteTensionEnSortie, 7, 20, 100, 10);
        chargerListeDeroulante(listeDeroulanteCourantEnSortie, 9, 10, 50, 5);
        chargerListeDeroulante(listeDeroulantePuissanceFournie, 11, 200, 1500, 100);
        chargerListeDeroulante(listeDeroulanteEnergiePorduite, 13, 100, 10000, 100);
        chargerListeDeroulante(listeDeroulanteTemperatureAlternateur, 15, 15, 30, 5);
        chargerListeDeroulante(listeDeroulanteTemperatureFrein, 17, 15, 30, 5);

        //Lien entre le bouton de l'interface et de l'activité
        boutonReglageJauges = findViewById(R.id.boutonReglageActiviteReglage);

        listeDeroulanteVitesseRotation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tabMinMax[1] = Double.parseDouble(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listeDeroulanteTensionEnEntree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tabMinMax[3] = Double.parseDouble(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listeDeroulanteCourantEnEntree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tabMinMax[5] = Double.parseDouble(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listeDeroulanteTensionEnSortie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tabMinMax[7] = Double.parseDouble(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listeDeroulanteCourantEnSortie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tabMinMax[9] = Double.parseDouble(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listeDeroulantePuissanceFournie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tabMinMax[11] = Double.parseDouble(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listeDeroulanteEnergiePorduite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tabMinMax[13] = Double.parseDouble(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listeDeroulanteTemperatureAlternateur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tabMinMax[15] = Double.parseDouble(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listeDeroulanteTemperatureFrein.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tabMinMax[17] = Double.parseDouble(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        boutonReglageJauges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Intent modeProdActivity = new Intent();
                modeProdActivity.putExtra("tabMinMax", tabMinMax);
                setResult(1,modeProdActivity);
                finish();
            }
        });


    }

    public void chargerListeDeroulante(Spinner listeCourante, int positionDuMaxDansLeTableau, int valMinDuMaxDeLaJauge, int valMaxDuMaxDeLaJauge, int pas){
        List<String> list = new ArrayList<String>();
        list.add(String.valueOf((int) tabMinMax[positionDuMaxDansLeTableau]));
        int valMin = valMinDuMaxDeLaJauge;
        int increment = pas;
        while(valMin<=valMaxDuMaxDeLaJauge){
            if (valMin!=tabMinMax[positionDuMaxDansLeTableau]){
                list.add(String.valueOf(valMin));
            }
            valMin+=increment;
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeCourante.setAdapter(dataAdapter);
    }


    @Override
    public void onBackPressed() {
        //Permet de bloquer la flêche retour de l'appareil
    }
}
