package com.dutinfo.gimeole.ClassesUtiles;

import java.util.ArrayList;


/**
 * ModeProduction est un type qui permet de contenir toutes les données à afficher/traiter dans le mode production de l'application (MainActivity.java).
 */
public class ModeProduction {


    /**
     * Enum qui permet de représenter l'état dans lequel se trouve le mode production. Les états changent en fonction de la jauge qui est affiché sur l'écran.
     */
    public enum UnEtat {
        /**
         * Jauge vitesse rotation un etat.
         */
        jaugeVitesseRotation,
        /**
         * Jauge tension en entree un etat.
         */
        jaugeTensionEnEntree,
        /**
         * Jauge courant en entree un etat.
         */
        jaugeCourantEnEntree,
        /**
         * Jauge tension en sortie un etat.
         */
        jaugeTensionEnSortie,
        /**
         * Jauge courant en sortie un etat.
         */
        jaugeCourantEnSortie,
        /**
         * Jauge puissance fournie un etat.
         */
        jaugePuissanceFournie,
        /**
         * Jauge energie produite un etat.
         */
        jaugeEnergieProduite,
        /**
         * Jauge temperature alternateur un etat.
         */
        jaugeTemperatureAlternateur,
        /**
         * Jauge temperature frein un etat.
         */
        jaugeTemperatureFrein}

    private UnEtat etatModeProduction;
    private Element_A_Afficher vitesseRotation;

    private Element_A_Afficher tensionEnEntree;

    private Element_A_Afficher courantEnEntree;

    private Element_A_Afficher tensionEnSortie;

    private Element_A_Afficher courantEnSortie;

    private Element_A_Afficher puissanceFournie;

    private Element_Energie_Produite energieProduite;

    private Element_A_Afficher temperatureAlternateur;

    private Element_A_Afficher temperatureFrein;
    /**
     * Contient la valeur du réglage du courant de freinage
     */
    private double courantDeFreinage;


    /**
     * Instantiates a new Mode production.
     */
    public ModeProduction (){
         vitesseRotation = new Element_A_Afficher(0,500);
         tensionEnEntree = new Element_A_Afficher(0,100);
         courantEnEntree = new Element_A_Afficher(0,20);
        tensionEnSortie = new Element_A_Afficher(0,30);
        courantEnSortie = new Element_A_Afficher(0,30);
         puissanceFournie = new Element_A_Afficher(0,500);
         energieProduite = new Element_Energie_Produite(0,100);
         temperatureAlternateur = new Element_A_Afficher(0,15);
         temperatureFrein = new Element_A_Afficher(0,15);
         this.etatModeProduction=UnEtat.jaugeVitesseRotation;
         this.courantDeFreinage=20;

    }

    /**
     * Gets etat mode production.
     *
     * @return the etat mode production
     */
    public UnEtat getEtatModeProduction() {
        return etatModeProduction;
    }

    /**
     * Sets etat mode production.
     *
     * @param etatModeProduction the etat mode production
     */
    public void setEtatModeProduction(UnEtat etatModeProduction) {
        this.etatModeProduction = etatModeProduction;
    }

    /**
     * Gets courant de freinage.
     *
     * @return the courant de freinage
     */
    public double getCourantDeFreinage() {
        return courantDeFreinage;
    }

    /**
     * Sets courant de freinage.
     *
     * @param courantDeFreinage the courant de freinage
     */
    public void setCourantDeFreinage(double courantDeFreinage) {
        this.courantDeFreinage = courantDeFreinage;
    }

    /**
     * Gets courant en entree.
     *
     * @return the courant en entree
     */
    public Element_A_Afficher getCourantEnEntree() {
        return courantEnEntree;
    }

    /**
     * Gets energie produite.
     *
     * @return the energie produite
     */
    public Element_Energie_Produite getEnergieProduite() {
        return energieProduite;
    }

    /**
     * Gets puissance fournie.
     *
     * @return the puissance fournie
     */
    public Element_A_Afficher getPuissanceFournie() {
        return puissanceFournie;
    }

    /**
     * Gets temperature alternateur.
     *
     * @return the temperature alternateur
     */
    public Element_A_Afficher getTemperatureAlternateur() {
        return temperatureAlternateur;
    }

