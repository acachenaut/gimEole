package com.dutinfo.gimeole;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dutinfo.gimeole.ClassesUtiles.BoiteAOutils;
import com.dutinfo.gimeole.ClassesUtiles.ModeTest;
import com.dutinfo.gimeole.ClassesUtiles.Point;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.DeviceCallback;

public class ModeTestActivity extends AppCompatActivity {

    //Création du modeProduction
    final ModeTest modeTest = new ModeTest();

    //Nécessaire à la connexion Bluetooth
    private Bluetooth bluetooth;
    private BluetoothDevice device;

    //Création valeurs à afficher
    TextView vitesseRotation, tensionEnEntree, courantEnEntree, puissanceFournie, energieProduite, temperatureAlternateur,temperatureFrein, nomJaugeCourante;

    //Création de l'indicateur Bluetooth et de du nom du périphérique connecté
    ImageView logoBluetooth;
    TextView nomPeripheriqueBluetooth;

    //Création des bouttons de l'activité
    Button boutonRAZenergie, boutonFreinage, boutonModeProduction, boutonValiderPoint;

    //Création du graphique
    GraphView graphique;

    EditText abscisseSaisie,ordonneeSaisie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_test);

        //Initialisation de la connexion Bluetooth
        device = getIntent().getParcelableExtra("device");
        bluetooth = new Bluetooth(this);
        bluetooth.setCallbackOnUI(this);
        bluetooth.setDeviceCallback(deviceCallback);
        bluetooth.onStart();
        bluetooth.connectToDevice(device);

        //Lien entre les valeurs courante de l'interface et l'activité
        vitesseRotation = findViewById(R.id.t_vitesseRotation);
        tensionEnEntree = findViewById(R.id.t_tensionEnEntree);
        courantEnEntree = findViewById(R.id.t_courantEnEntree);
        puissanceFournie = findViewById(R.id.t_puissanceFournie);
        energieProduite = findViewById(R.id.t_energieProduite);
        temperatureAlternateur = findViewById(R.id.t_temperatureAlternateur);
        temperatureFrein = findViewById(R.id.t_temperatureFrein);

        //Lien entre l'indicateur Bluetooth/nom du périphérique de l'interface et l'activité
        logoBluetooth = findViewById(R.id.logoBluetooth);
        nomPeripheriqueBluetooth = findViewById(R.id.nomPeripheriqueBluetooth);
        nomPeripheriqueBluetooth.setText(getResources().getString(R.string.connecteA));

        //Lien entre les boutons de l'interface et l'activité
        boutonRAZenergie = findViewById(R.id.t_boutonRAZenergie);
        boutonModeProduction = findViewById(R.id.t_boutonModeProd);
        boutonValiderPoint = findViewById(R.id.t_boutonValidationPoint);

        graphique = findViewById(R.id.t_graphique);

        abscisseSaisie = findViewById(R.id.t_saisieAbscisse);
        ordonneeSaisie = findViewById(R.id.t_saiseOrdonnee);

        boutonValiderPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                String abscisse = abscisseSaisie.getText().toString();
                String ordonnee = ordonneeSaisie.getText().toString();
                if (!(abscisse.matches("") || ordonnee.matches("")))
                {
                 boolean estAjoute;
                 estAjoute=modeTest.ajouterUnPointEtTrierTableau(Double.parseDouble(abscisse),Double.parseDouble(ordonnee));
                 if(estAjoute){
                     LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                     for(Point pointCourant : modeTest.getPointsDuGraphique()){
                         series.appendData(new DataPoint(pointCourant.getAbscisse(),pointCourant.getOrdonnee()),true,10);
                     }
                     graphique.removeAllSeries();
                     series.setDrawDataPoints(true);
                     series.setDataPointsRadius(10);
                     series.setThickness(8);
                     graphique.getViewport().setYAxisBoundsManual(true);
                     graphique.getViewport().setMinY(0);
                     graphique.getViewport().setMaxY(20);
                     graphique.getViewport().setXAxisBoundsManual(true);
                     graphique.getViewport().setMinX(0);
                     graphique.getViewport().setMaxX(3000);
                     graphique.getViewport().setScalable(true);
                     graphique.getViewport().setScalableY(true);
                     graphique.getViewport().setScrollable(true);
                     graphique.getViewport().setScrollableY(true);
                     graphique.addSeries(series);
                 }
                 else{
                     Toast messageTableauComplet = Toast.makeText(getApplicationContext(),
                             "Vous avez déjà saisi vos 10 points",
                             Toast.LENGTH_SHORT);
                     messageTableauComplet.show();

                 }
                }
                else
                {
                    Toast messageZoneDeSaisieVide = Toast.makeText(getApplicationContext(),
                            "Une ou plusieurs zones de saisies sont vide !",
                            Toast.LENGTH_SHORT);

                    messageZoneDeSaisieVide.show();
                }
            }
        });

        boutonRAZenergie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                modeTest.getEnergieProduite().setValRAZ(modeTest.getEnergieProduite().getValCourante());
            }
        });

        boutonModeProduction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(ModeTestActivity.this, MainActivity.class);
                intent.putExtra("device", device);
                startActivity(intent);
                finish();
            }
        });
    }

    public void afficherValeur (String chaineRecuParBluetooth){
        String premierCaractere = (chaineRecuParBluetooth.substring(0,1));
        String valeurCourante = (chaineRecuParBluetooth.substring(1));
        switch (premierCaractere){
            case "$" :
                modeTest.getVitesseRotation().setValCourante(Double.parseDouble(valeurCourante));
                vitesseRotation.setText(Double.toString(modeTest.getVitesseRotation().getValCourante()));

                break;
            case ":" :
                modeTest.getTensionEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                tensionEnEntree.setText(Double.toString(modeTest.getTensionEnEntree().getValCourante()));

                break;
            case ";" :
                modeTest.getCourantEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                courantEnEntree.setText(Double.toString(modeTest.getCourantEnEntree().getValCourante()));

                break;
            case "%" :
                modeTest.getPuissanceFournie().setValCourante(Double.parseDouble(valeurCourante));
                puissanceFournie.setText(Double.toString(modeTest.getPuissanceFournie().getValCourante()));

                break;
            case "!" :
                modeTest.getEnergieProduite().setValCourante(Double.parseDouble(valeurCourante));
                energieProduite.setText(BoiteAOutils.obtenirEcritureScientifiqueAvecChiffresSignificatifs(3, modeTest.getEnergieProduite().getValCourante()));

                break;
            case "(" :
                modeTest.getTemperatureAlternateur().setValCourante(Double.parseDouble(valeurCourante));
                temperatureAlternateur.setText(Double.toString(modeTest.getTemperatureAlternateur().getValCourante()));

                break;
            case ")" :
                modeTest.getTemperatureFrein().setValCourante(Double.parseDouble(valeurCourante));
                temperatureFrein.setText(Double.toString(modeTest.getTemperatureFrein().getValCourante()));

                break;
            default:
                break;
        }
    }

    //Partie Bluetooth
    private DeviceCallback deviceCallback = new DeviceCallback() {
        @Override
        public void onDeviceConnected(BluetoothDevice device) {
            logoBluetooth.setImageResource(R.drawable.logobluetoohconnecte);
            nomPeripheriqueBluetooth.setText(getResources().getString(R.string.connecteA)+ " "+device.getName());
        }

        @Override
        public void onDeviceDisconnected(final BluetoothDevice device, String message) {
            logoBluetooth.setImageResource(R.drawable.logobluetoohdeconnecte);
            nomPeripheriqueBluetooth.setText(getResources().getString(R.string.connecteA));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bluetooth.connectToDevice(device);
                }
            }, 3000);
        }

        @Override
        public void onMessage(byte[] message) {
            String str = new String(message);
            afficherValeur(str);
        }

        @Override
        public void onError(int errorCode) {

        }

        @Override
        public void onConnectError(final BluetoothDevice device, String message) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bluetooth.connectToDevice(device);
                }
            }, 3000);
        }
    };

    //Partie Android
    @Override
    public void onBackPressed() {
        //Permet de bloquer la flêche retour de l'appareil
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
        bluetooth.onStop();
        bluetooth.disconnect();

    }
}
