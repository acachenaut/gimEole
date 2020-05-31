package com.dutinfo.gimeole.ClassesUtiles;

import java.text.DecimalFormat;

public class BoiteAOutils {

    public static String obtenirEcritureScientifiqueEnFonctionDuNombreDeChiffresApresLaVirgule(int nombreDeChiffresApresLaVirgule, Double chiffreATransfromer){
        int puissanceDe10 = 0;
        boolean estPuissanceNegative = false, estChiffreNegatif = false;
        String chiffreAAfficher = "";
        if (!chiffreATransfromer.equals(0.0) && !chiffreATransfromer.equals(-0.0) ){
            if (chiffreATransfromer.compareTo(0.0)<0){
                estChiffreNegatif=true;
                chiffreATransfromer=Math.abs(chiffreATransfromer);
            }
            if (chiffreATransfromer.compareTo(10.0)>0){
                do{
                    chiffreATransfromer=chiffreATransfromer/10;
                    puissanceDe10++;
                }while (chiffreATransfromer.compareTo(10.0)>0);
            }
            else if (chiffreATransfromer.compareTo(1.0)<0){
                do {
                    chiffreATransfromer=chiffreATransfromer*10;
                    puissanceDe10++;
                }while (chiffreATransfromer.compareTo(1.0)<0);
                estPuissanceNegative = true;
            }
            if (estChiffreNegatif){
                chiffreAAfficher+="-";
            }
            chiffreAAfficher += String.valueOf(arrondirChiffreEnFonctionDuNombreDeChiffresApresLaVrigule(nombreDeChiffresApresLaVirgule,chiffreATransfromer));
            chiffreAAfficher+="E";
            if (estPuissanceNegative){
                chiffreAAfficher+="-";
            }
            chiffreAAfficher+=puissanceDe10;
        }
        else{
            chiffreAAfficher = "0";
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

    public static double arrondirChiffreEnFonctionDuNombreDeChiffresApresLaVrigule (int nombreDeChiffresApresLaVirgule, double chiffreATransformer){
        double valeur = chiffreATransformer;
        valeur = valeur*Math.pow(10,nombreDeChiffresApresLaVirgule);
        valeur = Math.round(valeur);
        valeur  = valeur/Math.pow(10,nombreDeChiffresApresLaVirgule);
        return valeur;
    }


}
