package com.dutinfo.gimeole;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.jjoe64.graphview.series.DataPointInterface;
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
    TextView vitesseRotation, tensionEnEntree, courantEnEntree, puissanceFournie, affichageUniteAmpere,affichageDixiemeAmpere,affichageAmpere;
    //Création de l'indicateur Bluetooth et de du nom du périphérique connecté
    ImageView logoBluetooth;
    TextView nomPeripheriqueBluetooth;

    //Création des bouttons de l'activité
    Button boutonModeProduction,boutonPointPrecedent, boutonPointSuivant,boutonSupprimerPoint,boutonTransfererProfilAppli;
    Button boutonMoins1Ampere,boutonPlus1Ampere,boutonMoins1DixiemeAmpere,boutonPlus1DixiemeAmpere,boutonAjouterPoint,boutonModeSuivant,boutonModifierPoint,boutonModePrecedent;

    //Création du graphique
    GraphView graphique;

    EditText abscisseSaisie,ordonneeSaisie;

    //Série de points affichés actuellement
    LineGraphSeries<DataPoint> profilAppli = new LineGraphSeries<>();
    PointsGraphSeries<DataPoint> pointDeFonctionnement = new PointsGraphSeries<>();
    PointsGraphSeries<DataPoint> pointSelectionne = new PointsGraphSeries<>();


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
        affichageUniteAmpere = findViewById(R.id.t_affichageUniteAmpere);
        affichageDixiemeAmpere = findViewById(R.id.t_affichageDixiemeAmpere);
        affichageAmpere = findViewById(R.id.t_affichageAmpere);

        //Lien entre l'indicateur Bluetooth/nom du périphérique de l'interface et l'activité
        logoBluetooth = findViewById(R.id.logoBluetooth);
        nomPeripheriqueBluetooth = findViewById(R.id.nomPeripheriqueBluetooth);
        nomPeripheriqueBluetooth.setText(getResources().getString(R.string.connecteA));

        estDeconnecteDuBluetoothCarChangementDActivite=false;

        //Lien entre les boutons de l'interface et l'activité
        boutonModeProduction = findViewById(R.id.t_boutonModeProd);
        boutonPointPrecedent = findViewById(R.id.t_boutonPointPrécédent);
        boutonPointSuivant = findViewById(R.id.t_boutonPointSuivant);
        boutonSupprimerPoint = findViewById(R.id.t_boutonSupprimerPoint);
        boutonMoins1Ampere = findViewById(R.id.t_boutonMoins1Ampere);
        boutonPlus1Ampere = findViewById(R.id.t_boutonPlus1Ampere);
        boutonMoins1DixiemeAmpere = findViewById(R.id.t_boutonMoins1DixiemeAmpere);
        boutonPlus1DixiemeAmpere = findViewById(R.id.t_boutonPlus1DixiemeAmpere);
        boutonAjouterPoint = findViewById(R.id.t_boutonAjouterPoint);
        boutonModeSuivant = findViewById(R.id.t_boutonModeSuivant);
        boutonModifierPoint = findViewById(R.id.t_boutonModifierPoint);
        boutonModePrecedent = findViewById(R.id.t_boutonModePrecedent);
        boutonTransfererProfilAppli = findViewById(R.id.t_boutonTransfererProfilAppli);

        //Lien entre le graphique de l'interface et l'activité
        graphique = findViewById(R.id.t_graphique);

        //Lien entre les zones de saisis de l'interface et l'activité
        abscisseSaisie = findViewById(R.id.t_saisieAbscisse);
        ordonneeSaisie = findViewById(R.id.t_saiseOrdonnee);

        initialiserGraphique();

        boutonAjouterPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                 boolean estAjoute;
                 estAjoute= modeReglage.ajouterUnPointEtTrierTableau(pointDeFonctionnement.getHighestValueX(), pointDeFonctionnement.getHighestValueY());
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
                if(modeReglage.getNombreDePointsDuProfilAppli()!=0){
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
                if(modeReglage.getNombreDePointsDuProfilAppli()!=0){
                    if(modeReglage.getPointSelectionne()== modeReglage.getNombreDePointsDuProfilAppli()-1){
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
                    pointSelectionne=null;
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


        boutonModeSuivant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                boutonMoins1Ampere.setVisibility(v.INVISIBLE);
                boutonPlus1Ampere.setVisibility(v.INVISIBLE);
                boutonMoins1DixiemeAmpere.setVisibility(v.INVISIBLE);
                boutonPlus1DixiemeAmpere.setVisibility(v.INVISIBLE);
                boutonAjouterPoint.setVisibility(v.INVISIBLE);
                boutonModeSuivant.setVisibility(v.INVISIBLE);
                affichageUniteAmpere.setVisibility(v.INVISIBLE);
                affichageDixiemeAmpere.setVisibility(v.INVISIBLE);
                affichageAmpere.setVisibility(v.INVISIBLE);
                abscisseSaisie.setVisibility(v.VISIBLE);
                ordonneeSaisie.setVisibility(v.VISIBLE);
                boutonModifierPoint.setVisibility(v.VISIBLE);
                boutonSupprimerPoint.setVisibility(v.VISIBLE);
                boutonModePrecedent.setVisibility(v.VISIBLE);
                boutonPointPrecedent.setVisibility(v.VISIBLE);
                boutonPointSuivant.setVisibility(v.VISIBLE);
            }
        });

        boutonModePrecedent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                boutonMoins1Ampere.setVisibility(v.VISIBLE);
                boutonPlus1Ampere.setVisibility(v.VISIBLE);
                boutonMoins1DixiemeAmpere.setVisibility(v.VISIBLE);
                boutonPlus1DixiemeAmpere.setVisibility(v.VISIBLE);
                boutonAjouterPoint.setVisibility(v.VISIBLE);
                boutonModeSuivant.setVisibility(v.VISIBLE);
                affichageUniteAmpere.setVisibility(v.VISIBLE);
                affichageDixiemeAmpere.setVisibility(v.VISIBLE);
                affichageAmpere.setVisibility(v.VISIBLE);
                abscisseSaisie.setVisibility(v.INVISIBLE);
                ordonneeSaisie.setVisibility(v.INVISIBLE);
                boutonModifierPoint.setVisibility(v.INVISIBLE);
                boutonSupprimerPoint.setVisibility(v.INVISIBLE);
                boutonModePrecedent.setVisibility(v.INVISIBLE);
                boutonPointPrecedent.setVisibility(v.INVISIBLE);
                boutonPointSuivant.setVisibility(v.INVISIBLE);
            }
        });

    }

    public void afficherValeur (String chaineRecuParBluetooth){
        String premierCaractere = (chaineRecuParBluetooth.substring(0,1));
        String valeurCourante = (chaineRecuParBluetooth.substring(1));
        switch (premierCaractere){
            case "$" :
                modeReglage.getVitesseRotation().setValCourante(Double.parseDouble(valeurCourante));
                vitesseRotation.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(4,modeReglage.getVitesseRotation().getValCourante()));
                afficherPointDeFonctionnement();
                break;
            case ":" :
                modeReglage.getTensionEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                tensionEnEntree.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeReglage.getTensionEnEntree().getValCourante()));
                afficherPointDeFonctionnement();
                break;
            case ";" :
                modeReglage.getCourantEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                courantEnEntree.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeReglage.getCourantEnEntree().getValCourante()));
                break;
            case "[" :
                modeReglage.getTensionEnSortie().setValCourante(Double.parseDouble(valeurCourante));
                break;
            case "]" :
                modeReglage.getCourantEnSortie().setValCourante(Double.parseDouble(valeurCourante));
                break;
            case "%" :
                modeReglage.getPuissanceFournie().setValCourante(Double.parseDouble(valeurCourante));
                puissanceFournie.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeReglage.getPuissanceFournie().getValCourante()));
                break;
            case "!" :
                modeReglage.getEnergieProduite().setValCourante(Double.parseDouble(valeurCourante));
                break;
            case "(" :
                modeReglage.getTemperatureAlternateur().setValCourante(Double.parseDouble(valeurCourante));
                break;
            case ")" :
                modeReglage.getTemperatureFrein().setValCourante(Double.parseDouble(valeurCourante));
                break;
            default:
                break;
        }
    }

    public void afficherPointSelectionne(int positionDuPoint){
        PointsGraphSeries<DataPoint> point = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(modeReglage.getPointsDuProfilAppli().get(positionDuPoint).getAbscisse(), modeReglage.getPointsDuProfilAppli().get(positionDuPoint).getOrdonnee())
    });
        pointSelectionne = point;
        pointSelectionne.setShape(PointsGraphSeries.Shape.POINT);
        pointSelectionne.setColor(Color.RED);
        pointSelectionne.setShape(PointsGraphSeries.Shape.POINT);
        graphique.removeAllSeries();
        graphique.addSeries(profilAppli);
        graphique.addSeries(pointSelectionne);
        graphique.addSeries(pointDeFonctionnement);
    }

    public void afficherLesPointsSurLeGraphique(){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(Point pointCourant : modeReglage.getPointsDuProfilAppli()){
            series.appendData(new DataPoint(pointCourant.getAbscisse(),pointCourant.getOrdonnee()),true,10);
        }
        profilAppli =series;
        graphique.removeAllSeries();
        profilAppli.setDrawDataPoints(true);
        profilAppli.setDataPointsRadius(10);
        profilAppli.setThickness(8);
        adapterEchelleDuGraphqiue();
        graphique.addSeries(profilAppli);
        graphique.addSeries(pointDeFonctionnement);
        if(pointSelectionne!=null){
            graphique.addSeries(pointSelectionne);
        }
    }

    public void initialiserGraphique(){
        graphique.getViewport().setYAxisBoundsManual(true);
        graphique.getViewport().setMinY(0);
        graphique.getViewport().setMaxY(modeReglage.getCourantEnEntree().getValMaxJauge());
        graphique.getViewport().setXAxisBoundsManual(true);
        graphique.getViewport().setMinX(0);
        graphique.getViewport().setMaxX(modeReglage.getVitesseRotation().getValMaxJauge());
    }

    public void afficherPointDeFonctionnement(){
        PointsGraphSeries<DataPoint> point = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(modeReglage.getVitesseRotation().getValCourante(), modeReglage.getCourantEnEntree().getValCourante())
        });
        modeReglage.setAbscisseDuPointDeFonctionnement(point.getHighestValueX());
        modeReglage.setOrdonneeDuPointDeFonctionnement(point.getHighestValueY());
        pointDeFonctionnement = point;
        pointDeFonctionnement.setColor(Color.BLACK);
        pointDeFonctionnement.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(10);
                canvas.drawLine(x-20, y-20, x+20, y+20, paint);
                canvas.drawLine(x+20, y-20, x-20, y+20, paint);
            }
        });
        graphique.removeAllSeries();
        adapterEchelleDuGraphqiue();
        graphique.addSeries(profilAppli);
        graphique.addSeries(pointDeFonctionnement);
        if(pointSelectionne!=null){
            graphique.addSeries(pointSelectionne);
        }
    }

    public void adapterEchelleDuGraphqiue(){
        graphique.getViewport().setXAxisBoundsManual(true);
        graphique.getViewport().setMinX(0);
        graphique.getViewport().setMaxX(modeReglage.getMaxAbscisseDuGraphique());
        graphique.getViewport().setYAxisBoundsManual(true);
        graphique.getViewport().setMinY(0);
        graphique.getViewport().setMaxY(modeReglage.getMaxOrdonneeDuGraphique());

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
