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

import static com.dutinfo.gimeole.ClassesUtiles.BoiteAOutils.arrondirNombreEnFonctionDuNombreDeChiffresApresLaVrigule;
import static com.dutinfo.gimeole.ClassesUtiles.BoiteAOutils.arrondirNombreEnFonctionDuNombreDeChiffresSignificatifs;
import static com.dutinfo.gimeole.ClassesUtiles.BoiteAOutils.obtenirEcritureScientifiqueEnFonctionDuNombreDeChiffresApresLaVirgule;

/**
 * Seconde activité principale de l'application. Elle correspond au mode réglage.
 */
public class ModeReglageActivity extends AppCompatActivity {

    /**
     * The Mode reglage.
     */
//Création du modeProduction
    final ModeReglage modeReglage = new ModeReglage();

    //Nécessaire à la connexion Bluetooth
    private Bluetooth bluetooth;
    private BluetoothDevice device;

    /**
     * The Vitesse rotation.
     */
//Création valeurs à afficher
    TextView vitesseRotation, /**
     * The Tension en entree.
     */
    tensionEnEntree, /**
     * The Courant en entree.
     */
    courantEnEntree, /**
     * The Puissance fournie.
     */
    puissanceFournie, /**
     * The Affichage unite ampere.
     */
    affichageUniteAmpere, /**
     * The Affichage dixieme ampere.
     */
    affichageDixiemeAmpere, /**
     * The Affichage ampere.
     */
    affichageAmpere;
    /**
     * The Logo bluetooth.
     */
//Création de l'indicateur Bluetooth et de du nom du périphérique connecté
    ImageView logoBluetooth;
    /**
     * The Nom peripherique bluetooth.
     */
    TextView nomPeripheriqueBluetooth;

    /**
     * The Bouton mode production.
     */
//Création des bouttons de l'activité
    Button boutonModeProduction, /**
     * The Bouton point precedent.
     */
    boutonPointPrecedent, /**
     * The Bouton point suivant.
     */
    boutonPointSuivant, /**
     * The Bouton supprimer point.
     */
    boutonSupprimerPoint, /**
     * The Bouton transferer profil.
     */
    boutonTransfererProfil, /**
     * The Bouton freiner.
     */
    boutonFreiner;
    /**
     * The Bouton reglage jauges.
     */
    Button boutonReglageJauges;
    /**
     * The Bouton moins 1 ampere.
     */
    Button boutonMoins1Ampere, /**
     * The Bouton plus 1 ampere.
     */
    boutonPlus1Ampere, /**
     * The Bouton moins 1 dixieme ampere.
     */
    boutonMoins1DixiemeAmpere, /**
     * The Bouton plus 1 dixieme ampere.
     */
    boutonPlus1DixiemeAmpere, /**
     * The Bouton ajouter point.
     */
    boutonAjouterPoint, /**
     * The Bouton mode suivant.
     */
    boutonModeSuivant, /**
     * The Bouton modifier point.
     */
    boutonModifierPoint, /**
     * The Bouton mode precedent.
     */
    boutonModePrecedent, /**
     * The Bouton generer profil appli.
     */
    boutonGenererProfilAppli, /**
     * The Bouton generer equation.
     */
    boutonGenererEquation, /**
     * The Bouton affichage fonction genere.
     */
    boutonAffichageFonctionGenere;

    /**
     * The Graphique.
     */
//Création du graphique
    GraphView graphique;

    /**
     * The Abscisse saisie.
     */
    EditText abscisseSaisie, /**
     * The Ordonnee saisie.
     */
    ordonneeSaisie;

