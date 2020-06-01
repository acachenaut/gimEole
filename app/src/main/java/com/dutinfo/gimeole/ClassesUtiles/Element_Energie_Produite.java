package com.dutinfo.gimeole.ClassesUtiles;


/**
 * Hérite de la classe Element_A_Afficher.
 * Elle permet de gérer la remise à zéro de la valeur qui ne concerne que l'énergie produite par l'éolienne et également l'échelle logarithmique de la jauge.
 */
public class Element_Energie_Produite extends Element_A_Afficher {

    /**
     * La valeur de remise à zéro contient la valeur de l'énergie produite lors de la remise à zéro. Cela permet de déduire cette valeur à la valeur courante envoyée par l'éolienne.
     */
    private double valRAZ;

    /**
     * Instantiates a new Element energie produite.
     *
     * @param valMinJauge the val min jauge
     * @param valMaxJauge the val max jauge
     */
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

    /**
     * Sets val raz.
     *
     * @param valRAZ the val raz
     */
    public void setValRAZ(double valRAZ) {
        this.valRAZ += valRAZ;
    }

    /**
     * Gets val raz.
     *
     * @return the val raz
     */
    public double getValRAZ() {
        return valRAZ;
    }

    /**
     * Fonction qui permet d'obtenir l'échelle logarithmique. Elle calcule l'indice à 25%, 50%, 75% et 100% de la jauge.
     *
     * @return the string [ ]
     */
    public String[] echelleLogarithmique(){
        String[] echelle = new String[5];
        echelle[0]="";
        double valeurQuartEchelle, valeurMoitieEchelle, valeurTroisQuartsEchelle;
        valeurQuartEchelle = Math.pow(getValMaxJauge(),0.25);
        valeurMoitieEchelle = Math.pow(getValMaxJauge(),0.5);
        valeurTroisQuartsEchelle = Math.pow(getValMaxJauge(),0.75);
        echelle[1]= BoiteAOutils.obtenirEcritureScientifiqueEnFonctionDuNombreDeChiffresApresLaVirgule(2,valeurQuartEchelle);
        echelle[2]= BoiteAOutils.obtenirEcritureScientifiqueEnFonctionDuNombreDeChiffresApresLaVirgule(2,valeurMoitieEchelle);
        echelle[3]= BoiteAOutils.obtenirEcritureScientifiqueEnFonctionDuNombreDeChiffresApresLaVirgule(2,valeurTroisQuartsEchelle);
        echelle[4]= (getValMaxJauge()>10000000 ? Double.toString(getValMaxJauge()) : BoiteAOutils.obtenirEcritureScientifiqueEnFonctionDuNombreDeChiffresApresLaVirgule(2,getValMaxJauge()));

        return echelle;
    }

    /**
     * Pourcentage rempli de la jauge double.
     *
     * @return the double
     */
    public double pourcentageRempliDeLaJauge(){
        Double pourcentage=Math.log10(getValCourante())/Math.log10(getValMaxJauge())*100;
        return pourcentage;
    }


}


