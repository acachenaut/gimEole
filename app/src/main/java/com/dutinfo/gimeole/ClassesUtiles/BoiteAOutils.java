package com.dutinfo.gimeole.ClassesUtiles;

import java.text.DecimalFormat;

public class BoiteAOutils {

    public static String obtenirEcritureScientifiqueAvecChiffresSignificatifs(int nombreDeChiffresApresLaVirgule, Double chiffreATransfromer){
        String chiffreAAfficher=Double.toString(chiffreATransfromer) ;
        int puissanceDe10 = 0;
        if (chiffreATransfromer<10000000){
            while (chiffreAAfficher.indexOf(".")>1){
                chiffreATransfromer=chiffreATransfromer/10;
                puissanceDe10++;
                chiffreAAfficher=Double.toString(chiffreATransfromer);
            }
            String nbDeChiffreSignificatif="";
            for (int i = 0 ; i<nombreDeChiffresApresLaVirgule;i++){
                nbDeChiffreSignificatif+="0";
            }
            DecimalFormat df = new DecimalFormat("0."+nbDeChiffreSignificatif);
            chiffreAAfficher=df.format(chiffreATransfromer);
            chiffreAAfficher+="E"+puissanceDe10;
        }
        else{
            chiffreAAfficher=chiffreAAfficher.substring(0,4)+chiffreAAfficher.substring(chiffreAAfficher.length()-3);
        }

        return chiffreAAfficher;
    }

    public static String arrondirChiffreEnFonctionDuNombreDeChiffresSignificatifs(int nombreDeChiffresSignificatifs, Double chiffreATransformer){

        double valeur = chiffreATransformer;
        int nombreDeChiffreAvantLaVirgule;
        boolean estNegatif = false;
        if (valeur<0){
            estNegatif=true;
            Math.abs(valeur);
        }
        if (valeur < 1){
            nombreDeChiffreAvantLaVirgule = 0;
        }
        else if ( valeur < 10){
            nombreDeChiffreAvantLaVirgule = 1;
        }
        else if ( valeur < 100 ){
            nombreDeChiffreAvantLaVirgule = 2;
        }
        else if ( valeur < 1000 ){
            nombreDeChiffreAvantLaVirgule = 3;
        }
        else{
            nombreDeChiffreAvantLaVirgule = 4;
        }

        if ( nombreDeChiffresSignificatifs-nombreDeChiffreAvantLaVirgule == 0 ){
            int chiffreFinal;
            chiffreFinal = (int) Math.round(valeur);
            String chiffreAAfficher= String.valueOf(chiffreFinal);
            return chiffreAAfficher;
        }
        else {
            Double chiffreFinal;
            chiffreFinal = Math.round(valeur*Math.pow(10,nombreDeChiffresSignificatifs-nombreDeChiffreAvantLaVirgule))/Math.pow(10,nombreDeChiffresSignificatifs-nombreDeChiffreAvantLaVirgule);
            if(estNegatif){
                chiffreFinal=-chiffreFinal;
            }
            String chiffreAAfficher=Double.toString(chiffreFinal) ;
            return chiffreAAfficher;
        }


    }


}
