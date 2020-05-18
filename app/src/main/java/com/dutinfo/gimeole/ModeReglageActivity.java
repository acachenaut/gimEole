package com.dutinfo.gimeole;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dutinfo.gimeole.ClassesUtiles.BoiteAOutils;
import com.dutinfo.gimeole.ClassesUtiles.ModeReglage;
import com.dutinfo.gimeole.ClassesUtiles.Point;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.DeviceCallback;

public class ModeReglageActivity extends AppCompatActivity {

    //Création du modeProduction
    final ModeReglage modeReglage = new ModeReglage();

    //Nécessaire à la connexion Bluetooth
    private Bluetooth bluetooth;
    private BluetoothDevice device;

    //Création valeurs à afficher
    TextView vitesseRotation, tensionEnEntree, courantEnEntree, puissanceFournie, energieProduite, temperatureAlternateur,temperatureFrein;

    //Création de l'indicateur Bluetooth et de du nom du périphérique connecté
    ImageView logoBluetooth;
    TextView nomPeripheriqueBluetooth;

    //Création des bouttons de l'activité
    Button boutonRAZenergie, boutonModeProduction, boutonValiderPoint, boutonPointPrecedent, boutonPointSuivant,boutonSupprimerPoint, boutonEnvoyerSerieDePoint;

    //Création du graphique
    GraphView graphique;

    EditText abscisseSaisie,ordonneeSaisie;

    //Serie de point affiché actuellement
    LineGraphSeries<DataPoint> pointsAffiches = new LineGraphSeries<>();