    /**
     * Gets temperature frein.
     *
     * @return the temperature frein
     */
    public Element_A_Afficher getTemperatureFrein() {
        return temperatureFrein;
    }

    /**
     * Gets tension en entree.
     *
     * @return the tension en entree
     */
    public Element_A_Afficher getTensionEnEntree() {
        return tensionEnEntree;
    }

    /**
     * Gets courant en sortie.
     *
     * @return the courant en sortie
     */
    public Element_A_Afficher getCourantEnSortie() {
        return courantEnSortie;
    }

    /**
     * Gets tension en sortie.
     *
     * @return the tension en sortie
     */
    public Element_A_Afficher getTensionEnSortie() {
        return tensionEnSortie;
    }

    /**
     * Gets vitesse rotation.
     *
     * @return the vitesse rotation
     */
    public Element_A_Afficher getVitesseRotation() {
        return vitesseRotation;
    }

    /**
     * Fonction qui permet d'obtenir un tableau de double contenant les valeurs des minimums et maximums de chaque élément à afficher.
     * Les valeurs sont rangées par élément avec en premier le minimum et en second le maximum.
     * Cette fonction est utile pour le passage vers l'activté qui permet de régler le min/max des valeurs.
     *
     * @return the double [ ]
     */
    public double[] getMinMaxDesJauges() {

        double[] minMax = new double[18];
        minMax[0]=this.getVitesseRotation().getValMinJauge();
        minMax[1]=this.getVitesseRotation().getValMaxJauge();
        minMax[2]=this.getTensionEnEntree().getValMinJauge();
        minMax[3]=this.getTensionEnEntree().getValMaxJauge();
        minMax[4]=this.getCourantEnEntree().getValMinJauge();
        minMax[5]=this.getCourantEnEntree().getValMaxJauge();
        minMax[6]=this.getTensionEnSortie().getValMinJauge();
        minMax[7]=this.getTensionEnSortie().getValMaxJauge();
        minMax[8]=this.getCourantEnSortie().getValMinJauge();
        minMax[9]=this.getCourantEnSortie().getValMaxJauge();
        minMax[10]=this.getPuissanceFournie().getValMinJauge();
        minMax[11]=this.getPuissanceFournie().getValMaxJauge();
        minMax[12]=this.getEnergieProduite().getValMinJauge();
        minMax[13]=this.getEnergieProduite().getValMaxJauge();
        minMax[14]=this.getTemperatureAlternateur().getValMinJauge();
        minMax[15]=this.getTemperatureAlternateur().getValMaxJauge();
        minMax[16]=this.getTemperatureFrein().getValMinJauge();
        minMax[17]=this.getTemperatureFrein().getValMaxJauge();

        return minMax;


    }

    /**
     * Fonction qui permet de mettre à jour les valeurs des minimums et maximums de chaque élément à afficher à partir d'un tableau de double.
     * Le tableau doit être rangé par élément avec en premier le minimum et en second le maximum.
     *
     * @param tab the tab
     */
    public void setMinMaxDesJauges(double[] tab){
        this.getVitesseRotation().setValMinJauge(tab[0]);
        this.getVitesseRotation().setValMaxJauge(tab[1]);
        this.getTensionEnEntree().setValMinJauge(tab[2]);
        this.getTensionEnEntree().setValMaxJauge(tab[3]);
        this.getCourantEnEntree().setValMinJauge(tab[4]);
        this.getCourantEnEntree().setValMaxJauge(tab[5]);
        this.getTensionEnSortie().setValMinJauge(tab[6]);
        this.getTensionEnSortie().setValMaxJauge(tab[7]);
        this.getCourantEnSortie().setValMinJauge(tab[8]);
        this.getCourantEnSortie().setValMaxJauge(tab[9]);
        this.getPuissanceFournie().setValMinJauge(tab[10]);
        this.getPuissanceFournie().setValMaxJauge(tab[11]);
        this.getEnergieProduite().setValMinJauge(tab[12]);
        this.getEnergieProduite().setValMaxJauge(tab[13]);
        this.getTemperatureAlternateur().setValMinJauge(tab[14]);
        this.getTemperatureAlternateur().setValMaxJauge(tab[15]);
        this.getTemperatureFrein().setValMinJauge(tab[16]);
        this.getTemperatureFrein().setValMaxJauge(tab[17]);
    }









}
