package com.dutinfo.gimeole;

public class ModeProduction {
    public enum UnEtat {jaugeVitesseRotation, jaugeTensionEnEntree, jaugeCourantEnEntree, jaugePuissanceFournie, jaugeEnergieProduite, jaugeTemperatureAlternateur, jaugeTemperatureFrein}
    private UnEtat etatModeProduction;
    private Element_A_Afficher vitesseRotation;
    private Element_A_Afficher tensionEnEntree;
    private Element_A_Afficher courantEnEntree;
    private Element_A_Afficher puissanceFournie;
    private Element_Energie_Produite energieProduite;
    private Element_A_Afficher temperatureAlternateur;
    private Element_A_Afficher temperatureFrein;

    public ModeProduction (){
         vitesseRotation = new Element_A_Afficher(0,3000);
         tensionEnEntree = new Element_A_Afficher(0,150);
         courantEnEntree = new Element_A_Afficher(0,20);
         puissanceFournie = new Element_A_Afficher(1000,2000);
         energieProduite = new Element_Energie_Produite(0,1000000000);
         temperatureAlternateur = new Element_A_Afficher(25,40);
         temperatureFrein = new Element_A_Afficher(25,40);
         this.etatModeProduction=UnEtat.jaugeVitesseRotation;

    }

    public UnEtat getEtatModeProduction() {
        return etatModeProduction;
    }

    public void setEtatModeProduction(UnEtat etatModeProduction) {
        this.etatModeProduction = etatModeProduction;
    }

    public Element_A_Afficher getCourantEnEntree() {
        return courantEnEntree;
    }

    public Element_Energie_Produite getEnergieProduite() {
        return energieProduite;
    }

    public Element_A_Afficher getPuissanceFournie() {
        return puissanceFournie;
    }

    public Element_A_Afficher getTemperatureAlternateur() {
        return temperatureAlternateur;
    }

    public Element_A_Afficher getTemperatureFrein() {
        return temperatureFrein;
    }

    public Element_A_Afficher getTensionEnEntree() {
        return tensionEnEntree;
    }

    public Element_A_Afficher getVitesseRotation() {
        return vitesseRotation;
    }

    public double[] getMinMaxDesJauges() {

        double[] minMax = new double[14];
        minMax[0]=this.getVitesseRotation().getValMinJauge();
        minMax[1]=this.getVitesseRotation().getValMaxJauge();
        minMax[2]=this.getTensionEnEntree().getValMinJauge();
        minMax[3]=this.getTensionEnEntree().getValMaxJauge();
        minMax[4]=this.getCourantEnEntree().getValMinJauge();
        minMax[5]=this.getCourantEnEntree().getValMaxJauge();
        minMax[6]=this.getPuissanceFournie().getValMinJauge();
        minMax[7]=this.getPuissanceFournie().getValMaxJauge();
        minMax[8]=this.getEnergieProduite().getValMinJauge();
        minMax[9]=this.getEnergieProduite().getValMaxJauge();
        minMax[10]=this.getTemperatureAlternateur().getValMinJauge();
        minMax[11]=this.getTemperatureAlternateur().getValMaxJauge();
        minMax[12]=this.getTemperatureFrein().getValMinJauge();
        minMax[13]=this.getTemperatureFrein().getValMaxJauge();

        return minMax;


    }

    public void setMinMaxDesJauges(double[] tab){
        this.getVitesseRotation().setValMinJauge(tab[0]);
        this.getVitesseRotation().setValMaxJauge(tab[1]);
        this.getTensionEnEntree().setValMinJauge(tab[2]);
        this.getTensionEnEntree().setValMaxJauge(tab[3]);
        this.getCourantEnEntree().setValMinJauge(tab[4]);
        this.getCourantEnEntree().setValMaxJauge(tab[5]);
        this.getPuissanceFournie().setValMinJauge(tab[6]);
        this.getPuissanceFournie().setValMaxJauge(tab[7]);
        this.getEnergieProduite().setValMinJauge(tab[8]);
        this.getEnergieProduite().setValMaxJauge(tab[9]);
        this.getTemperatureAlternateur().setValMinJauge(tab[10]);
        this.getTemperatureAlternateur().setValMaxJauge(tab[11]);
        this.getTemperatureFrein().setValMinJauge(tab[12]);
        this.getTemperatureFrein().setValMaxJauge(tab[13]);
    }

}