    //indicateur permettant de savoir si le bluetooth a été deconnecté à cause d'un changement d'activité
    boolean estDeconnecteDuBluetoothCarChangementDActivite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_reglage);

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

        estDeconnecteDuBluetoothCarChangementDActivite=false;

        //Lien entre les boutons de l'interface et l'activité
        boutonRAZenergie = findViewById(R.id.t_boutonRAZenergie);
        boutonModeProduction = findViewById(R.id.t_boutonModeProd);
        boutonValiderPoint = findViewById(R.id.t_boutonValidationPoint);
        boutonPointPrecedent = findViewById(R.id.t_boutonPointPrécédent);
        boutonPointSuivant = findViewById(R.id.t_boutonPointSuivant);
        boutonSupprimerPoint = findViewById(R.id.t_boutonSupprimerPoint);
        boutonEnvoyerSerieDePoint = findViewById(R.id.t_boutonEnvoyerSerieDePoints);

        //Lien entre le graphique de l'interface et l'activité
        graphique = findViewById(R.id.t_graphique);

        //Lien entre les zones de saisis de l'interface et l'activité
        abscisseSaisie = findViewById(R.id.t_saisieAbscisse);
        ordonneeSaisie = findViewById(R.id.t_saiseOrdonnee);

        initialiserGraphique();

        boutonValiderPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                String abscisse = abscisseSaisie.getText().toString();
                String ordonnee = ordonneeSaisie.getText().toString();
                if (!(abscisse.matches("") || ordonnee.matches("")))
                {
                 boolean estAjoute;
                 estAjoute= modeReglage.ajouterUnPointEtTrierTableau(Double.parseDouble(abscisse),Double.parseDouble(ordonnee));
                 if(estAjoute){
                     afficherLesPointsSurLeGraphique();
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
                modeReglage.getEnergieProduite().setValRAZ(modeReglage.getEnergieProduite().getValCourante());
            }
        });

        boutonModeProduction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(ModeReglageActivity.this, MainActivity.class);
                intent.putExtra("device", device);
                startActivity(intent);
                finish();
            }
        });


        boutonPointPrecedent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(modeReglage.getNombreDePoints()!=0){
                    switch (modeReglage.getPointSelectionne()){
                        case -1:
                            modeReglage.setPointSelectionne(0);
                            break;
                        case 0:
                            Toast messagePasDePointPrecedent = Toast.makeText(getApplicationContext(),
                                    "C'est déjà le premier point !",
                                    Toast.LENGTH_SHORT);
                            messagePasDePointPrecedent.show();
                            break;
                        default:
                            modeReglage.setPointSelectionne(modeReglage.getPointSelectionne()-1);
                            break;
                    }
                    afficherPointSelectionne(modeReglage.getPointSelectionne());
                }
                else{
                    Toast messageAucunPointSaisi = Toast.makeText(getApplicationContext(),
                            "Aucun point n'a été ajouté !",
                            Toast.LENGTH_SHORT);
                    messageAucunPointSaisi.show();
                }


            }
        });

        boutonPointSuivant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(modeReglage.getNombreDePoints()!=0){
                    if(modeReglage.getPointSelectionne()== modeReglage.getNombreDePoints()-1){
                        Toast messagePasDePointSuivant = Toast.makeText(getApplicationContext(),
                                "C'est déjà le dernier point !",
                                Toast.LENGTH_SHORT);
                        messagePasDePointSuivant.show();
                    }
                    else{
                        modeReglage.setPointSelectionne(modeReglage.getPointSelectionne()+1);
                    }
                    afficherPointSelectionne(modeReglage.getPointSelectionne());
                }
                else{
                    Toast messageAucunPointSaisi = Toast.makeText(getApplicationContext(),
                            "Aucun point n'a été ajouté !",
                            Toast.LENGTH_SHORT);
                    messageAucunPointSaisi.show();
                }

            }
        });

        boutonSupprimerPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(modeReglage.getPointSelectionne()!=-1){
                    modeReglage.supprimerPointSelectionne(modeReglage.getPointSelectionne());
                    modeReglage.setPointSelectionne(-1);
                    afficherLesPointsSurLeGraphique();
                }
                else{
                    Toast messagePasDePointSelectionne = Toast.makeText(getApplicationContext(),
                            "Aucun point n'a été selectionné !",
                            Toast.LENGTH_SHORT);
                    messagePasDePointSelectionne.show();
                }

            }
        });

        boutonEnvoyerSerieDePoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(modeReglage.getNombreDePoints()!=0){
                    for(Point pointCourant : modeReglage.getPointsDuGraphique()){
                        bluetooth.send("n"+pointCourant.getAbscisse()+";"+pointCourant.getOrdonnee());
                    }
                }
                else{
                    Toast messageAucunPointSaisi = Toast.makeText(getApplicationContext(),
                            "Aucun point n'a été ajouté !",
                            Toast.LENGTH_SHORT);
                    messageAucunPointSaisi.show();
                }

            }
        });
    }

    public void afficherValeur (String chaineRecuParBluetooth){
        String premierCaractere = (chaineRecuParBluetooth.substring(0,1));
        String valeurCourante = (chaineRecuParBluetooth.substring(1));
        switch (premierCaractere){
            case "$" :
                modeReglage.getVitesseRotation().setValCourante(Double.parseDouble(valeurCourante));
                vitesseRotation.setText(Double.toString(modeReglage.getVitesseRotation().getValCourante()));

                break;
            case ":" :
                modeReglage.getTensionEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                tensionEnEntree.setText(Double.toString(modeReglage.getTensionEnEntree().getValCourante()));

                break;
            case ";" :
                modeReglage.getCourantEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                courantEnEntree.setText(Double.toString(modeReglage.getCourantEnEntree().getValCourante()));

                break;
            case "%" :
                modeReglage.getPuissanceFournie().setValCourante(Double.parseDouble(valeurCourante));
                puissanceFournie.setText(Double.toString(modeReglage.getPuissanceFournie().getValCourante()));

                break;
            case "!" :
                modeReglage.getEnergieProduite().setValCourante(Double.parseDouble(valeurCourante));
                energieProduite.setText(BoiteAOutils.obtenirEcritureScientifiqueAvecChiffresSignificatifs(3, modeReglage.getEnergieProduite().getValCourante()));

                break;
            case "(" :
                modeReglage.getTemperatureAlternateur().setValCourante(Double.parseDouble(valeurCourante));
                temperatureAlternateur.setText(Double.toString(modeReglage.getTemperatureAlternateur().getValCourante()));

                break;
            case ")" :
                modeReglage.getTemperatureFrein().setValCourante(Double.parseDouble(valeurCourante));
                temperatureFrein.setText(Double.toString(modeReglage.getTemperatureFrein().getValCourante()));

                break;
            default:
                break;
        }
    }

    public void afficherPointSelectionne(int positionDuPoint){
        PointsGraphSeries<DataPoint> pointSelectionne = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(modeReglage.getPointsDuGraphique().get(positionDuPoint).getAbscisse(), modeReglage.getPointsDuGraphique().get(positionDuPoint).getOrdonnee())
    });
        pointSelectionne.setShape(PointsGraphSeries.Shape.POINT);
        pointSelectionne.setColor(Color.RED);
        pointSelectionne.setShape(PointsGraphSeries.Shape.POINT);
        graphique.removeAllSeries();
        graphique.addSeries(pointsAffiches);
        graphique.addSeries(pointSelectionne);
    }

    public void afficherLesPointsSurLeGraphique(){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(Point pointCourant : modeReglage.getPointsDuGraphique()){
            series.appendData(new DataPoint(pointCourant.getAbscisse(),pointCourant.getOrdonnee()),true,10);
        }
        pointsAffiches=series;
        graphique.removeAllSeries();
        pointsAffiches.setDrawDataPoints(true);
        pointsAffiches.setDataPointsRadius(10);
        pointsAffiches.setThickness(8);
        graphique.getViewport().setYAxisBoundsManual(true);
        graphique.getViewport().setMinY(0);
        graphique.getViewport().setMaxY(modeReglage.getMaxOrdonee());
        graphique.getViewport().setXAxisBoundsManual(true);
        graphique.getViewport().setMinX(0);
        graphique.getViewport().setMaxX(modeReglage.getMaxAbscisse());
        graphique.addSeries(pointsAffiches);
    }

    public void initialiserGraphique(){
        graphique.getViewport().setYAxisBoundsManual(true);
        graphique.getViewport().setMinY(0);
        graphique.getViewport().setMaxY(modeReglage.getCourantEnEntree().getValMaxJauge());
        graphique.getViewport().setXAxisBoundsManual(true);
        graphique.getViewport().setMinX(0);
        graphique.getViewport().setMaxX(modeReglage.getVitesseRotation().getValMaxJauge());
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
            if(!estDeconnecteDuBluetoothCarChangementDActivite){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bluetooth.connectToDevice(device);
                    }
                }, 3000);
            }
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
        estDeconnecteDuBluetoothCarChangementDActivite = true;
        bluetooth.disconnect();

    }
}
