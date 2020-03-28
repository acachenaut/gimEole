package com.dutinfo.gimeole;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.biansemao.widget.ThermometerView;
import com.sccomponents.gauges.gr008.GR008;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.DeviceCallback;


public class MainActivity extends AppCompatActivity {

    //Création du modeProduction
    final ModeProduction modeProd = new ModeProduction();

    //Création des jauges
    GR008 jaugeVitesseRotation, jaugeTensionEnEntree, jaugeCourantEnEntree, jaugePuissanceFournie;
    RoundCornerProgressBar jaugeEnergieProduite;
    ThermometerView thermometreAlternateur, thermometreFrein;
    ThermometerView.ThermometerBuilder thermometreAlternateurType, thermometreFreinType;

    //Création valeurs à afficher
    TextView vitesseRotation, tensionEnEntree, courantEnEntree, puissanceFournie, energieProduite, temperatureAlternateur,temperatureFrein, nomJaugeCourante;

    //Création des valeurs moyennes et max à afficher
    TextView valeurMoyenne, valeurMax, valeurEnergie;

    //Création des noms des jauges à afficher
    String nomJaugeVitesseRotation,nomJaugeTensionEnEntree,nomJaugeCourantEnEntree,nomJaugeEnergieProduite,nomJaugePuissanceFournie,nomJaugeTemperatureAlternateur,nomJaugeTemperatureFrein;

    //Création de l'indicateur Bluetooth
    ImageView logoBluetooth;

    //Création des bouttons permettant de changer la jauge affiché
    Button boutonVitesseRotation,boutonTensionEnEntree,boutonCourantEnEntree,boutonPuissanceFournie,boutonEnergieProduite,boutonTemperatureAlternateur,boutonTemperatureFrein,boutonReglageJauges,boutonRAZenergie;

    //Nécessaire à la connexion Bluetooth
    private Bluetooth bluetooth;
    private BluetoothDevice device;


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

        //Lien entre les jauges de l'interface et de l'activté
        jaugeVitesseRotation = findViewById(R.id.jaugeVitesseRotation);
        jaugeTensionEnEntree = findViewById(R.id.jaugeTensionEnEntree);
        jaugeCourantEnEntree = findViewById(R.id.jaugeCourantEnEntree);
        jaugePuissanceFournie = findViewById(R.id.jaugePuissanceFournie);
        jaugeEnergieProduite = findViewById(R.id.jaugeEnergieProduite);
        parametrerEtAfficherThermometreAlternateur();
        parametrerEtAfficherThermometreFrein();

        //Nom des jauges
        nomJaugeVitesseRotation = getResources().getString(R.string.nomJaugeVitesseDeRotation);
        nomJaugeTensionEnEntree = getResources().getString(R.string.nomJaugeTensionEnEntree);
        nomJaugeCourantEnEntree = getResources().getString(R.string.nomJaugeCourantEnEntree);
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
        boutonPuissanceFournie = findViewById(R.id.boutonPuissanceFournie);
        boutonEnergieProduite = findViewById(R.id.boutonEnergieProduite);
        boutonTemperatureAlternateur = findViewById(R.id.boutonTemperatureAlternateur);
        boutonTemperatureFrein = findViewById(R.id.boutonTemperatureFrein);
        boutonReglageJauges = findViewById(R.id.boutonReglage);
        boutonRAZenergie = findViewById(R.id.boutonRAZenergie);

        //Lien entre le nom de la jauge courante de l'interface et l'activité
        nomJaugeCourante = findViewById(R.id.nomJaugeCourante);

        //Lien entre les valeurs courante de l'interface et l'activité
        vitesseRotation = findViewById(R.id.vitesseRotation);
        tensionEnEntree = findViewById(R.id.tensionEnEntree);
        courantEnEntree = findViewById(R.id.courantEnEntree);
        puissanceFournie = findViewById(R.id.puissanceFournie);
        energieProduite = findViewById(R.id.energieProduite);
        temperatureAlternateur = findViewById(R.id.temperatureAlternateur);
        temperatureFrein = findViewById(R.id.temperatureFrein);

        //Lien entre l'indicateur Bluetooth de l'interface et l'activité
        logoBluetooth = findViewById(R.id.logoBluetooth);

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
                Intent reglageActivity = new Intent(MainActivity.this, ReglageJauge.class);
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

    @Override
    protected void onStop() {
        super.onStop();
    }


