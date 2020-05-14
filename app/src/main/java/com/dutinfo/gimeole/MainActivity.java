package com.dutinfo.gimeole;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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
    final ModeProduction modeProd = new ModeProduction();

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
    Button boutonReglageJauges, boutonModeTest;

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
        boutonModeTest = findViewById(R.id.boutonModeTest);

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


        boutonVitesseRotation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeVitesseRotation);
                afficherNouvelleJauge();
            }
        });

        boutonTensionEnEntree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeTensionEnEntree);
                afficherNouvelleJauge();
            }
        });

        boutonCourantEnEntree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeCourantEnEntree);
                afficherNouvelleJauge();
            }
        });

        boutonTensionEnSortie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeTensionEnSortie);
                afficherNouvelleJauge();
            }
        });

        boutonCourantEnSortie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeCourantEnSortie);
                afficherNouvelleJauge();
            }
        });

        boutonPuissanceFournie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugePuissanceFournie);
                afficherNouvelleJauge();
            }
        });

        boutonEnergieProduite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeEnergieProduite);
                afficherNouvelleJauge();
            }
        });

        boutonTemperatureAlternateur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeTemperatureAlternateur);
                afficherNouvelleJauge();
            }
        });

        boutonTemperatureFrein.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                supprimerJaugeAfficheeActuellement();
                modeProd.setEtatModeProduction(ModeProduction.UnEtat.jaugeTemperatureFrein);
                afficherNouvelleJauge();
            }
        });

        boutonReglageJauges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent reglageActivity = new Intent(MainActivity.this, ReglageJaugeActivity.class);
                reglageActivity.putExtra("tabMinMax", modeProd.getMinMaxDesJauges());
                startActivityForResult(reglageActivity, 1);
            }
        });

        boutonRAZenergie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                modeProd.getEnergieProduite().setValRAZ(modeProd.getEnergieProduite().getValCourante());
                jaugeEnergieProduite.setProgress(0);
            }
        });

        boutonModeTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(MainActivity.this, ModeTestActivity.class);
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
            modeProd.setMinMaxDesJauges(data.getDoubleArrayExtra("tabMinMax"));
            changerMinMaxDesJauges();
        }
    }



    public void parametrerEtAfficherThermometreAlternateur(){
        thermometreAlternateurType = new ThermometerView.ThermometerBuilder(this.getApplicationContext());
        thermometreAlternateurType.setMinScaleValue((float)modeProd.getTemperatureAlternateur().getValMinJauge());
        thermometreAlternateurType.setMaxScaleValue((float)modeProd.getTemperatureAlternateur().getValMaxJauge());
        ConstraintLayout layoutOfDynamicContent = findViewById(R.id.thermometreAlternateur);
        layoutOfDynamicContent.removeAllViewsInLayout();
        thermometreAlternateur =thermometreAlternateurType.builder();
        thermometreAlternateur.setCurValue((float)modeProd.getTemperatureAlternateur().getValMinJauge());
        if(modeProd.getEtatModeProduction()!= ModeProduction.UnEtat.jaugeTemperatureAlternateur)
        {
            thermometreAlternateur.setVisibility(View.INVISIBLE);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams. WRAP_CONTENT , LinearLayout.LayoutParams. WRAP_CONTENT );
        layoutOfDynamicContent.addView(thermometreAlternateur, params);
    }

    public void parametrerEtAfficherThermometreFrein(){
        thermometreFreinType = new ThermometerView.ThermometerBuilder(this.getApplicationContext());
        thermometreFreinType.setMinScaleValue((float)modeProd.getTemperatureFrein().getValMinJauge());
        thermometreFreinType.setMaxScaleValue((float)modeProd.getTemperatureFrein().getValMaxJauge());
        ConstraintLayout layoutOfDynamicContent = findViewById(R.id.thermometreFrein);
        layoutOfDynamicContent.removeAllViewsInLayout();
        thermometreFrein =thermometreFreinType.builder();
        thermometreFrein.setCurValue((float)modeProd.getTemperatureFrein().getValMinJauge());
        if(modeProd.getEtatModeProduction()!= ModeProduction.UnEtat.jaugeTemperatureFrein)
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
                modeProd.getVitesseRotation().setValCourante(Double.parseDouble(valeurCourante));
                jaugeVitesseRotation.setValue(modeProd.getVitesseRotation().getValCourante());
                vitesseRotation.setText(Double.toString(modeProd.getVitesseRotation().getValCourante()));
                break;
            case ":" :
                modeProd.getTensionEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                jaugeTensionEnEntree.setValue(modeProd.getTensionEnEntree().getValCourante());
                tensionEnEntree.setText(Double.toString(modeProd.getTensionEnEntree().getValCourante()));

                break;
            case ";" :
                modeProd.getCourantEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                jaugeCourantEnEntree.setValue(modeProd.getCourantEnEntree().getValCourante());
                courantEnEntree.setText(Double.toString(modeProd.getCourantEnEntree().getValCourante()));
                break;
            case "[" :
                modeProd.getTensionEnSortie().setValCourante(Double.parseDouble(valeurCourante));
                jaugeTensionEnSortie.setValue(modeProd.getTensionEnSortie().getValCourante());
                tensionEnSortie.setText(Double.toString(modeProd.getTensionEnSortie().getValCourante()));

                break;
            case "]" :
                modeProd.getCourantEnSortie().setValCourante(Double.parseDouble(valeurCourante));
                jaugeCourantEnSortie.setValue(modeProd.getCourantEnSortie().getValCourante());
                courantEnSortie.setText(Double.toString(modeProd.getCourantEnSortie().getValCourante()));
                break;
            case "%" :
                modeProd.getPuissanceFournie().setValCourante(Double.parseDouble(valeurCourante));
                jaugePuissanceFournie.setValue(modeProd.getPuissanceFournie().getValCourante());
                puissanceFournie.setText(Double.toString(modeProd.getPuissanceFournie().getValCourante()));
                break;
            case "!" :
                modeProd.getEnergieProduite().setValCourante(Double.parseDouble(valeurCourante));
                jaugeEnergieProduite.setProgress((float) modeProd.getEnergieProduite().pourcentageRempliDeLaJauge());
                energieProduite.setText(BoiteAOutils.obtenirEcritureScientifiqueAvecChiffresSignificatifs(3,modeProd.getEnergieProduite().getValCourante()));
                break;
            case "(" :
                modeProd.getTemperatureAlternateur().setValCourante(Double.parseDouble(valeurCourante));
                changerMinMaxDuThermometreAlternateurCarValeurCouranteTropBasseOuTropElevee();
                thermometreAlternateur.setValueAndStartAnim((float) modeProd.getTemperatureAlternateur().getValCourante());
                temperatureAlternateur.setText(Double.toString(modeProd.getTemperatureAlternateur().getValCourante()));
                break;
            case ")" :
                modeProd.getTemperatureFrein().setValCourante(Double.parseDouble(valeurCourante));
                changerMinMaxDuThermometreFreinCarValeurCouranteTropBasseOuTropElevee();
                thermometreFrein.setValueAndStartAnim((float) modeProd.getTemperatureFrein().getValCourante());
                temperatureFrein.setText(Double.toString(modeProd.getTemperatureFrein().getValCourante()));
                break;
            default:
                break;
        }

        switch (modeProd.getEtatModeProduction()) {
            case jaugeVitesseRotation:
                valeurMoyenne.setText(getResources().getString(R.string.valeurVitesseMoyenne)+modeProd.getVitesseRotation().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurVitesseMax)+modeProd.getVitesseRotation().getValMaxCourante());
                break;
            case jaugeTensionEnEntree:
                valeurMoyenne.setText(getResources().getString(R.string.valeurTensionMoyenne)+modeProd.getTensionEnEntree().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurTensionMax)+modeProd.getTensionEnEntree().getValMaxCourante());
                break;
            case jaugeCourantEnEntree:
                valeurMoyenne.setText(getResources().getString(R.string.valeurCourantMoyen)+modeProd.getCourantEnEntree().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurCourantMax)+modeProd.getCourantEnEntree().getValMaxCourante());
                break;
            case jaugePuissanceFournie:
                valeurMoyenne.setText(getResources().getString(R.string.valeurPuissanceMoyenne)+modeProd.getPuissanceFournie().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurPuissanceMax)+modeProd.getPuissanceFournie().getValMaxCourante());
                break;
            case jaugeEnergieProduite:
                valeurEnergie.setText(getResources().getString(R.string.valeurEnergieProduite)+BoiteAOutils.obtenirEcritureScientifiqueAvecChiffresSignificatifs(3,modeProd.getEnergieProduite().getValCourante()));
                break;
            case jaugeTemperatureAlternateur:
                valeurMoyenne.setText(getResources().getString(R.string.valeurTemperatureMoyenne)+modeProd.getTemperatureAlternateur().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurTemperatureMax)+modeProd.getTemperatureAlternateur().getValMaxCourante());
                break;
            case jaugeTemperatureFrein:
                valeurMoyenne.setText(getResources().getString(R.string.valeurTemperatureMoyenne)+modeProd.getTemperatureFrein().getValMoyenne());
                valeurMax.setText(getResources().getString(R.string.valeurTemperatureMax)+modeProd.getTemperatureFrein().getValMaxCourante());
                break;
            default:
                break;
        }
    }

    public void supprimerJaugeAfficheeActuellement(){
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
            case jaugeTensionEnSortie:
                jaugeTensionEnSortie.setVisibility(View.INVISIBLE);
                break;
            case jaugeCourantEnSortie:
                jaugeCourantEnSortie.setVisibility(View.INVISIBLE);
                break;
            case jaugePuissanceFournie:
                jaugePuissanceFournie.setVisibility(View.INVISIBLE);
                break;
            case jaugeEnergieProduite:
                jaugeEnergieProduite.setVisibility(View.INVISIBLE);
                echelleLogarithmique.setVisibility(View.INVISIBLE);
                cacheDuGrapheDeLeEchelleLogarithmique.setVisibility(View.INVISIBLE);
                valeurEnergie.setVisibility(View.INVISIBLE);
                valeurMoyenne.setVisibility(View.VISIBLE);
                valeurMax.setVisibility(View.VISIBLE);
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
    }

    public void afficherNouvelleJauge(){
        switch (modeProd.getEtatModeProduction()){
            case jaugeVitesseRotation:
                jaugeVitesseRotation.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeVitesseRotation);
                valeurMoyenne.setText(getResources().getString(R.string.valeurVitesseMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurVitesseMax));
                break;
            case jaugeTensionEnEntree:
                jaugeTensionEnEntree.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTensionEnEntree);
                valeurMoyenne.setText(getResources().getString(R.string.valeurTensionMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurTensionMax));
                break;
            case jaugeCourantEnEntree:
                jaugeCourantEnEntree.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeCourantEnEntree);
                valeurMoyenne.setText(getResources().getString(R.string.valeurCourantMoyen));
                valeurMax.setText(getResources().getString(R.string.valeurCourantMax));
                break;
            case jaugeTensionEnSortie:
                jaugeTensionEnSortie.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTensionEnSortie);
                valeurMoyenne.setText(getResources().getString(R.string.valeurTensionMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurTensionMax));
                break;
            case jaugeCourantEnSortie:
                jaugeCourantEnSortie.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeCourantEnSortie);
                valeurMoyenne.setText(getResources().getString(R.string.valeurCourantMoyen));
                valeurMax.setText(getResources().getString(R.string.valeurCourantMax));
                break;
            case jaugePuissanceFournie:
                jaugePuissanceFournie.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugePuissanceFournie);
                valeurMoyenne.setText(getResources().getString(R.string.valeurPuissanceMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurPuissanceMoyenne));
                break;
            case jaugeEnergieProduite:
                valeurMoyenne.setVisibility(View.INVISIBLE);
                valeurMax.setVisibility(View.INVISIBLE);
                valeurEnergie.setVisibility(View.VISIBLE);
                jaugeEnergieProduite.setVisibility(View.VISIBLE);
                echelleLogarithmique.setVisibility(View.VISIBLE);
                cacheDuGrapheDeLeEchelleLogarithmique.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeEnergieProduite);
                break;
            case jaugeTemperatureAlternateur:
                thermometreAlternateur.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTemperatureAlternateur);
                valeurMoyenne.setText(getResources().getString(R.string.valeurTemperatureMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurTemperatureMax));
                break;
            case jaugeTemperatureFrein:
                thermometreFrein.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeTemperatureFrein);
                valeurMoyenne.setText(getResources().getString(R.string.valeurTemperatureMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurTemperatureMax));
                break;
            default:
                break;
        }

    }

    public void changerMinMaxDesJauges() {
        jaugeVitesseRotation.setMinValue(modeProd.getVitesseRotation().getValMinJauge());
        jaugeVitesseRotation.setMaxValue(modeProd.getVitesseRotation().getValMaxJauge());
        jaugeTensionEnEntree.setMinValue(modeProd.getTensionEnEntree().getValMinJauge());
        jaugeTensionEnEntree.setMaxValue(modeProd.getTensionEnEntree().getValMaxJauge());
        jaugeCourantEnEntree.setMinValue(modeProd.getCourantEnEntree().getValMinJauge());
        jaugeCourantEnEntree.setMaxValue(modeProd.getCourantEnEntree().getValMaxJauge());
        jaugeTensionEnSortie.setMinValue(modeProd.getTensionEnSortie().getValMinJauge());
        jaugeTensionEnSortie.setMaxValue(modeProd.getTensionEnSortie().getValMaxJauge());
        jaugeCourantEnSortie.setMinValue(modeProd.getCourantEnSortie().getValMinJauge());
        jaugeCourantEnSortie.setMaxValue(modeProd.getCourantEnSortie().getValMaxJauge());
        jaugePuissanceFournie.setMinValue(modeProd.getPuissanceFournie().getValMinJauge());
        jaugePuissanceFournie.setMaxValue(modeProd.getPuissanceFournie().getValMaxJauge());
        jaugeEnergieProduite.setMax(100);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(echelleLogarithmique);
        staticLabelsFormatter.setHorizontalLabels(modeProd.getEnergieProduite().echelleLogarithmique());
        staticLabelsFormatter.setVerticalLabels(new String[] {"1",""});
        echelleLogarithmique.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        parametrerEtAfficherThermometreAlternateur();
        parametrerEtAfficherThermometreFrein();
        changerEcartsEntreValeursDesJaugesCirculaires();
    }

    public void changerEcartsEntreValeursDesJaugesCirculaires(){
        jaugeVitesseRotation.setMajorTicks((modeProd.getVitesseRotation().getValMaxJauge()-modeProd.getVitesseRotation().getValMinJauge())/10);
        jaugeVitesseRotation.setMinorTicks((modeProd.getVitesseRotation().getValMaxJauge()-modeProd.getVitesseRotation().getValMinJauge())/10/4);
        jaugeTensionEnEntree.setMajorTicks((modeProd.getTensionEnEntree().getValMaxJauge()-modeProd.getTensionEnEntree().getValMinJauge())/10);
        jaugeTensionEnEntree.setMinorTicks((modeProd.getTensionEnEntree().getValMaxJauge()-modeProd.getTensionEnEntree().getValMinJauge())/10/4);
        jaugeCourantEnEntree.setMajorTicks((modeProd.getCourantEnEntree().getValMaxJauge()-modeProd.getCourantEnEntree().getValMinJauge())/10);
        jaugeCourantEnEntree.setMinorTicks((modeProd.getCourantEnEntree().getValMaxJauge()-modeProd.getCourantEnEntree().getValMinJauge())/10/4);
        jaugeTensionEnSortie.setMajorTicks((modeProd.getTensionEnSortie().getValMaxJauge()-modeProd.getTensionEnSortie().getValMinJauge())/10);
        jaugeTensionEnSortie.setMinorTicks((modeProd.getTensionEnSortie().getValMaxJauge()-modeProd.getTensionEnSortie().getValMinJauge())/10/4);
        jaugeCourantEnSortie.setMajorTicks((modeProd.getCourantEnSortie().getValMaxJauge()-modeProd.getCourantEnSortie().getValMinJauge())/10);
        jaugeCourantEnSortie.setMinorTicks((modeProd.getCourantEnSortie().getValMaxJauge()-modeProd.getCourantEnSortie().getValMinJauge())/10/4);
        jaugePuissanceFournie.setMajorTicks((modeProd.getPuissanceFournie().getValMaxJauge()-modeProd.getPuissanceFournie().getValMinJauge())/10);
        jaugePuissanceFournie.setMinorTicks((modeProd.getPuissanceFournie().getValMaxJauge()-modeProd.getPuissanceFournie  ().getValMinJauge())/10/4);
    }

    public void changerMinMaxDuThermometreAlternateurCarValeurCouranteTropBasseOuTropElevee(){
        if(modeProd.getTemperatureAlternateur().getValCourante()-modeProd.getTemperatureAlternateur().getValMinJauge()<5){
            do {
                modeProd.getTemperatureAlternateur().setValMinJauge(modeProd.getTemperatureAlternateur().getValMinJauge()-5);
                modeProd.getTemperatureAlternateur().setValMaxJauge(modeProd.getTemperatureAlternateur().getValMaxJauge()-5);

            }while(modeProd.getTemperatureAlternateur().getValCourante()-modeProd.getTemperatureAlternateur().getValMinJauge()<5);
            parametrerEtAfficherThermometreAlternateur();
        }
        else if(modeProd.getTemperatureAlternateur().getValMaxJauge()-modeProd.getTemperatureAlternateur().getValCourante()<5){
            do {
                modeProd.getTemperatureAlternateur().setValMinJauge(modeProd.getTemperatureAlternateur().getValMinJauge()+5);
                modeProd.getTemperatureAlternateur().setValMaxJauge(modeProd.getTemperatureAlternateur().getValMaxJauge()+5);
            }while(modeProd.getTemperatureAlternateur().getValMaxJauge()-modeProd.getTemperatureAlternateur().getValCourante()<5);
            parametrerEtAfficherThermometreAlternateur();
        }
    }

    public void changerMinMaxDuThermometreFreinCarValeurCouranteTropBasseOuTropElevee(){
        if(modeProd.getTemperatureFrein().getValCourante()-modeProd.getTemperatureFrein().getValMinJauge()<5){
            do {
                modeProd.getTemperatureFrein().setValMinJauge(modeProd.getTemperatureFrein().getValMinJauge()-5);
                modeProd.getTemperatureFrein().setValMaxJauge(modeProd.getTemperatureFrein().getValMaxJauge()-5);

            }while(modeProd.getTemperatureFrein().getValCourante()-modeProd.getTemperatureFrein().getValMinJauge()<5);
            parametrerEtAfficherThermometreFrein();
        }
        else if(modeProd.getTemperatureFrein().getValMaxJauge()-modeProd.getTemperatureFrein().getValCourante()<5){
            do {
                modeProd.getTemperatureFrein().setValMinJauge(modeProd.getTemperatureFrein().getValMinJauge()+5);
                modeProd.getTemperatureFrein().setValMaxJauge(modeProd.getTemperatureFrein().getValMaxJauge()+5);
            }while(modeProd.getTemperatureFrein().getValMaxJauge()-modeProd.getTemperatureFrein().getValCourante()<5);
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
