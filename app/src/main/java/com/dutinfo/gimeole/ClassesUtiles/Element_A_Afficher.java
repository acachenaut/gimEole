package com.dutinfo.gimeole.ClassesUtiles;

/**
 * Element_A_Afficher est un type permettant de représenter les différentes valeurs à traiter qui sont envoyé par l'eolienne.
 */
public class Element_A_Afficher {

    /**
     * La valeur courante de l'élément.
     */
    public double valCourante;
    /**
     * La valeur maximum atteinte par l'élement.
     */
    private double valMaxCourante;
    /**
     * La valeur minimum de l'élement qui sert également de minimum pour la jauge.
     */
    private double valMinJauge;
    /**
     * La valeur maximum de l'élement qui sert également de maximum pour la jauge.
     */
    private double valMaxJauge;
    /**
     * Le nombre de valeur enregistré pour l'élément courant.
     */
    private int nbVal;
    /**
     * La somme de toutes les valeurs enregistrées pour l'élément courant.
     */
    private double sommeVal;

    /**
     * Constructeur de Element_a_afficher.
     *
     * @param valMinJauge la valeur minimum de l'élément
     * @param valMaxJauge la valeur maximum de l'élément
     */
    public Element_A_Afficher(double valMinJauge, double valMaxJauge){
        this.valMinJauge = valMinJauge;
        this.valMaxJauge = valMaxJauge;
        this.valCourante = 0;
        this.valMaxCourante = 0;
        this.nbVal = 0;
        this.sommeVal = 0;
    }

    /**
     * Gets val courante.
     *
     * @return the val courante
     */
    public double getValCourante() {
        return valCourante;
    }

    /**
     * Gets val max jauge.
     *
     * @return the val max jauge
     */
    public double getValMaxJauge() {
        return valMaxJauge;
    }

    /**
     * Gets val max courante.
     *
     * @return the val max courante
     */
    public double getValMaxCourante() {
        return valMaxCourante;
    }

    /**
     * Gets val min jauge.
     *
     * @return the val min jauge
     */
    public double getValMinJauge() {
        return valMinJauge;
    }


    /**
     * Sets val courante. Permet également de mettre à jour la somme des valeurs, le nombre de valeurs et la valeur maximum atteinte
     *
     * @param valCourante the val courante
     */
    public void setValCourante(double valCourante) {
        setSommeVal(valCourante);
        setNbVal(1);
        if (this.valMaxCourante<valCourante){
            setValMaxCourante(valCourante);
        }
        this.valCourante = valCourante;
    }

    /**
     * Sets val max jauge.
     *
     * @param valMaxJauge the val max jauge
     */
    public void setValMaxJauge(double valMaxJauge) {
        this.valMaxJauge = valMaxJauge;
    }

    /**
     * Sets val max courante.
     *
     * @param valMaxCourante the val max courante
     */
    public void setValMaxCourante(double valMaxCourante) {
        this.valMaxCourante = valMaxCourante;
    }

    /**
     * Sets val min jauge.
     *
     * @param valMinJauge the val min jauge
     */
    public void setValMinJauge(double valMinJauge) {
        this.valMinJauge = valMinJauge;
    }

    /**
     * Gets nb val.
     *
     * @return the nb val
     */
    public int getNbVal() {
        return nbVal;
    }

    /**
     * Gets somme val.
     *
     * @return the somme val
     */
    public double getSommeVal() {
        return sommeVal;
    }

    /**
     * Sets nb val.
     *
     * @param nbVal the nb val
     */
    public void setNbVal(int nbVal) {
        this.nbVal += nbVal;
    }

    /**
     * Sets somme val.
     *
     * @param sommeVal the somme val
     */
    public void setSommeVal(double sommeVal) {
        this.sommeVal += sommeVal;
    }

    /**
     * Get val moyenne double.
     *
     * @return the double
     */
    public double getValMoyenne(){
        double valMoyenne = this.getSommeVal()/this.getNbVal();
        valMoyenne = valMoyenne*100;
        valMoyenne = Math.round(valMoyenne);
        valMoyenne = valMoyenne/100;
        return valMoyenne;
    }


}
