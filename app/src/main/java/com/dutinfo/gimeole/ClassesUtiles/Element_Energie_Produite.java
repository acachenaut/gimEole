package com.dutinfo.gimeole.ClassesUtiles;

import android.util.Log;

import java.text.DecimalFormat;

public class Element_Energie_Produite extends Element_A_Afficher {

    private double valRAZ;

    public Element_Energie_Produite(double valMinJauge, double valMaxJauge){
        super(valMinJauge, valMaxJauge);
        this.valRAZ=0;
    }

    public void setValCourante(double valCourante) {
        this.valCourante = valCourante-this.valRAZ;
        if (this.getValMaxCourante()<this.getValCourante()){
            setValMaxCourante(this.getValCourante());
        }
    }

    public void setValRAZ(double valRAZ) {
        this.valRAZ += valRAZ;
    }

    public double getValRAZ() {
        return valRAZ;
    }

    public String[] echelleLogarithmique(){
        String[] echelle = new String[5];
        echelle[0]="";
        double valeurQuartEchelle, valeurMoitieEchelle, valeurTroisQuartsEchelle;
        valeurQuartEchelle = Math.pow(getValMaxJauge(),0.25);
        valeurMoitieEchelle = Math.pow(getValMaxJauge(),0.5);
        valeurTroisQuartsEchelle = Math.pow(getValMaxJauge(),0.75);
        echelle[1]= BoiteAOutils.obtenirEcritureScientifiqueAvecChiffresSignificatifs(2,valeurQuartEchelle);
        echelle[2]= BoiteAOutils.obtenirEcritureScientifiqueAvecChiffresSignificatifs(2,valeurMoitieEchelle);
        echelle[3]= BoiteAOutils.obtenirEcritureScientifiqueAvecChiffresSignificatifs(2,valeurTroisQuartsEchelle);
        echelle[4]= (getValMaxJauge()>10000000 ? Double.toString(getValMaxJauge()) : BoiteAOutils.obtenirEcritureScientifiqueAvecChiffresSignificatifs(2,getValMaxJauge()));

        return echelle;
    }

    public double pourcentageRempliDeLaJauge(){
        Double pourcentage=Math.log10(getValCourante())/Math.log10(getValMaxJauge())*100;
        return pourcentage;
    }


}


