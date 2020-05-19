package com.dutinfo.gimeole;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.biansemao.widget.ThermometerView;
import com.dutinfo.gimeole.ClassesUtiles.BoiteAOutils;
import com.dutinfo.gimeole.ClassesUtiles.ModeProduction;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.sccomponents.gauges.gr008.GR008;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.DeviceCallback;


public class MainActivity extends AppCompatActivity {

    //Création du modeProduction
    final ModeProduction modeProduction = new ModeProduction();

    //Création des jauges
    GR008 jaugeVitesseRotation, jaugeTensionEnEntree, jaugeCourantEnEntree, jaugeTensionEnSortie, jaugeCourantEnSortie, jaugePuissanceFournie;
    RoundCornerProgressBar jaugeEnergieProduite;
    GraphView echelleLogarithmique;
    FrameLayout cacheDuGrapheDeLeEchelleLogarithmique;
    ThermometerView thermometreAlternateur, thermometreFrein;
    ThermometerView.ThermometerBuilder thermometreAlternateurType, thermometreFreinType;

    //Création valeurs à afficher
    TextView vitesseRotation, tensionEnEntree, courantEnEntree, tensionEnSortie, courantEnSortie, puissanceFournie, energieProduite, temperatureAlternateur,temperatureFrein, nomJaugeCourante;

    //Création des valeurs moyennes et max à afficher
    TextView valeurMoyenne, valeurMax, valeurEnergie;

    //Création des noms des jauges à afficher
    String nomJaugeVitesseRotation,nomJaugeTensionEnEntree,nomJaugeCourantEnEntree,nomJaugeTensionEnSortie,nomJaugeCourantEnSortie,nomJaugeEnergieProduite,nomJaugePuissanceFournie,nomJaugeTemperatureAlternateur,nomJaugeTemperatureFrein;

    //Création de l'indicateur Bluetooth et de du nom du périphérique connecté
    ImageView logoBluetooth;
    TextView nomPeripheriqueBluetooth;

    //Création des bouttons permettant de changer la jauge affiché
    Button boutonVitesseRotation,boutonTensionEnEntree,boutonCourantEnEntree,boutonTensionEnSortie,boutonCourantEnSortie,boutonPuissanceFournie,boutonEnergieProduite,boutonTemperatureAlternateur,boutonTemperatureFrein,boutonRAZenergie;

    //Création du bouton permettant d'entrer dans l'activité réglage et test
    Button boutonReglageJauges, boutonModeReglage;

    //Nécessaire à la connexion Bluetooth
    private Bluetooth bluetooth;
    private BluetoothDevice device;

    //indicateur permettant de savoir si le bluetooth a été deconnecté à cause d'un changement d'activité
    boolean estDeconnecteDuBluetoothCarChangementDActivite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialisation de la connexion Bluetooth
        device = getIntent().getParcelableExtra("device");
        bluetooth = new Bluetooth(this);
        bluetooth.setCallbackOnUI(this);
        bluetooth.setDeviceCallback(deviceCallback);
        bluetooth.onStart();
        bluetooth.connectToDevice(device);

        estDeconnecteDuBluetoothCarChangementDActivite=false;

        //Lien entre les jauges de l'interface et de l'activté
        jaugeVitesseRotation = findViewById(R.id.jaugeVitesseRotation);
        jaugeTensionEnEntree = findViewById(R.id.jaugeTensionEnEntree);
        jaugeCourantEnEntree = findViewById(R.id.jaugeCourantEnEntree);
        jaugeTensionEnSortie = findViewById(R.id.jaugeTensionEnSortie);
        jaugeCourantEnSortie = findViewById(R.id.jaugeCourantEnSortie);
        jaugePuissanceFournie = findViewById(R.id.jaugePuissanceFournie);
        jaugeEnergieProduite = findViewById(R.id.jaugeEnergieProduite);
        echelleLogarithmique = findViewById(R.id.echelleLogarithmiqueEnergieProduite);
        cacheDuGrapheDeLeEchelleLogarithmique = findViewById(R.id.cacheDuGrapheDeLeEchelleLogarithmique);
        parametrerEtAfficherThermometreAlternateur();
        parametrerEtAfficherThermometreFrein();

