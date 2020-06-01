package com.dutinfo.gimeole.ClassesUtiles;

/**
 * Boite à outils est une classe regroupant un ensemble de fonctions utiles au fonctionnement de l'application GimEole.
 * Elle permet principalement de traiter des nombres afin de les arrondir ou de les écrire sous une forme différente.
 */
public class BoiteAOutils {

    /**
     * Fonction permettant d'btenir l'écriture scientifique d'un nombre en choisissant le nombre de chiffres après la virgule.
     * Les puissance de 10 sont représenté par le carctère E suivi du nombre de la puissance.
     *
     * @param nombreDeChiffresApresLaVirgule le nombre chiffres après la vrigule qu'il faut obtenir
     * @param nombreATransfromer            le nombre à traiter
     * @return Retourne le nombre sous forme d'une chaîne de carctère.
     */
    public static String obtenirEcritureScientifiqueEnFonctionDuNombreDeChiffresApresLaVirgule(int nombreDeChiffresApresLaVirgule, Double nombreATransfromer){
        int puissanceDe10 = 0;
        boolean estPuissanceNegative = false, estChiffreNegatif = false;
        String chiffreAAfficher = "";
        if (!nombreATransfromer.equals(0.0) && !nombreATransfromer.equals(-0.0) ){
            if (nombreATransfromer.compareTo(0.0)<0){
                estChiffreNegatif=true;
                nombreATransfromer=Math.abs(nombreATransfromer);
            }
            if (nombreATransfromer.compareTo(10.0)>0){
                do{
                    nombreATransfromer=nombreATransfromer/10;
                    puissanceDe10++;
                }while (nombreATransfromer.compareTo(10.0)>0);
            }
            else if (nombreATransfromer.compareTo(1.0)<0){
                do {
                    nombreATransfromer=nombreATransfromer*10;
                    puissanceDe10++;
                }while (nombreATransfromer.compareTo(1.0)<0);
                estPuissanceNegative = true;
            }
            if (estChiffreNegatif){
                chiffreAAfficher+="-";
            }
            chiffreAAfficher += String.valueOf(arrondirNombreEnFonctionDuNombreDeChiffresApresLaVrigule(nombreDeChiffresApresLaVirgule,nombreATransfromer));
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

    /**
     * Fonction permettant d'arrondir un nombre en choisissant le nombre de chiffres significatifs qu'il doit contenir.
     *
     * @param nombreDeChiffresSignificatifs le nombre de chiffres significatifs
     * @param nombreATransformer           le nombre à traiter
     * @return Retourne le nombre sous forme d'une chaîne de carctère.
     */
    public static String arrondirNombreEnFonctionDuNombreDeChiffresSignificatifs(int nombreDeChiffresSignificatifs, Double nombreATransformer){

        double valeur = nombreATransformer;
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

    /**
     * Fonction permettant d'arrondir un nombre en choisissant le nombre de chiffres après la virgule.
     *
     * @param nombreDeChiffresApresLaVirgule le nombre de chiffres après la virgule
     * @param nombreATransformer            le nombre à traiter
     * @return Retourne le nombre sous forme décimale.
     */
    public static double arrondirNombreEnFonctionDuNombreDeChiffresApresLaVrigule(int nombreDeChiffresApresLaVirgule, double nombreATransformer){
        double valeur = nombreATransformer;
        valeur = valeur*Math.pow(10,nombreDeChiffresApresLaVirgule);
        valeur = Math.round(valeur);
        valeur  = valeur/Math.pow(10,nombreDeChiffresApresLaVirgule);
        return valeur;
    }


}