    private DeviceCallback deviceCallback = new DeviceCallback() {
        @Override
        public void onDeviceConnected(BluetoothDevice device) {
            logoBluetooth.setImageResource(R.drawable.logobluetoohconnecte);
        }

        @Override
        public void onDeviceDisconnected(final BluetoothDevice device, String message) {
            logoBluetooth.setImageResource(R.drawable.logobluetoohdeconnecte);
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

    public void afficherValeur (String str){
        String premierCaractere = (str.substring(0,1));
        String valeurCourante = (str.substring(1));
        switch (premierCaractere){
            case "$" :
                modeProd.getVitesseRotation().setValCourante(Double.parseDouble(valeurCourante));
                jaugeVitesseRotation.setValue(Double.parseDouble(valeurCourante));
                vitesseRotation.setText(valeurCourante);

                break;
            case ":" :
                modeProd.getTensionEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                jaugeTensionEnEntree.setValue(Double.parseDouble(valeurCourante));
                tensionEnEntree.setText(valeurCourante);

                break;
            case ";" :
                modeProd.getCourantEnEntree().setValCourante(Double.parseDouble(valeurCourante));
                jaugeCourantEnEntree.setValue(Double.parseDouble(valeurCourante));
                courantEnEntree.setText(valeurCourante);

                break;
            case "%" :
                modeProd.getPuissanceFournie().setValCourante(Double.parseDouble(valeurCourante));
                jaugePuissanceFournie.setValue(Double.parseDouble(valeurCourante));
                puissanceFournie.setText(valeurCourante);

                break;
            case "!" :
                modeProd.getEnergieProduite().setValCourante(Double.parseDouble(valeurCourante));
                jaugeEnergieProduite.setProgress((float) modeProd.getEnergieProduite().getValCourante());
                /*int pos = valeurCourante.indexOf(".");
                //Code à modifier pour afficher les valeurs correctements
                if(pos >= 8){
                    energieProduite.setText(valeurCourante.substring(0,pos-6)+"M");
                }
                else if (pos>=5){
                    energieProduite.setText(valeurCourante.substring(0,pos-3)+"K"+valeurCourante.substring(pos-4,pos));
                }*/
                energieProduite.setText(Double.toString(modeProd.getEnergieProduite().getValCourante()));

                break;
            case "(" :
                modeProd.getTemperatureAlternateur().setValCourante(Double.parseDouble(valeurCourante));
                thermometreAlternateur.setValueAndStartAnim(Float.parseFloat(valeurCourante));
                temperatureAlternateur.setText(valeurCourante);

                break;
            case ")" :
                modeProd.getTemperatureFrein().setValCourante(Double.parseDouble(valeurCourante));
                thermometreFrein.setValueAndStartAnim(Float.parseFloat(valeurCourante));
                temperatureFrein.setText(valeurCourante);

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
                valeurEnergie.setText(getResources().getString(R.string.valeurEnergieProduite)+modeProd.getEnergieProduite().getValCourante());
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
            case jaugePuissanceFournie:
                jaugePuissanceFournie.setVisibility(View.INVISIBLE);
                break;
            case jaugeCourantEnEntree:
                jaugeCourantEnEntree.setVisibility(View.INVISIBLE);
                break;
            case jaugeEnergieProduite:
                jaugeEnergieProduite.setVisibility(View.INVISIBLE);
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
            case jaugePuissanceFournie:
                jaugePuissanceFournie.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugePuissanceFournie);
                valeurMoyenne.setText(getResources().getString(R.string.valeurPuissanceMoyenne));
                valeurMax.setText(getResources().getString(R.string.valeurPuissanceMoyenne));
                break;
            case jaugeCourantEnEntree:
                jaugeCourantEnEntree.setVisibility(View.VISIBLE);
                nomJaugeCourante.setText(nomJaugeCourantEnEntree);
                valeurMoyenne.setText(getResources().getString(R.string.valeurCourantMoyen));
                valeurMax.setText(getResources().getString(R.string.valeurCourantMax));
                break;
            case jaugeEnergieProduite:
                valeurMoyenne.setVisibility(View.INVISIBLE);
                valeurMax.setVisibility(View.INVISIBLE);
                valeurEnergie.setVisibility(View.VISIBLE);
                jaugeEnergieProduite.setVisibility(View.VISIBLE);
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
        jaugePuissanceFournie.setMinValue(modeProd.getPuissanceFournie().getValMinJauge());
        jaugePuissanceFournie.setMaxValue(modeProd.getPuissanceFournie().getValMaxJauge());
        jaugeEnergieProduite.setMax((float) modeProd.getEnergieProduite().getValMaxJauge());
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
        jaugePuissanceFournie.setMajorTicks((modeProd.getPuissanceFournie().getValMaxJauge()-modeProd.getPuissanceFournie().getValMinJauge())/10);
        jaugePuissanceFournie.setMinorTicks((modeProd.getPuissanceFournie().getValMaxJauge()-modeProd.getPuissanceFournie ().getValMinJauge())/10/4);
    }

    @Override
    public void onBackPressed() {
        //Permet de bloquer la flêche retour de l'appareil
    }

}