        //Nom des jauges
        nomJaugeVitesseRotation = getResources().getString(R.string.nomJaugeVitesseDeRotation);
        nomJaugeTensionEnEntree = getResources().getString(R.string.nomJaugeTensionEnEntree);
        nomJaugeCourantEnEntree = getResources().getString(R.string.nomJaugeCourantEnEntree);
        nomJaugeTensionEnSortie = getResources().getString(R.string.nomJaugeTensionEnSortie);
        nomJaugeCourantEnSortie = getResources().getString(R.string.nomJaugeCourantEnSortie);
        nomJaugeEnergieProduite = getResources().getString(R.string.nomJaugeEnergieProduite);
        nomJaugePuissanceFournie = getResources().getString(R.string.nomJaugePuissanceFournie);
        nomJaugeTemperatureAlternateur = getResources().getString(R.string.nomJaugeTemperatureAlternateur);
        nomJaugeTemperatureFrein = getResources().getString(R.string.nomJaugeTemperatureFrein);



        //Lien entre les valeurs moyennes/max de l'interface et l'activité
        valeurMoyenne = findViewById(R.id.valeurMoyenne);
        valeurMax = findViewById(R.id.valeurMax);
        valeurEnergie = findViewById(R.id.valeurEnergieProduite);


        //Lien entre les boutons de l'interface et l'activité
        boutonVitesseRotation = findViewById(R.id.boutonVitesseDeRotation);
        boutonTensionEnEntree = findViewById(R.id.boutonTensionEnEntree);
        boutonCourantEnEntree = findViewById(R.id.boutonCourantEnEntree);
        boutonTensionEnSortie = findViewById(R.id.boutonTensionEnSortie);
        boutonCourantEnSortie = findViewById(R.id.boutonCourantEnSortie);
        boutonPuissanceFournie = findViewById(R.id.boutonPuissanceFournie);
        boutonEnergieProduite = findViewById(R.id.boutonEnergieProduite);
        boutonTemperatureAlternateur = findViewById(R.id.boutonTemperatureAlternateur);
        boutonTemperatureFrein = findViewById(R.id.boutonTemperatureFrein);
        boutonReglageJauges = findViewById(R.id.boutonReglage);
        boutonRAZenergie = findViewById(R.id.boutonRAZenergie);
        boutonModeReglage = findViewById(R.id.boutonModeReglage);

        //Lien entre le nom de la jauge courante de l'interface et l'activité
        nomJaugeCourante = findViewById(R.id.nomJaugeCourante);

        //Lien entre les valeurs courante de l'interface et l'activité
        vitesseRotation = findViewById(R.id.vitesseRotation);
        tensionEnEntree = findViewById(R.id.tensionEnEntree);
        courantEnEntree = findViewById(R.id.courantEnEntree);
        tensionEnSortie = findViewById(R.id.tensionEnSortie);
        courantEnSortie = findViewById(R.id.courantEnSortie);
        puissanceFournie = findViewById(R.id.puissanceFournie);
        energieProduite = findViewById(R.id.energieProduite);
        temperatureAlternateur = findViewById(R.id.temperatureAlternateur);
        temperatureFrein = findViewById(R.id.temperatureFrein);

        //Lien entre l'indicateur Bluetooth/nom du périphérique de l'interface et l'activité
        logoBluetooth = findViewById(R.id.logoBluetooth);
        nomPeripheriqueBluetooth = findViewById(R.id.nomPeripheriqueBluetooth);
        nomPeripheriqueBluetooth.setText(getResources().getString(R.string.connecteA));


        changerMinMaxDesJauges();
        afficherNouvelleJauge();


