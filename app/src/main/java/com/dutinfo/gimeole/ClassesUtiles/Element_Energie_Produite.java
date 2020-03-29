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
        double quart, demi, troisQuarts;
        quart = Math.pow(getValMaxJauge(),0.25);
        demi = Math.pow(getValMaxJauge(),0.5);
        troisQuarts = Math.pow(getValMaxJauge(),0.75);
        echelle[1]= obtenirEcritureScientifiqueAvecDeuxChiffresSignificatifs(quart);
        echelle[2]= obtenirEcritureScientifiqueAvecDeuxChiffresSignificatifs(demi);
        echelle[3]= obtenirEcritureScientifiqueAvecDeuxChiffresSignificatifs(troisQuarts);
        echelle[4]= (getValMaxJauge()>10000000 ? Double.toString(getValMaxJauge()) : obtenirEcritureScientifiqueAvecDeuxChiffresSignificatifs(getValMaxJauge()));

        return echelle;
    }

    public double pourcentageRempliDeLaJauge(){
        Double pourcentage=Math.log10(getValCourante())/Math.log10(getValMaxJauge())*100;
        return pourcentage;
    }

    public String obtenirEcritureScientifiqueAvecDeuxChiffresSignificatifs(Double chiffreATransfromer){
        String chiffreAAfficher=Double.toString(chiffreATransfromer) ;
        int puissanceDe10 = 0;
        if (chiffreATransfromer<10000000){
            while (chiffreAAfficher.indexOf(".")>1){
                chiffreATransfromer=chiffreATransfromer/10;
                puissanceDe10++;
                chiffreAAfficher=Double.toString(chiffreATransfromer);
            }
            DecimalFormat df = new DecimalFormat("0.00");
            chiffreAAfficher=df.format(chiffreATransfromer);
            chiffreAAfficher+="E"+puissanceDe10;
        }
        else{
            chiffreAAfficher=chiffreAAfficher.substring(0,4)+chiffreAAfficher.substring(chiffreAAfficher.length()-3);
        }

        return chiffreAAfficher;
    }

}