    /**
     * Série contenant les points du profilAppli affichés sur le graphique
     */
    LineGraphSeries<DataPoint> profilAppli = new LineGraphSeries<>();
    /**
     * Série contenant les points du profilConv affichés sur le graphique
     */
    LineGraphSeries<DataPoint> profilConv = new LineGraphSeries<>();
    /**
     * Série de points contenant le point de fonctionnement affiché sur le graphique
     */
    PointsGraphSeries<DataPoint> pointDeFonctionnement = null;
    /**
     * Série de points contenant le point selectionné par l'utilisateur affiché sur le graphique
     */
    PointsGraphSeries<DataPoint> pointSelectionne = new PointsGraphSeries<>();
    /**
     * Série contenant les points de la regressionPolynomial affichés sur le graphique
     */
    LineGraphSeries<DataPoint> regressionPolynomial = null;


    /**
     * The Est deconnecte du bluetooth car changement d activite.
     */
//indicateur permettant de savoir si le bluetooth a été deconnecté à cause d'un changement d'activité
    boolean estDeconnecteDuBluetoothCarChangementDActivite;


    /**
     * The enum Choix generer profil appli.
     */
    enum choixGenererProfilAppli {
        /**
         * A partir d un point choix generer profil appli.
         */
        aPartirDUnPoint,
        /**
         * A partin de l ensemble des points choix generer profil appli.
         */
        aPartinDeLEnsembleDesPoints,
        /**
         * A partir du porfil conv choix generer profil appli.
         */
        aPartirDuPorfilConv,
        /**
         * A partir d un fichier csv choix generer profil appli.
         */
        aPartirDUnFichierCSV}

    /**
     * The Choix utilisateur generer profil appli.
     */
    choixGenererProfilAppli choixUtilisateurGenererProfilAppli;

    /**
     * The enum Choix transferer profil.
     */
    enum choixTransfererProfil {
        /**
         * Envoyer profil appli choix transferer profil.
         */
        envoyerProfilAppli,
        /**
         * Recevoir profil conv choix transferer profil.
         */
        recevoirProfilConv}

    /**
     * The Choix utilisateur transferer profil.
     */
    choixTransfererProfil choixUtilisateurTransfererProfil;

    /**
     * The enum Choix adapter maximum ou annuler transfert du profil conv.
     */
    enum choixAdapterMaximumOuAnnulerTransfertDuProfilConv {
        /**
         * Adapter maximum choix adapter maximum ou annuler transfert du profil conv.
         */
        adapterMaximum,
        /**
         * Annuler transfert choix adapter maximum ou annuler transfert du profil conv.
         */
        annulerTransfert}

    /**
     * The Choix utilisateur adapter ou annuler.
     */
    choixAdapterMaximumOuAnnulerTransfertDuProfilConv choixUtilisateurAdapterOuAnnuler = choixAdapterMaximumOuAnnulerTransfertDuProfilConv.adapterMaximum;
    /**
     * The Degre du polynome.
     */
    int degreDuPolynome;
    /**
     * The Coefficients du polynome.
     */
    ArrayList<Double> coefficientsDuPolynome = new ArrayList<>();
    /**
     * The Coefficients du polynome dans l ordre.
     */
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
        boutonAffichageFonctionGenere = findViewById(R.id.t_boutonAffichageFonctionGenere);


        //Lien entre l'indicateur Bluetooth/nom du périphérique de l'interface et l'activité
        logoBluetooth = findViewById(R.id.logoBluetooth);
        nomPeripheriqueBluetooth = findViewById(R.id.nomPeripheriqueBluetooth);
        nomPeripheriqueBluetooth.setText(getResources().getString(R.string.connecteA));

        estDeconnecteDuBluetoothCarChangementDActivite=false;