        boutonVitesseRotation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProduction.setEtatModeProduction(ModeProduction.UnEtat.jaugeVitesseRotation);
                afficherNouvelleJauge();
            }
        });

        boutonTensionEnEntree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProduction.setEtatModeProduction(ModeProduction.UnEtat.jaugeTensionEnEntree);
                afficherNouvelleJauge();
            }
        });

        boutonCourantEnEntree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProduction.setEtatModeProduction(ModeProduction.UnEtat.jaugeCourantEnEntree);
                afficherNouvelleJauge();
            }
        });

        boutonTensionEnSortie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProduction.setEtatModeProduction(ModeProduction.UnEtat.jaugeTensionEnSortie);
                afficherNouvelleJauge();
            }
        });

        boutonCourantEnSortie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProduction.setEtatModeProduction(ModeProduction.UnEtat.jaugeCourantEnSortie);
                afficherNouvelleJauge();
            }
        });

        boutonPuissanceFournie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProduction.setEtatModeProduction(ModeProduction.UnEtat.jaugePuissanceFournie);
                afficherNouvelleJauge();
            }
        });

        boutonEnergieProduite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProduction.setEtatModeProduction(ModeProduction.UnEtat.jaugeEnergieProduite);
                afficherNouvelleJauge();
            }
        });

        boutonTemperatureAlternateur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProduction.setEtatModeProduction(ModeProduction.UnEtat.jaugeTemperatureAlternateur);
                afficherNouvelleJauge();
            }
        });

        boutonTemperatureFrein.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProduction.setEtatModeProduction(ModeProduction.UnEtat.jaugeTemperatureFrein);
                afficherNouvelleJauge();
            }
        });

        boutonReglageJauges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent reglageActivity = new Intent(MainActivity.this, ReglageJaugeActivity.class);
                reglageActivity.putExtra("tabMinMax", modeProduction.getMinMaxDesJauges());
                startActivityForResult(reglageActivity, 1);
            }
        });

        boutonRAZenergie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                modeProduction.getEnergieProduite().setValRAZ(modeProduction.getEnergieProduite().getValCourante());
                jaugeEnergieProduite.setProgress(0);
            }
        });

        boutonModeReglage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(MainActivity.this, ModeReglageActivity.class);
                intent.putExtra("device", device);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            modeProduction.setMinMaxDesJauges(data.getDoubleArrayExtra("tabMinMax"));
            changerMinMaxDesJauges();
        }
    }



    public void parametrerEtAfficherThermometreAlternateur(){
        thermometreAlternateurType = new ThermometerView.ThermometerBuilder(this.getApplicationContext());
        thermometreAlternateurType.setMinScaleValue((float) modeProduction.getTemperatureAlternateur().getValMinJauge());
        thermometreAlternateurType.setMaxScaleValue((float) modeProduction.getTemperatureAlternateur().getValMaxJauge());
        ConstraintLayout layoutOfDynamicContent = findViewById(R.id.thermometreAlternateur);
        layoutOfDynamicContent.removeAllViewsInLayout();
        thermometreAlternateur =thermometreAlternateurType.builder();
        thermometreAlternateur.setCurValue((float) modeProduction.getTemperatureAlternateur().getValMinJauge());
        if(modeProduction.getEtatModeProduction()!= ModeProduction.UnEtat.jaugeTemperatureAlternateur)
        {
            thermometreAlternateur.setVisibility(View.INVISIBLE);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams. WRAP_CONTENT , LinearLayout.LayoutParams. WRAP_CONTENT );
        layoutOfDynamicContent.addView(thermometreAlternateur, params);
    }

    public void parametrerEtAfficherThermometreFrein(){
        thermometreFreinType = new ThermometerView.ThermometerBuilder(this.getApplicationContext());
        thermometreFreinType.setMinScaleValue((float) modeProduction.getTemperatureFrein().getValMinJauge());
        thermometreFreinType.setMaxScaleValue((float) modeProduction.getTemperatureFrein().getValMaxJauge());
        ConstraintLayout layoutOfDynamicContent = findViewById(R.id.thermometreFrein);
        layoutOfDynamicContent.removeAllViewsInLayout();
        thermometreFrein =thermometreFreinType.builder();
        thermometreFrein.setCurValue((float) modeProduction.getTemperatureFrein().getValMinJauge());
        if(modeProduction.getEtatModeProduction()!= ModeProduction.UnEtat.jaugeTemperatureFrein)
        {
            thermometreFrein.setVisibility(View.INVISIBLE);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams. WRAP_CONTENT , LinearLayout.LayoutParams. WRAP_CONTENT );
        layoutOfDynamicContent.addView(thermometreFrein, params);
    }


        public void afficherValeur (String chaineRecuParBluetooth){
            String premierCaractere = (chaineRecuParBluetooth.substring(0,1));
            String valeurCourante = (chaineRecuParBluetooth.substring(1));
            switch (premierCaractere){
                case "$" :
                    modeProduction.getVitesseRotation().setValCourante(Double.parseDouble(valeurCourante));
                    jaugeVitesseRotation.setValue(modeProduction.getVitesseRotation().getValCourante());
                    vitesseRotation.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(4,modeProduction.getVitesseRotation().getValCourante()));
                    break;
                case ":" :
                    modeProduction.getTensionEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                    jaugeTensionEnEntree.setValue(modeProduction.getTensionEnEntree().getValCourante());
                    tensionEnEntree.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeProduction.getTensionEnEntree().getValCourante()));

                    break;
                case ";" :
                    modeProduction.getCourantEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                    jaugeCourantEnEntree.setValue(modeProduction.getCourantEnEntree().getValCourante());
                    courantEnEntree.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeProduction.getCourantEnEntree().getValCourante()));
                    break;
                case "[" :
                    modeProduction.getTensionEnSortie().setValCourante(Double.parseDouble(valeurCourante));
                    jaugeTensionEnSortie.setValue(modeProduction.getTensionEnSortie().getValCourante());
                    tensionEnSortie.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeProduction.getTensionEnSortie().getValCourante()));

                    break;
                case "]" :
                    modeProduction.getCourantEnSortie().setValCourante(Double.parseDouble(valeurCourante));
                    jaugeCourantEnSortie.setValue(modeProduction.getCourantEnSortie().getValCourante());
                    courantEnSortie.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeProduction.getCourantEnSortie().getValCourante()));
                    break;
                case "%" :
                    modeProduction.getPuissanceFournie().setValCourante(Double.parseDouble(valeurCourante));
                    jaugePuissanceFournie.setValue(modeProduction.getPuissanceFournie().getValCourante());
                    puissanceFournie.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeProduction.getPuissanceFournie().getValCourante()));
                    break;
                case "!" :
                    modeProduction.getEnergieProduite().setValCourante(Double.parseDouble(valeurCourante));
                    jaugeEnergieProduite.setProgress((float) modeProduction.getEnergieProduite().pourcentageRempliDeLaJauge());
                    energieProduite.setText(BoiteAOutils.obtenirEcritureScientifiqueAvecChiffresSignificatifs(3,modeProduction.getEnergieProduite().getValCourante()));
                    break;
                case "(" :
                    modeProduction.getTemperatureAlternateur().setValCourante(Double.parseDouble(valeurCourante));
                    changerMinMaxDuThermometreAlternateurCarValeurCouranteTropBasseOuTropElevee();
                    thermometreAlternateur.setValueAndStartAnim((float) modeProduction.getTemperatureAlternateur().getValCourante());
                    temperatureAlternateur.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeProduction.getTemperatureAlternateur().getValCourante()));
                    break;
                case ")" :
                    modeProduction.getTemperatureFrein().setValCourante(Double.parseDouble(valeurCourante));
                    changerMinMaxDuThermometreFreinCarValeurCouranteTropBasseOuTropElevee();
                    thermometreFrein.setValueAndStartAnim((float) modeProduction.getTemperatureFrein().getValCourante());
                    temperatureFrein.setText(BoiteAOutils.arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(3,modeProduction.getTemperatureFrein().getValCourante()));
                    break;
                default:
                    break;
            }

        switch (modeProduction.getEtatModeProduction()) {
            case jaugeVitesseRotation:
                valeurMoyenne.setText(getResources().getString(R.string.valeurVitesseMoyenne)+ modeProduction.getVitesseRotation().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurVitesseMax)+ modeProduction.getVitesseRotation().getValMaxCourante());
                break;
            case jaugeTensionEnEntree:
                valeurMoyenne.setText(getResources().getString(R.string.valeurTensionMoyenne)+ modeProduction.getTensionEnEntree().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurTensionMax)+ modeProduction.getTensionEnEntree().getValMaxCourante());
                break;
            case jaugeCourantEnEntree:
                valeurMoyenne.setText(getResources().getString(R.string.valeurCourantMoyen)+ modeProduction.getCourantEnEntree().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurCourantMax)+ modeProduction.getCourantEnEntree().getValMaxCourante());
                break;
            case jaugePuissanceFournie:
                valeurMoyenne.setText(getResources().getString(R.string.valeurPuissanceMoyenne)+ modeProduction.getPuissanceFournie().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurPuissanceMax)+ modeProduction.getPuissanceFournie().getValMaxCourante());
                break;
            case jaugeEnergieProduite:
                valeurEnergie.setText(getResources().getString(R.string.valeurEnergieProduite)+BoiteAOutils.obtenirEcritureScientifiqueAvecChiffresSignificatifs(3, modeProduction.getEnergieProduite().getValCourante()));
                break;
            case jaugeTemperatureAlternateur:
                valeurMoyenne.setText(getResources().getString(R.string.valeurTemperatureMoyenne)+ modeProduction.getTemperatureAlternateur().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurTemperatureMax)+ modeProduction.getTemperatureAlternateur().getValMaxCourante());
                break;
            case jaugeTemperatureFrein:
                valeurMoyenne.setText(getResources().getString(R.string.valeurTemperatureMoyenne)+ modeProduction.getTemperatureFrein().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurTemperatureMax)+ modeProduction.getTemperatureFrein().getValMaxCourante());
                break;
            default:
                break;
        }
    }

    public void supprimerJaugeAfficheeActuellement(){
        switch (modeProduction.getEtatModeProduction()){
            case jaugeVitesseRotation:
                jaugeVitesseRotation.setVisibility(View.INVISIBLE);
                boutonVitesseRotation.setTextColor(getResources().getColor(R.color.cyan));
                boutonVitesseRotation.setTypeface(null, Typeface.NORMAL);
                break;
            case jaugeTensionEnEntree:
                jaugeTensionEnEntree.setVisibility(View.INVISIBLE);
                boutonTensionEnEntree.setTextColor(getResources().getColor(R.color.cyan));
                boutonTensionEnEntree.setTypeface(null, Typeface.NORMAL);
                break;
            case jaugeCourantEnEntree:
                jaugeCourantEnEntree.setVisibility(View.INVISIBLE);
                boutonCourantEnEntree.setTextColor(getResources().getColor(R.color.cyan));
                boutonCourantEnEntree.setTypeface(null, Typeface.NORMAL);
                break;
            case jaugeTensionEnSortie:
                jaugeTensionEnSortie.setVisibility(View.INVISIBLE);
                boutonTensionEnSortie.setTextColor(getResources().getColor(R.color.cyan));
                boutonTensionEnSortie.setTypeface(null, Typeface.NORMAL);
                break;
            case jaugeCourantEnSortie:
                jaugeCourantEnSortie.setVisibility(View.INVISIBLE);
                boutonCourantEnSortie.setTextColor(getResources().getColor(R.color.cyan));
                boutonCourantEnSortie.setTypeface(null, Typeface.NORMAL);
                break;
            case jaugePuissanceFournie:
                jaugePuissanceFournie.setVisibility(View.INVISIBLE);
                boutonPuissanceFournie.setTextColor(getResources().getColor(R.color.cyan));
                boutonPuissanceFournie.setTypeface(null, Typeface.NORMAL);
                break;
            case jaugeEnergieProduite:
                jaugeEnergieProduite.setVisibility(View.INVISIBLE);
                echelleLogarithmique.setVisibility(View.INVISIBLE);
                cacheDuGrapheDeLeEchelleLogarithmique.setVisibility(View.INVISIBLE);
                valeurEnergie.setVisibility(View.INVISIBLE);
                valeurMoyenne.setVisibility(View.VISIBLE);
                valeurMax.setVisibility(View.VISIBLE);
                boutonEnergieProduite.setTextColor(getResources().getColor(R.color.cyan));
                boutonEnergieProduite.setTypeface(null, Typeface.NORMAL);
                break;
            case jaugeTemperatureAlternateur:
                thermometreAlternateur.setVisibility(View.INVISIBLE);
                boutonTemperatureAlternateur.setTextColor(getResources().getColor(R.color.cyan));
                boutonTemperatureAlternateur.setTypeface(null, Typeface.NORMAL);
                break;
            case jaugeTemperatureFrein:
                thermometreFrein.setVisibility(View.INVISIBLE);
                boutonTemperatureFrein.setTextColor(getResources().getColor(R.color.cyan));
                boutonTemperatureFrein.setTypeface(null, Typeface.NORMAL);
                break;
            default:
                break;
        }
    }

    public void afficherNouvelleJauge(){
        switch (modeProduction.getEtatModeProduction()){
            case jaugeVitesseRotation:
                jaugeVitesseRotation.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeVitesseRotation);
                valeurMoyenne.setText(getResources().getString(R.string.valeurVitesseMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurVitesseMax));
                boutonVitesseRotation.setTextColor(getResources().getColor(R.color.violet));
                boutonVitesseRotation.setTypeface(null, Typeface.BOLD);
                break;
            case jaugeTensionEnEntree:
                jaugeTensionEnEntree.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTensionEnEntree);
                valeurMoyenne.setText(getResources().getString(R.string.valeurTensionMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurTensionMax));
                boutonTensionEnEntree.setTextColor(getResources().getColor(R.color.violet));
                boutonTensionEnEntree.setTypeface(null, Typeface.BOLD);
                break;
            case jaugeCourantEnEntree:
                jaugeCourantEnEntree.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeCourantEnEntree);
                valeurMoyenne.setText(getResources().getString(R.string.valeurCourantMoyen));
                valeurMax.setText(getResources().getString(R.string.valeurCourantMax));
                boutonCourantEnEntree.setTextColor(getResources().getColor(R.color.violet));
                boutonCourantEnEntree.setTypeface(null, Typeface.BOLD);
                break;
            case jaugeTensionEnSortie:
                jaugeTensionEnSortie.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTensionEnSortie);
                valeurMoyenne.setText(getResources().getString(R.string.valeurTensionMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurTensionMax));
                boutonTensionEnSortie.setTextColor(getResources().getColor(R.color.violet));
                boutonTensionEnSortie.setTypeface(null, Typeface.BOLD);
                break;
            case jaugeCourantEnSortie:
                jaugeCourantEnSortie.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeCourantEnSortie);
                valeurMoyenne.setText(getResources().getString(R.string.valeurCourantMoyen));
                valeurMax.setText(getResources().getString(R.string.valeurCourantMax));
                boutonCourantEnSortie.setTextColor(getResources().getColor(R.color.violet));
                boutonCourantEnSortie.setTypeface(null, Typeface.BOLD);
                break;
            case jaugePuissanceFournie:
                jaugePuissanceFournie.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugePuissanceFournie);
                valeurMoyenne.setText(getResources().getString(R.string.valeurPuissanceMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurPuissanceMoyenne));
                boutonPuissanceFournie.setTextColor(getResources().getColor(R.color.violet));
                boutonPuissanceFournie.setTypeface(null, Typeface.BOLD);
                break;
            case jaugeEnergieProduite:
                valeurMoyenne.setVisibility(View.INVISIBLE);
                valeurMax.setVisibility(View.INVISIBLE);
                valeurEnergie.setVisibility(View.VISIBLE);
                jaugeEnergieProduite.setVisibility(View.VISIBLE);
                echelleLogarithmique.setVisibility(View.VISIBLE);
                cacheDuGrapheDeLeEchelleLogarithmique.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeEnergieProduite);
                boutonEnergieProduite.setTextColor(getResources().getColor(R.color.violet));
                boutonEnergieProduite.setTypeface(null, Typeface.BOLD);
                break;
            case jaugeTemperatureAlternateur:
                thermometreAlternateur.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTemperatureAlternateur);
                valeurMoyenne.setText(getResources().getString(R.string.valeurTemperatureMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurTemperatureMax));
                boutonTemperatureAlternateur.setTextColor(getResources().getColor(R.color.violet));
                boutonTemperatureAlternateur.setTypeface(null, Typeface.BOLD);
                break;
            case jaugeTemperatureFrein:
                thermometreFrein.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTemperatureFrein);
                valeurMoyenne.setText(getResources().getString(R.string.valeurTemperatureMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurTemperatureMax));
                boutonTemperatureFrein.setTextColor(getResources().getColor(R.color.violet));
                boutonTemperatureFrein.setTypeface(null, Typeface.BOLD);
                break;
            default:
                break;
        }

    }

    public void changerMinMaxDesJauges() {
        jaugeVitesseRotation.setMinValue(modeProduction.getVitesseRotation().getValMinJauge());
        jaugeVitesseRotation.setMaxValue(modeProduction.getVitesseRotation().getValMaxJauge());
        jaugeTensionEnEntree.setMinValue(modeProduction.getTensionEnEntree().getValMinJauge());
        jaugeTensionEnEntree.setMaxValue(modeProduction.getTensionEnEntree().getValMaxJauge());
        jaugeCourantEnEntree.setMinValue(modeProduction.getCourantEnEntree().getValMinJauge());
        jaugeCourantEnEntree.setMaxValue(modeProduction.getCourantEnEntree().getValMaxJauge());
        jaugeTensionEnSortie.setMinValue(modeProduction.getTensionEnSortie().getValMinJauge());
        jaugeTensionEnSortie.setMaxValue(modeProduction.getTensionEnSortie().getValMaxJauge());
        jaugeCourantEnSortie.setMinValue(modeProduction.getCourantEnSortie().getValMinJauge());
        jaugeCourantEnSortie.setMaxValue(modeProduction.getCourantEnSortie().getValMaxJauge());
        jaugePuissanceFournie.setMinValue(modeProduction.getPuissanceFournie().getValMinJauge());
        jaugePuissanceFournie.setMaxValue(modeProduction.getPuissanceFournie().getValMaxJauge());
        jaugeEnergieProduite.setMax(100);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(echelleLogarithmique);
        staticLabelsFormatter.setHorizontalLabels(modeProduction.getEnergieProduite().echelleLogarithmique());
        staticLabelsFormatter.setVerticalLabels(new String[] {"1",""});
        echelleLogarithmique.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        parametrerEtAfficherThermometreAlternateur();
        parametrerEtAfficherThermometreFrein();
        changerEcartsEntreValeursDesJaugesCirculaires();
    }

    public void changerEcartsEntreValeursDesJaugesCirculaires(){
        jaugeVitesseRotation.setMajorTicks((modeProduction.getVitesseRotation().getValMaxJauge()- modeProduction.getVitesseRotation().getValMinJauge())/10);
        jaugeVitesseRotation.setMinorTicks((modeProduction.getVitesseRotation().getValMaxJauge()- modeProduction.getVitesseRotation().getValMinJauge())/10/4);
        jaugeTensionEnEntree.setMajorTicks((modeProduction.getTensionEnEntree().getValMaxJauge()- modeProduction.getTensionEnEntree().getValMinJauge())/10);
        jaugeTensionEnEntree.setMinorTicks((modeProduction.getTensionEnEntree().getValMaxJauge()- modeProduction.getTensionEnEntree().getValMinJauge())/10/4);
        jaugeCourantEnEntree.setMajorTicks((modeProduction.getCourantEnEntree().getValMaxJauge()- modeProduction.getCourantEnEntree().getValMinJauge())/10);
        jaugeCourantEnEntree.setMinorTicks((modeProduction.getCourantEnEntree().getValMaxJauge()- modeProduction.getCourantEnEntree().getValMinJauge())/10/4);
        jaugeTensionEnSortie.setMajorTicks((modeProduction.getTensionEnSortie().getValMaxJauge()- modeProduction.getTensionEnSortie().getValMinJauge())/10);
        jaugeTensionEnSortie.setMinorTicks((modeProduction.getTensionEnSortie().getValMaxJauge()- modeProduction.getTensionEnSortie().getValMinJauge())/10/4);
        jaugeCourantEnSortie.setMajorTicks((modeProduction.getCourantEnSortie().getValMaxJauge()- modeProduction.getCourantEnSortie().getValMinJauge())/10);
        jaugeCourantEnSortie.setMinorTicks((modeProduction.getCourantEnSortie().getValMaxJauge()- modeProduction.getCourantEnSortie().getValMinJauge())/10/4);
        jaugePuissanceFournie.setMajorTicks((modeProduction.getPuissanceFournie().getValMaxJauge()- modeProduction.getPuissanceFournie().getValMinJauge())/10);
        jaugePuissanceFournie.setMinorTicks((modeProduction.getPuissanceFournie().getValMaxJauge()- modeProduction.getPuissanceFournie  ().getValMinJauge())/10/4);
    }

    public void changerMinMaxDuThermometreAlternateurCarValeurCouranteTropBasseOuTropElevee(){
        if(modeProduction.getTemperatureAlternateur().getValCourante()- modeProduction.getTemperatureAlternateur().getValMinJauge()<5){
            do {
                modeProduction.getTemperatureAlternateur().setValMinJauge(modeProduction.getTemperatureAlternateur().getValMinJauge()-5);
                modeProduction.getTemperatureAlternateur().setValMaxJauge(modeProduction.getTemperatureAlternateur().getValMaxJauge()-5);

            }while(modeProduction.getTemperatureAlternateur().getValCourante()- modeProduction.getTemperatureAlternateur().getValMinJauge()<5);
            parametrerEtAfficherThermometreAlternateur();
        }
        else if(modeProduction.getTemperatureAlternateur().getValMaxJauge()- modeProduction.getTemperatureAlternateur().getValCourante()<5){
            do {
                modeProduction.getTemperatureAlternateur().setValMinJauge(modeProduction.getTemperatureAlternateur().getValMinJauge()+5);
                modeProduction.getTemperatureAlternateur().setValMaxJauge(modeProduction.getTemperatureAlternateur().getValMaxJauge()+5);
            }while(modeProduction.getTemperatureAlternateur().getValMaxJauge()- modeProduction.getTemperatureAlternateur().getValCourante()<5);
            parametrerEtAfficherThermometreAlternateur();
        }
    }

    public void changerMinMaxDuThermometreFreinCarValeurCouranteTropBasseOuTropElevee(){
        if(modeProduction.getTemperatureFrein().getValCourante()- modeProduction.getTemperatureFrein().getValMinJauge()<5){
            do {
                modeProduction.getTemperatureFrein().setValMinJauge(modeProduction.getTemperatureFrein().getValMinJauge()-5);
                modeProduction.getTemperatureFrein().setValMaxJauge(modeProduction.getTemperatureFrein().getValMaxJauge()-5);

            }while(modeProduction.getTemperatureFrein().getValCourante()- modeProduction.getTemperatureFrein().getValMinJauge()<5);
            parametrerEtAfficherThermometreFrein();
        }
        else if(modeProduction.getTemperatureFrein().getValMaxJauge()- modeProduction.getTemperatureFrein().getValCourante()<5){
            do {
                modeProduction.getTemperatureFrein().setValMinJauge(modeProduction.getTemperatureFrein().getValMinJauge()+5);
                modeProduction.getTemperatureFrein().setValMaxJauge(modeProduction.getTemperatureFrein().getValMaxJauge()+5);
            }while(modeProduction.getTemperatureFrein().getValMaxJauge()- modeProduction.getTemperatureFrein().getValCourante()<5);
            parametrerEtAfficherThermometreFrein();
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
        bluetooth.onStop();
        estDeconnecteDuBluetoothCarChangementDActivite =true;
        bluetooth.disconnect();


    }


}
