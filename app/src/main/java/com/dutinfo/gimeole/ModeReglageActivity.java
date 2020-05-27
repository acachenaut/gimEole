package com.dutinfo.gimeole;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.dutinfo.gimeole.ClassesUtiles.ModeReglage;
import com.dutinfo.gimeole.ClassesUtiles.Point;
import com.dutinfo.gimeole.ClassesUtiles.PolynomialRegression;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.List;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.DeviceCallback;

import static com.dutinfo.gimeole.ClassesUtiles.BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresApresLaVrigule;
import static com.dutinfo.gimeole.ClassesUtiles.BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs;

public class ModeReglageActivity extends AppCompatActivity {

    //Création du modeProduction
    final ModeReglage modeReglage = new ModeReglage();

    //Nécessaire à la connexion Bluetooth
    private Bluetooth bluetooth;
    private BluetoothDevice device;

    //Création valeurs à afficher
    TextView vitesseRotation, tensionEnEntree, courantEnEntree, puissanceFournie, affichageUniteAmpere,affichageDixiemeAmpere,affichageAmpere, affichageFonctionGenere;
    //Création de l'indicateur Bluetooth et de du nom du périphérique connecté
    ImageView logoBluetooth;
    TextView nomPeripheriqueBluetooth;

    //Création des bouttons de l'activité
    Button boutonModeProduction,boutonPointPrecedent, boutonPointSuivant,boutonSupprimerPoint, boutonTransfererProfil,boutonFreiner;
    Button boutonReglageJauges;
    Button boutonMoins1Ampere,boutonPlus1Ampere,boutonMoins1DixiemeAmpere,boutonPlus1DixiemeAmpere,boutonAjouterPoint,boutonModeSuivant,boutonModifierPoint,boutonModePrecedent, boutonGenererProfilAppli, boutonGenererEquation;

    //Création du graphique
    GraphView graphique;

    EditText abscisseSaisie,ordonneeSaisie;

    //Série de points affichés actuellement
    LineGraphSeries<DataPoint> profilAppli = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> profilConv = new LineGraphSeries<>();
    PointsGraphSeries<DataPoint> pointDeFonctionnement = null;
    PointsGraphSeries<DataPoint> pointSelectionne = new PointsGraphSeries<>();
    LineGraphSeries<DataPoint> regressionPolynomial = null;


    //indicateur permettant de savoir si le bluetooth a été deconnecté à cause d'un changement d'activité
    boolean estDeconnecteDuBluetoothCarChangementDActivite;