        //Lien entre les boutons de l'interface et l'activité
        boutonModeProduction = findViewById(R.id.t_boutonModeProd);
        boutonPointPrecedent = findViewById(R.id.t_boutonPointPrecedent);
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
                    if (pointDeFonctionnement.getHighestValueX()<=modeReglage.getVitesseRotation().getValMaxJauge() && pointDeFonctionnement.getHighestValueY()<=modeReglage.getCourantEnEntree().getValMaxJauge()){
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
                    else {
                        Toast messagePointTropGrand = Toast.makeText(getApplicationContext(),
                                "Le point est en dehors des maximums de Vitesse ou de Courant !",
                                Toast.LENGTH_SHORT);
                        messagePointTropGrand.show();
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
                        if (Double.parseDouble(abscisse)<=modeReglage.getVitesseRotation().getValMaxJauge() && Double.parseDouble(ordonnee)<=modeReglage.getCourantEnEntree().getValMaxJauge()){
                            modeReglage.modifierPointDuProfilAppli(Double.parseDouble(abscisse),Double.parseDouble(ordonnee),modeReglage.getPointSelectionne());
                            afficherPointSelectionne(modeReglage.getPointSelectionne());
                        }
                        else {
                            Toast messagePointTropGrand = Toast.makeText(getApplicationContext(),
                                    "Le point est en dehors des maximums de Vitesse ou de Courant !",
                                    Toast.LENGTH_SHORT);
                            messagePointTropGrand.show();
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
                            default:
                                break;
                        }
                    }
                });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        if (choixUtilisateurGenererProfilAppli!=null){
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
                                        regressionPolynomial=null;
                                        boutonAffichageFonctionGenere.setText(null);
                                        modeReglage.reinitialiserMaxAbscisseEtOrdonneeDeLEquationGenere();
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
                                        regressionPolynomial=null;
                                        boutonAffichageFonctionGenere.setText(null);
                                        modeReglage.reinitialiserMaxAbscisseEtOrdonneeDeLEquationGenere();
                                        modeReglage.setPointSelectionne(0);
                                        afficherLesPointsSurLeGraphique();

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
                                    break;
                            }
                            choixUtilisateurGenererProfilAppli = null;
                        }
                        else{
                            Toast aucuneChoixeffectue = Toast.makeText(getApplicationContext(),
                                    "Vous n'avez rien sélectionné !",
                                    Toast.LENGTH_SHORT);
                            aucuneChoixeffectue.show();
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
                        if (modeReglage.getPointsDuProfilAppli().size()!=1){
                            coefficientsDuPolynome.clear();
                            modeReglage.reinitialiserMaxAbscisseEtOrdonneeDeLEquationGenere();
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
                                fonctionGenere+= arrondirNombreEnFonctionDuNombreDeChiffresSignificatifs(3,coefficient);
                                if (degre!=0){
                                    fonctionGenere+="*X^"+degre;
                                }
                                degre--;
                            }
                            boutonAffichageFonctionGenere.setPaintFlags(boutonAffichageFonctionGenere.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                            boutonAffichageFonctionGenere.setText(fonctionGenere);


                        }
                        else {
                            Toast messageAucunPointSaisi = Toast.makeText(getApplicationContext(),
                                    "Aucun point n'a été ajouté !",
                                    Toast.LENGTH_SHORT);
                            messageAucunPointSaisi.show();
                        }
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
                        if (choixUtilisateurTransfererProfil!=null){
                            if(modeReglage.getVitesseRotation().getValCourante()==0) {
                                switch (choixUtilisateurTransfererProfil) {
                                    case envoyerProfilAppli:
                                        if (modeReglage.getPointsDuProfilAppli().size()==11){
                                            int i = 1;
                                            while (i<=modeReglage.getPointsDuProfilAppli().size()-1){
                                                bluetooth.send("N"+i+Math.round(modeReglage.getPointsDuProfilAppli().get(i).getAbscisse())+";Ie"+i+ arrondirNombreEnFonctionDuNombreDeChiffresApresLaVrigule(1,modeReglage.getPointsDuProfilAppli().get(i).getOrdonnee()));
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
                        else {
                            Toast aucuneChoixeffectue = Toast.makeText(getApplicationContext(),
                                    "Vous n'avez rien sélectionné !",
                                    Toast.LENGTH_SHORT);
                            aucuneChoixeffectue.show();
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

        boutonAffichageFonctionGenere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (regressionPolynomial != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ModeReglageActivity.this);
                    builder.setTitle(R.string.coefficientsDuPolynome);
                    String affichageDesCoefficients = "";
                    for (int i = 0; i <= coefficientsDuPolynomeDansLOrdre.size() - 1; i++) {
                        affichageDesCoefficients += (char) (97 + i) + " : " + obtenirEcritureScientifiqueEnFonctionDuNombreDeChiffresApresLaVirgule(3, coefficientsDuPolynomeDansLOrdre.get(i)) + "\n";
                    }
                    builder.setMessage(affichageDesCoefficients);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });
                    AlertDialog afficherCoefficientsAlertDialog = builder.create();
                    afficherCoefficientsAlertDialog.show();
                }
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

    /**
     * Méthode qui permet de mettre à jour les TextView affichants les valeurs du réglage manuel du courant en entrée
     */
    private void afficherChangementReglageManuelCourantEnEntree() {
        affichageUniteAmpere.setText(String.valueOf(modeReglage.getCourantEnEntreeReglageManuelUnite()));
        affichageDixiemeAmpere.setText(String.valueOf("0."+modeReglage.getCourantEnEntreeReglageManuelDixieme()));
        affichageAmpere.setText(modeReglage.getCourantEnEntreeReglageManuelUnite()+"."+modeReglage.getCourantEnEntreeReglageManuelDixieme());
    }

    /**
     * Envoyer courant en entrée à l éolienne à partir de la valeur paramétrée dans le modeReglage
     */
    public void envoyerCourantEnEntreeALEolienne(){
        bluetooth.send("*"+modeReglage.getCourantEnEntreeReglageManuelUnite()+"."+modeReglage.getCourantEnEntreeReglageManuelDixieme());
    }

    /**
     * Cette méthode est appelé lorsque l'application reçoit une donnée envoyée par l'éolienne.
     * La donnée est traitée en fonction de son premier caractère. C'est cela qui permet de savoir à quelle valeur elle correspond.
     * Les valeurs sont affcihées dans des TextView pour seulement 4 éléments (vitesse de rotation, tention en entrée, courant en entrée et puissance fournie).
     * Permet également de traiter la réception du prfilConv
     *
     * @param chaineRecuParBluetooth donnée reçue par bluetooth
     */
    public void afficherValeur (String chaineRecuParBluetooth){
        String premierCaractere = (chaineRecuParBluetooth.substring(0,1));
        String valeurCourante = (chaineRecuParBluetooth.substring(1));
        switch (premierCaractere){
            case "$" :
                modeReglage.getVitesseRotation().setValCourante(Double.parseDouble(valeurCourante));
                vitesseRotation.setText(arrondirNombreEnFonctionDuNombreDeChiffresSignificatifs(4,modeReglage.getVitesseRotation().getValCourante()));
                afficherPointDeFonctionnement();
                break;
            case ":" :
                modeReglage.getTensionEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                tensionEnEntree.setText(arrondirNombreEnFonctionDuNombreDeChiffresSignificatifs(3,modeReglage.getTensionEnEntree().getValCourante()));
                afficherPointDeFonctionnement();
                break;
            case ";" :
                modeReglage.getCourantEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                courantEnEntree.setText(arrondirNombreEnFonctionDuNombreDeChiffresSignificatifs(3,modeReglage.getCourantEnEntree().getValCourante()));
                break;
            case "[" :
                modeReglage.getTensionEnSortie().setValCourante(Double.parseDouble(valeurCourante));
                break;
            case "]" :
                modeReglage.getCourantEnSortie().setValCourante(Double.parseDouble(valeurCourante));
                break;
            case "%" :
                modeReglage.getPuissanceFournie().setValCourante(Double.parseDouble(valeurCourante));
                puissanceFournie.setText(arrondirNombreEnFonctionDuNombreDeChiffresSignificatifs(3,modeReglage.getPuissanceFournie().getValCourante()));
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

    /**
     * Méthode qui permet d'enregistrer le profilConv dans le modeProduction. Chaque point est traité en fonction de son indice.
     * Si les coordonnées de l'un des points reçus est supérieur au maximum paramétré dans le modeReglage une fênetre pop-up apparît afin de demander si l'utilisateur veut modifier le maximum de l'élément qui pose problème.
     *
     * @param points the points
     */
    public void enregistrerLeProfilConv(String points){
        String indice = points.substring(1,2);
        String abscisse;
        String ordonnee = points.substring(points.indexOf(";")+3);
        if (indice.equals("1")){
            String indiceVerif = points.substring(1,3);
            if(indiceVerif.equals("10")) {
                abscisse = points.substring(3, points.indexOf(";"));
            }
            else {
                modeReglage.supprimerProfilConv();
                abscisse=points.substring(2,points.indexOf(";"));
            }
        }
        else{
            abscisse=points.substring(2,points.indexOf(";"));
        }

        if (ordonnee.substring(0,1).equals("1")){
            String indiceVerif = ordonnee.substring(0,2);
            if(indiceVerif.equals("10")) {
                indiceVerif = ordonnee.substring(0,3);
                if (indiceVerif.equals("100")){
                    ordonnee = ordonnee.substring(2);
                }
                else if (indiceVerif.equals("10.")){
                    ordonnee = ordonnee.substring(1);
                }
                else{
                    ordonnee = ordonnee.substring(2);
                }
            }
            else {
                ordonnee = ordonnee.substring(1);
            }
        }
        else{
            ordonnee = ordonnee.substring(1);
        }

        boolean estProblemeVitesse = false, estProblemeCourant = false;
        String finalAbscisse = abscisse;
        String finalOrdonnee = ordonnee;
        double nouveauReglageMaxVitesse;
        double nouveauReglageMaxCourant;

        if ((Double.valueOf(abscisse)>modeReglage.getVitesseRotation().getValMaxJauge() || Double.valueOf(ordonnee)>modeReglage.getCourantEnEntree().getValMaxJauge()) && choixUtilisateurAdapterOuAnnuler==choixAdapterMaximumOuAnnulerTransfertDuProfilConv.adapterMaximum){
            if((Double.valueOf(abscisse)>modeReglage.getVitesseRotation().getValMaxJauge())){
                AlertDialog.Builder builder = new AlertDialog.Builder(ModeReglageActivity.this);
                String [] choixAdapterOuAnnuler = new String[2];
                choixAdapterOuAnnuler[1]="Annuler le transfert du profilConv";
                builder.setTitle("Le réglage max de la vitesse de rotation est trop bas pour le transfert");
                nouveauReglageMaxVitesse = 500;
                while(nouveauReglageMaxVitesse<Double.valueOf(abscisse)){
                    nouveauReglageMaxVitesse += 500;
                }
                choixAdapterOuAnnuler[0]= "Adapter le max à "+nouveauReglageMaxVitesse+ " tr/min";
                estProblemeVitesse = true;
                builder.setSingleChoiceItems(choixAdapterOuAnnuler,0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choixUtilisateurAdapterOuAnnuler = choixAdapterMaximumOuAnnulerTransfertDuProfilConv.adapterMaximum;
                                break;
                            case 1:
                                choixUtilisateurAdapterOuAnnuler = choixAdapterMaximumOuAnnulerTransfertDuProfilConv.annulerTransfert;
                                break;
                        }
                    }
                });

                double finalNouveauReglageMaxVitesse = nouveauReglageMaxVitesse;
                boolean finalEstProblemeCourant = estProblemeCourant;
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        switch (choixUtilisateurAdapterOuAnnuler){
                            case adapterMaximum:
                                modeReglage.getVitesseRotation().setValMaxJauge(finalNouveauReglageMaxVitesse);
                                if(!finalEstProblemeCourant){
                                    modeReglage.ajouterUnPointAuProfilConvEtTrierArrayList(Double.valueOf(finalAbscisse),Double.valueOf(finalOrdonnee));
                                }
                                break;
                            case annulerTransfert:
                                modeReglage.supprimerProfilConv();
                                break;
                            default:
                                break;
                        }
                    }
                });
                AlertDialog adapterMaximumVitesseOuAnnulerTransfertAlertDialog = builder.create();
                adapterMaximumVitesseOuAnnulerTransfertAlertDialog.show();
            }
            if(Double.valueOf(ordonnee)>modeReglage.getCourantEnEntree().getValMaxJauge()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModeReglageActivity.this);
                String [] choixAdapterOuAnnuler = new String[2];
                choixAdapterOuAnnuler[1]="Annuler le transfert du profilConv";
                estProblemeCourant=true;
                builder.setTitle("Le réglage max du courant en entrée est trop bas pour le transfert");
                nouveauReglageMaxCourant = 5;
                while(nouveauReglageMaxCourant<Double.valueOf(ordonnee)){
                    nouveauReglageMaxCourant += 5;
                }
                choixAdapterOuAnnuler[0]= "Adapter le max à "+nouveauReglageMaxCourant+ " A";
                builder.setSingleChoiceItems(choixAdapterOuAnnuler,0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choixUtilisateurAdapterOuAnnuler = choixAdapterMaximumOuAnnulerTransfertDuProfilConv.adapterMaximum;
                                break;
                            case 1:
                                choixUtilisateurAdapterOuAnnuler = choixAdapterMaximumOuAnnulerTransfertDuProfilConv.annulerTransfert;
                                break;
                        }
                    }
                });
                boolean finalEstProblemeVitesse = estProblemeVitesse;
                double finalNouveauReglageMaxCourant = nouveauReglageMaxCourant;
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        switch (choixUtilisateurAdapterOuAnnuler){
                            case adapterMaximum:
                                modeReglage.getCourantEnEntree().setValMaxJauge(finalNouveauReglageMaxCourant);
                                if(!finalEstProblemeVitesse){
                                    modeReglage.ajouterUnPointAuProfilConvEtTrierArrayList(Double.valueOf(finalAbscisse),Double.valueOf(finalOrdonnee));
                                }
                                break;
                            case annulerTransfert:
                                modeReglage.supprimerProfilConv();
                                break;
                            default:
                                break;
                        }
                    }
                });
                AlertDialog adapterMaximumCourantOuAnnulerTransfertAlertDialog = builder.create();
                adapterMaximumCourantOuAnnulerTransfertAlertDialog.show();
            }
        }
        else if (choixUtilisateurAdapterOuAnnuler!=choixAdapterMaximumOuAnnulerTransfertDuProfilConv.annulerTransfert) {
            modeReglage.ajouterUnPointAuProfilConvEtTrierArrayList(Double.valueOf(abscisse), Double.valueOf(ordonnee));
            afficherLesPointsSurLeGraphique();
        }

    }

    /**
     * Permet d'afficher le point sélectionné par l'utilisateur sur le graphique.
     *
     * @param positionDuPoint the position du point
     */
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

    /**
     * Permet d'afficher tous les points de toutes les séries sur le graphique.
     */
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
        adapterEchelleDuGraphique();
        graphique.addSeries(profilConv);
        if(regressionPolynomial!=null){
            graphique.addSeries(regressionPolynomial);
        }
        graphique.addSeries(profilAppli);
        if(pointSelectionne!=null){
            graphique.addSeries(pointSelectionne);
        }
        if(pointDeFonctionnement!=null){
            graphique.addSeries(pointDeFonctionnement);
        }
    }

    /**
     * Initialiser le graphique.
     */
    public void initialiserGraphique(){
        graphique.getViewport().setYAxisBoundsManual(true);
        graphique.getViewport().setMinY(0);
        graphique.getViewport().setMaxY(modeReglage.getCourantEnEntree().getValMaxJauge());
        graphique.getViewport().setXAxisBoundsManual(true);
        graphique.getViewport().setMinX(0);
        graphique.getViewport().setMaxX(modeReglage.getVitesseRotation().getValMaxJauge());
    }

    /**
     * Afficher point de fonctionnement.
     */
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

    /**
     * Adapter l'échelle du graphique en fonction des coordonnées du plus grand point.
     */
    public void adapterEchelleDuGraphique(){
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
