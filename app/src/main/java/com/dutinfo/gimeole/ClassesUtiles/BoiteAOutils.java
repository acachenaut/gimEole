package com.dutinfo.gimeole.ClassesUtiles;

import java.text.DecimalFormat;

public class BoiteAOutils {

    public static String obtenirEcritureScientifiqueAvecChiffresSignificatifs(int nombreDeChiffreSignificatif, Double chiffreATransfromer){
        String chiffreAAfficher=Double.toString(chiffreATransfromer) ;
        int puissanceDe10 = 0;
        if (chiffreATransfromer<10000000){
            while (chiffreAAfficher.indexOf(".")>1){
                chiffreATransfromer=chiffreATransfromer/10;
                puissanceDe10++;
                chiffreAAfficher=Double.toString(chiffreATransfromer);
            }
            String nbDeChiffreSignificatif="";
            for (int i = 0 ; i<nombreDeChiffreSignificatif;i++){
                nbDeChiffreSignificatif+="0";
            }
            DecimalFormat df = new DecimalFormat("0"+nbDeChiffreSignificatif);
            chiffreAAfficher=df.format(chiffreATransfromer);
            chiffreAAfficher+="E"+puissanceDe10;
        }
        else{
            chiffreAAfficher=chiffreAAfficher.substring(0,4)+chiffreAAfficher.substring(chiffreAAfficher.length()-3);
        }

        return chiffreAAfficher;
    }
}