    enum choixGenererProfilAppli {aPartirDUnPoint,aPartinDeLEnsembleDesPoints,aPartirDuPorfilConv,aPartirDUnFichierCSV}
    choixGenererProfilAppli choixUtilisateurGenererProfilAppli;
    enum choixTransfererProfil {envoyerProfilAppli,recevoirProfilConv}
    choixTransfererProfil choixUtilisateurTransfererProfil;
    int degreDuPolynome;
    ArrayList<Double> coefficientsDuPolynome = new ArrayList<>();
    ArrayList<Double> coefficientsDuPolynomeDansLOrdre = new ArrayList<>();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_reglage);

        //Récupération des réglages du modeProduction
        modeReglage.setMinMaxDesJauges(getIntent().getDoubleArrayExtra("tabMinMax"));
        modeReglage.setCourantDeFreinage(getIntent().getDoubleExtra("courantDeFreinage",0));

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
        affichageFonctionGenere = findViewById(R.id.t_affichageFonctionGenere);


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
        boutonTransfererProfil = findViewById(R.id.t_boutonTransfererProfil);
        boutonGenererProfilAppli = findViewById(R.id.t_boutonGenererProfilAppli);
        boutonGenererEquation = findViewById(R.id.t_boutonGenererEquation);
        boutonReglageJauges = findViewById(R.id.t_boutonReglageActiviteReglage);
        boutonFreiner = findViewById(R.id.t_boutonFreiner);

        //Lien entre le graphique de l'interface et l'activité
        graphique = findViewById(R.id.t_graphique);

        //Lien entre les zones de saisis de l'interface et l'activité
        abscisseSaisie = findViewById(R.id.t_saisieAbscisse);
        ordonneeSaisie = findViewById(R.id.t_saiseOrdonnee);

        initialiserGraphique();
        afficherChangementReglageManuelCourantEnEntree();

        boutonAjouterPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (pointDeFonctionnement!=null){
                    boolean estAjoute;
                    estAjoute= modeReglage.ajouterUnPointAuProfilAppliEtTrierArrayList(pointDeFonctionnement.getHighestValueX(), pointDeFonctionnement.getHighestValueY());
                    if(estAjoute){
                        afficherLesPointsSurLeGraphique();
                    }
                    else{
                        Toast messageTableauComplet = Toast.makeText(getApplicationContext(),
                                "Vous avez déjà saisi vos 11 points",
                                Toast.LENGTH_SHORT);
                        messageTableauComplet.show();
                    }
                }
            }
        });

        boutonModeProduction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent modeProductionActivity = new Intent();
                modeProductionActivity.putExtra("device", device);
                modeProductionActivity.putExtra("tabMinMax",modeReglage.getMinMaxDesJauges());
                modeProductionActivity.putExtra("courantDeFreinage",modeReglage.getCourantDeFreinage());
                setResult(2,modeProductionActivity);
                finish();
            }
        });

        boutonReglageJauges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent reglageJaugesActivity = new Intent(ModeReglageActivity.this, ReglageJaugeActivity.class);
                reglageJaugesActivity.putExtra("tabMinMax", modeReglage.getMinMaxDesJauges());
                reglageJaugesActivity.putExtra("courantDeFreinage", modeReglage.getCourantDeFreinage());
                startActivityForResult(reglageJaugesActivity, 1);
            }
        });


        boutonPointPrecedent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(modeReglage.getNombreDePointsDuProfilAppli()!=1){
                    switch (modeReglage.getPointSelectionne()){
                        case 0:
                            modeReglage.setPointSelectionne(1);
                            abscisseSaisie.setText(String.valueOf(modeReglage.getPointsDuProfilAppli().get(modeReglage.getPointSelectionne()).getAbscisse()));
                            ordonneeSaisie.setText(String.valueOf(modeReglage.getPointsDuProfilAppli().get(modeReglage.getPointSelectionne()).getOrdonnee()));
                            break;
                        case 1:
                            Toast messagePasDePointPrecedent = Toast.makeText(getApplicationContext(),
                                    "C'est déjà le premier point !",
                                    Toast.LENGTH_SHORT);
                            messagePasDePointPrecedent.show();
                            break;
                        default:
                            modeReglage.setPointSelectionne(modeReglage.getPointSelectionne()-1);
                            abscisseSaisie.setText(String.valueOf(modeReglage.getPointsDuProfilAppli().get(modeReglage.getPointSelectionne()).getAbscisse()));
                            ordonneeSaisie.setText(String.valueOf(modeReglage.getPointsDuProfilAppli().get(modeReglage.getPointSelectionne()).getOrdonnee()));
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
                if(modeReglage.getNombreDePointsDuProfilAppli()!=1){
                    if(modeReglage.getPointSelectionne()== modeReglage.getNombreDePointsDuProfilAppli()-1){
                        Toast messagePasDePointSuivant = Toast.makeText(getApplicationContext(),
                                "C'est déjà le dernier point !",
                                Toast.LENGTH_SHORT);
                        messagePasDePointSuivant.show();
                    }
                    else{
                        modeReglage.setPointSelectionne(modeReglage.getPointSelectionne()+1);
                        abscisseSaisie.setText(String.valueOf(modeReglage.getPointsDuProfilAppli().get(modeReglage.getPointSelectionne()).getAbscisse()));
                        ordonneeSaisie.setText(String.valueOf(modeReglage.getPointsDuProfilAppli().get(modeReglage.getPointSelectionne()).getOrdonnee()));
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

        boutonModifierPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String abscisse = abscisseSaisie.getText().toString();
                String ordonnee = ordonneeSaisie.getText().toString();
                if (modeReglage.getPointSelectionne()!=0){
                    if (!(abscisse.matches("") || ordonnee.matches("")))
                    {
                        modeReglage.modifierPointDuProfilAppli(Double.parseDouble(abscisse),Double.parseDouble(ordonnee),modeReglage.getPointSelectionne());
                        afficherPointSelectionne(modeReglage.getPointSelectionne());
                    }
                    else
                    {
                        Toast messageZoneDeSaisieVide = Toast.makeText(getApplicationContext(),
                                "Une ou plusieurs zones de saisies sont vide !",
                                Toast.LENGTH_SHORT);

                        messageZoneDeSaisieVide.show();
                    }
                }
                else {
                    Toast messagePasDePointSelectionne = Toast.makeText(getApplicationContext(),
                            "Aucun point n'a été selectionné !",
                            Toast.LENGTH_SHORT);
                    messagePasDePointSelectionne.show();
                }

            }

        });

        boutonSupprimerPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(modeReglage.getPointSelectionne()!=0){
                    modeReglage.supprimerPointSelectionne(modeReglage.getPointSelectionne());
                    pointSelectionne=null;
                    abscisseSaisie.setText(null);
                    ordonneeSaisie.setText(null);
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

        boutonMoins1Ampere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(modeReglage.getCourantEnEntreeReglageManuelUnite()>0){
                    modeReglage.setCourantEnEntreeReglageManuelUnite(modeReglage.getCourantEnEntreeReglageManuelUnite()-1);
                    afficherChangementReglageManuelCourantEnEntree();
                    envoyerCourantEnEntreeALEolienne();
                }
                else{
                    Toast messagePasEnDessousDeZero = Toast.makeText(getApplicationContext(),
                            "Vous ne pouvez plus diminuer les unités !",
                            Toast.LENGTH_SHORT);
                    messagePasEnDessousDeZero.show();
                }

            }
        });

        boutonPlus1Ampere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(modeReglage.getCourantEnEntreeReglageManuelUnite()<modeReglage.getCourantEnEntree().getValMaxJauge()){
                    modeReglage.setCourantEnEntreeReglageManuelUnite(modeReglage.getCourantEnEntreeReglageManuelUnite()+1);
                    afficherChangementReglageManuelCourantEnEntree();
                    envoyerCourantEnEntreeALEolienne();
                }
                else{
                    Toast messagePasEnDessousDeZero = Toast.makeText(getApplicationContext(),
                            "Vous ne pouvez plus augmenter les unités !",
                            Toast.LENGTH_SHORT);
                    messagePasEnDessousDeZero.show();
                }

            }
        });

        boutonMoins1DixiemeAmpere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(modeReglage.getCourantEnEntreeReglageManuelDixieme()>0){
                    modeReglage.setCourantEnEntreeReglageManuelDixieme(modeReglage.getCourantEnEntreeReglageManuelDixieme()-1);
                    afficherChangementReglageManuelCourantEnEntree();
                    envoyerCourantEnEntreeALEolienne();
                }
                else{
                    Toast messagePasEnDessousDeZero = Toast.makeText(getApplicationContext(),
                            "Vous ne pouvez plus diminuer les dixièmes !",
                            Toast.LENGTH_SHORT);
                    messagePasEnDessousDeZero.show();
                }

            }
        });

        boutonPlus1DixiemeAmpere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(modeReglage.getCourantEnEntreeReglageManuelDixieme()<9){
                    modeReglage.setCourantEnEntreeReglageManuelDixieme(modeReglage.getCourantEnEntreeReglageManuelDixieme()+1);
                    afficherChangementReglageManuelCourantEnEntree();
                    envoyerCourantEnEntreeALEolienne();
                }
                else{
                    Toast messagePasEnDessousDeZero = Toast.makeText(getApplicationContext(),
                            "Vous ne pouvez plus augmenter les dixièmes !",
                            Toast.LENGTH_SHORT);
                    messagePasEnDessousDeZero.show();
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


        boutonFreiner.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                // Code here executes on main thread when user pressed button
                if (modeReglage.getVitesseRotation().getValCourante() == 0) {
                    Toast messageEoliennePasEnMarche = Toast.makeText(getApplicationContext(),
                            "L'éolienne n'est pas activée, vous ne pouvez donc pas activer le freinage !",
                            Toast.LENGTH_SHORT);
                    messageEoliennePasEnMarche.show();
                }
                else{
                    bluetooth.send("*"+String.valueOf(modeReglage.getCourantDeFreinage()+1));
                    try {
                        wait(1000);
                    }
                    catch(Exception erreur){
                    }
                    bluetooth.send("*"+String.valueOf(modeReglage.getCourantDeFreinage()-1));
                    try {
                        wait(1000);
                    }
                    catch(Exception erreur){
                    }
                }
                return false;
            }
        });

        boutonGenererProfilAppli.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                AlertDialog.Builder builder = new AlertDialog.Builder(ModeReglageActivity.this);
                builder.setTitle(R.string.genererProfilAppli);
                builder.setSingleChoiceItems(R.array.genererProfilAppliAlertDialog,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choixUtilisateurGenererProfilAppli = choixGenererProfilAppli.aPartirDUnPoint;
                                break;
                            case 1:
                                choixUtilisateurGenererProfilAppli = choixGenererProfilAppli.aPartinDeLEnsembleDesPoints;
                                break;
                            case 2:
                                choixUtilisateurGenererProfilAppli = choixGenererProfilAppli.aPartirDuPorfilConv;
                                break;
                            case 3:
                                choixUtilisateurGenererProfilAppli = choixGenererProfilAppli.aPartirDUnFichierCSV;
                                break;
                        }
                    }
                });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        switch (choixUtilisateurGenererProfilAppli){
                            case aPartirDUnPoint:
                                if(modeReglage.getPointSelectionne()!=0){
                                    double coefficient;
                                    coefficient = modeReglage.getPointsDuProfilAppli().get(modeReglage.getPointSelectionne()).getOrdonnee()/Math.pow(modeReglage.getPointsDuProfilAppli().get(modeReglage.getPointSelectionne()).getAbscisse(),2);
                                    modeReglage.supprimerProfilAppli();

                                    double abscisseCourante = modeReglage.getVitesseRotation().getValMaxJauge()/10;
                                    while(abscisseCourante<=modeReglage.getVitesseRotation().getValMaxJauge()){
                                        modeReglage.ajouterUnPointAuProfilAppliEtTrierArrayList(abscisseCourante,(coefficient*Math.pow(abscisseCourante,2)));
                                        abscisseCourante += modeReglage.getVitesseRotation().getValMaxJauge()/10;
                                    }
                                    pointSelectionne=null;
                                    abscisseSaisie.setText(null);
                                    ordonneeSaisie.setText(null);
                                    modeReglage.setPointSelectionne(0);
                                    afficherLesPointsSurLeGraphique();
                                }
                                else{
                                    Toast messagePasDePointSelectionne = Toast.makeText(getApplicationContext(),
                                            "Aucun point n'a été selectionné !",
                                            Toast.LENGTH_SHORT);
                                    messagePasDePointSelectionne.show();
                                }
                                break;
                            case aPartinDeLEnsembleDesPoints:
                                if (regressionPolynomial!=null){
                                    modeReglage.supprimerProfilAppli();
                                    double abscisseCourante = modeReglage.getVitesseRotation().getValMaxJauge()/10;
                                    while(abscisseCourante<=modeReglage.getVitesseRotation().getValMaxJauge()){
                                        double ordonnee = 0;
                                        int degre = 0;
                                        for(Double coefficient : coefficientsDuPolynome) {
                                            ordonnee += coefficient * Math.pow(abscisseCourante, degre);
                                            degre++;
                                        }
                                        modeReglage.ajouterUnPointAuProfilAppliEtTrierArrayList(abscisseCourante,ordonnee);
                                        abscisseCourante += modeReglage.getVitesseRotation().getValMaxJauge()/10;
                                    }
                                    pointSelectionne=null;
                                    abscisseSaisie.setText(null);
                                    ordonneeSaisie.setText(null);
                                    regressionPolynomial=null;
                                    affichageFonctionGenere.setText(null);
                                    modeReglage.reinitialiserMaxAbscisseEtOrdonneeDeLEquationGenere();
                                    modeReglage.setPointSelectionne(0);
                                    afficherLesPointsSurLeGraphique();
                                }
                                else {
                                    Toast messagePasDEquationGenere = Toast.makeText(getApplicationContext(),
                                            "Vous n'avez généré aucune équation !",
                                            Toast.LENGTH_SHORT);
                                    messagePasDEquationGenere.show();
                                }
                                break;
                            case aPartirDuPorfilConv:
                                if (modeReglage.getPointsDuProfilConv().size()>1){
                                    modeReglage.supprimerProfilAppli();
                                    modeReglage.setPointsDuProfilAppli(modeReglage.getPointsDuProfilConv());
                                }
                                else {
                                    Toast aucunProfilConvEnregistre = Toast.makeText(getApplicationContext(),
                                            "Aucun profilConv n'a été enregistré !",
                                            Toast.LENGTH_SHORT);
                                    aucunProfilConvEnregistre.show();
                                }

                                break;
                            case aPartirDUnFichierCSV:
                                break;
                            default:
                                Toast aucuneChoixeffectue = Toast.makeText(getApplicationContext(),
                                        "Vous n'avez rien sélectionné !",
                                        Toast.LENGTH_SHORT);
                                aucuneChoixeffectue.show();
                                break;
                        }
                    }
                });
                builder.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog genererProfilAppliAlertDialog = builder.create();
                genererProfilAppliAlertDialog.show();

            }
        });

        boutonGenererEquation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                NumberPicker choixDuDegreDuPolynome = new NumberPicker(ModeReglageActivity.this);
                choixDuDegreDuPolynome.setMinValue(2);
                choixDuDegreDuPolynome.setMaxValue(4);
                FrameLayout layout = new FrameLayout(ModeReglageActivity.this);
                layout.addView(choixDuDegreDuPolynome, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
                AlertDialog.Builder builder = new AlertDialog.Builder(ModeReglageActivity.this);
                builder.setTitle(R.string.genererEquation);
                builder.setView(layout);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        coefficientsDuPolynome.clear();
                        coefficientsDuPolynomeDansLOrdre.clear();
                        degreDuPolynome=choixDuDegreDuPolynome.getValue();

                        List<Utils.Point> points = new ArrayList<>();
                        for(Point pointCourant : modeReglage.getPointsDuProfilAppli()){
                            points.add(new Utils.Point(pointCourant.getAbscisse(),pointCourant.getOrdonnee()));
                        }
                        PolynomialRegression polynomialRegression = new PolynomialRegression(points,degreDuPolynome);
                        for (int i = 0; i < polynomialRegression.getCoefficients().length; i++) {
                            for (int j = 0; j < polynomialRegression.getCoefficients()[i].length; j++) {
                                coefficientsDuPolynome.add(polynomialRegression.getCoefficients()[i][j]);
                            }
                        }


                        int abscisseCourante = 1;
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                        while(abscisseCourante<=modeReglage.getVitesseRotation().getValMaxJauge()){
                            double ordonneeCalculee = 0;
                            int degre = 0;
                            for(Double coefficient : coefficientsDuPolynome) {
                                ordonneeCalculee += coefficient * Math.pow(abscisseCourante, degre);
                                degre++;
                            }
                            series.appendData(new DataPoint(abscisseCourante,ordonneeCalculee),true,10000);
                            modeReglage.modifierMaxAbscisseEtOrdonneeDeLEquationGenereEnFonctionDuNouveauPoint(abscisseCourante,ordonneeCalculee);
                            abscisseCourante ++;
                        }
                        regressionPolynomial=series;
                        regressionPolynomial.setDrawDataPoints(false);
                        regressionPolynomial.setThickness(5);
                        regressionPolynomial.setColor(Color.GREEN);
                        afficherLesPointsSurLeGraphique();

                        for (Double coefficient : coefficientsDuPolynome){
                            coefficientsDuPolynomeDansLOrdre.add(coefficient);
                        }
                        String fonctionGenere = "f(x)=";
                        int degre = coefficientsDuPolynomeDansLOrdre.size()-1;
                        for (Double coefficient : coefficientsDuPolynomeDansLOrdre){
                            if(coefficient>0 || coefficient==0){
                                if (coefficientsDuPolynomeDansLOrdre.size()-1!=degre){
                                    fonctionGenere+="+";
                                }
                            }
                            fonctionGenere+=arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,coefficient);
                            if (degre!=0){
                                fonctionGenere+="*X^"+degre;
                            }
                            degre--;
                        }
                        affichageFonctionGenere.setText(fonctionGenere);


                    }
                });
                builder.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog genererEquationAlertDialog = builder.create();
                genererEquationAlertDialog.show();

            }
        });

        boutonTransfererProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                AlertDialog.Builder builder = new AlertDialog.Builder(ModeReglageActivity.this);
                builder.setTitle(R.string.transfererProfil);
                builder.setSingleChoiceItems(R.array.transfererProfilAlertDialog,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choixUtilisateurTransfererProfil = choixTransfererProfil.envoyerProfilAppli;
                                break;
                            case 1:
                                choixUtilisateurTransfererProfil = choixTransfererProfil.recevoirProfilConv;
                                break;
                        }
                    }
                });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        if(modeReglage.getVitesseRotation().getValCourante()==0) {
                            switch (choixUtilisateurTransfererProfil) {
                                case envoyerProfilAppli:
                                    if (modeReglage.getPointsDuProfilAppli().size()==11){
                                        int i = 1;
                                        while (i<=modeReglage.getPointsDuProfilAppli().size()-1){
                                            bluetooth.send("N"+i+Math.round(modeReglage.getPointsDuProfilAppli().get(i).getAbscisse())+";Ie"+i+arrondirChiffreEnFonctionDuNombreDeChiffresApresLaVrigule(1,modeReglage.getPointsDuProfilAppli().get(i).getOrdonnee()));
                                            i++;
                                        }
                                    }
                                    else {
                                        Toast profilAppliIncomplet = Toast.makeText(getApplicationContext(),
                                                "Le profilAppli ne contient pas les 10 points, veuillez générer un profil complet !",
                                                Toast.LENGTH_SHORT);
                                        profilAppliIncomplet.show();
                                    }
                                    break;
                                case recevoirProfilConv:
                                    bluetooth.send("**");
                                    break;
                                default:
                                    Toast aucuneChoixeffectue = Toast.makeText(getApplicationContext(),
                                            "Vous n'avez rien sélectionné !",
                                            Toast.LENGTH_SHORT);
                                    aucuneChoixeffectue.show();
                                    break;
                            }
                        }
                        else {
                            Toast eolienneEnFonctionnement = Toast.makeText(getApplicationContext(),
                                    "Vous ne pouvez pas effectuer de transfert tant que l'éolienne est en fonctionnement",
                                    Toast.LENGTH_SHORT);
                            eolienneEnFonctionnement.show();
                        }
                    }
                });
                builder.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog genererProfilAppliAlertDialog = builder.create();
                genererProfilAppliAlertDialog.show();

            }
        });


    }

    @Override
    protected void onPause(){
        super.onPause();
        if(!estDeconnecteDuBluetoothCarChangementDActivite){
            bluetooth.onStop();
            bluetooth.disconnect();
        }
        estDeconnecteDuBluetoothCarChangementDActivite=true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(estDeconnecteDuBluetoothCarChangementDActivite){
            bluetooth.onStart();
            bluetooth.connectToDevice(device);
        }
        estDeconnecteDuBluetoothCarChangementDActivite=false;
    }

    private void afficherChangementReglageManuelCourantEnEntree() {
        affichageUniteAmpere.setText(String.valueOf(modeReglage.getCourantEnEntreeReglageManuelUnite()));
        affichageDixiemeAmpere.setText(String.valueOf("0."+modeReglage.getCourantEnEntreeReglageManuelDixieme()));
        affichageAmpere.setText(modeReglage.getCourantEnEntreeReglageManuelUnite()+"."+modeReglage.getCourantEnEntreeReglageManuelDixieme());
    }

    public void envoyerCourantEnEntreeALEolienne(){
        bluetooth.send("*"+modeReglage.getCourantEnEntreeReglageManuelUnite()+"."+modeReglage.getCourantEnEntreeReglageManuelDixieme());
    }

    public void afficherValeur (String chaineRecuParBluetooth){
        String premierCaractere = (chaineRecuParBluetooth.substring(0,1));
        String valeurCourante = (chaineRecuParBluetooth.substring(1));
        switch (premierCaractere){
            case "$" :
                modeReglage.getVitesseRotation().setValCourante(Double.parseDouble(valeurCourante));
                vitesseRotation.setText(arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(4,modeReglage.getVitesseRotation().getValCourante()));
                afficherPointDeFonctionnement();
                break;
            case ":" :
                modeReglage.getTensionEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                tensionEnEntree.setText(arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeReglage.getTensionEnEntree().getValCourante()));
                afficherPointDeFonctionnement();
                break;
            case ";" :
                modeReglage.getCourantEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                courantEnEntree.setText(arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeReglage.getCourantEnEntree().getValCourante()));
                break;
            case "[" :
                modeReglage.getTensionEnSortie().setValCourante(Double.parseDouble(valeurCourante));
                break;
            case "]" :
                modeReglage.getCourantEnSortie().setValCourante(Double.parseDouble(valeurCourante));
                break;
            case "%" :
                modeReglage.getPuissanceFournie().setValCourante(Double.parseDouble(valeurCourante));
                puissanceFournie.setText(arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeReglage.getPuissanceFournie().getValCourante()));
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
            case "N":
                enregistrerLeProfilConv(valeurCourante);
                break;
            default:
                break;
        }
    }

    public void enregistrerLeProfilConv(String points){
        String indice = points.substring(0,1);
        String abscisse = points.substring(1,points.indexOf(";"));
        String ordonnee = points.substring(points.indexOf(";")+1);
        if (indice == "1"){
            String indiceVerif = points.substring(0,2);
            if(indiceVerif=="10") {
                indice = indiceVerif;
                abscisse = points.substring(2, points.indexOf(";"));
            }
            else {
                modeReglage.supprimerProfilConv();
            }
        }
        modeReglage.ajouterUnPointAuProfilConvEtTrierArrayList(Double.valueOf(abscisse),Double.valueOf(ordonnee), Integer.valueOf(indice));
        afficherLesPointsSurLeGraphique();
    }

    public void afficherPointSelectionne(int positionDuPoint){
        PointsGraphSeries<DataPoint> point = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(modeReglage.getPointsDuProfilAppli().get(positionDuPoint).getAbscisse(), modeReglage.getPointsDuProfilAppli().get(positionDuPoint).getOrdonnee())
    });
        pointSelectionne = point;
        pointSelectionne.setShape(PointsGraphSeries.Shape.POINT);
        pointSelectionne.setColor(Color.YELLOW);
        pointSelectionne.setShape(PointsGraphSeries.Shape.POINT);
        afficherLesPointsSurLeGraphique();
    }

    public void afficherLesPointsSurLeGraphique(){
        LineGraphSeries<DataPoint> seriesProfilAppli = new LineGraphSeries<>();
        for(Point pointCourant : modeReglage.getPointsDuProfilAppli()){
            seriesProfilAppli.appendData(new DataPoint(pointCourant.getAbscisse(),pointCourant.getOrdonnee()),true,11);
        }
        LineGraphSeries<DataPoint> seriesProfilConv = new LineGraphSeries<>();
        for(Point pointCourant : modeReglage.getPointsDuProfilConv()){
            seriesProfilConv.appendData(new DataPoint(pointCourant.getAbscisse(),pointCourant.getOrdonnee()),true,11);
        }

        profilAppli = seriesProfilAppli;
        profilAppli.setDrawDataPoints(true);
        profilAppli.setDataPointsRadius(10);
        profilAppli.setThickness(8);

        profilConv = seriesProfilConv;
        profilConv.setColor(Color.RED);
        profilConv.setDrawDataPoints(true);
        profilConv.setDataPointsRadius(10);
        profilConv.setThickness(8);

        graphique.removeAllSeries();
        adapterEchelleDuGraphqiue();
        graphique.addSeries(profilConv);
        graphique.addSeries(profilAppli);

        if(pointSelectionne!=null){
            graphique.addSeries(pointSelectionne);
        }
        if(regressionPolynomial!=null){
            graphique.addSeries(regressionPolynomial);
        }
        if(pointDeFonctionnement!=null){
            graphique.addSeries(pointDeFonctionnement);
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
        afficherLesPointsSurLeGraphique();
    }

    public void adapterEchelleDuGraphqiue(){
        graphique.getViewport().setXAxisBoundsManual(true);
        graphique.getViewport().setMinX(0);
        graphique.getViewport().setMaxX(modeReglage.getMaxAbscisseDuGraphique());
        graphique.getViewport().setYAxisBoundsManual(true);
        graphique.getViewport().setMinY(0);
        graphique.getViewport().setMaxY(modeReglage.getMaxOrdonneeDuGraphique());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            modeReglage.setMinMaxDesJauges(data.getDoubleArrayExtra("tabMinMax"));
            modeReglage.setCourantDeFreinage(data.getDoubleExtra("courantDeFreinage",0));
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
        estDeconnecteDuBluetoothCarChangementDActivite = true;
        bluetooth.onStop();
        bluetooth.disconnect();

    }

}
