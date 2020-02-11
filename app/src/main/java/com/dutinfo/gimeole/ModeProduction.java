package com.dutinfo.gimeole;

public class ModeProduction {
    public enum UnEtat {jaugeVitesseRotation, jaugeTensionEnEntree, jaugeCourantEnEntree, jaugePuissanceFournie, jaugeEnergieProduite, jaugeTemperatureAlternateur, jeugeTemperatureFrein}
    private UnEtat etatModeProduction;
    private Element_A_Afficher vitesseRotation;
    private Element_A_Afficher tensionEnEntree;
    private Element_A_Afficher courantEnEntree;
    private Element_A_Afficher puissanceFournie;
    private Element_A_Afficher energieProduite;
    private Element_A_Afficher temperatureAlternateur;
    private Element_A_Afficher temperatureFrein;

    public ModeProduction (){
         vitesseRotation = new Element_A_Afficher(0,50);
         tensionEnEntree = new Element_A_Afficher(0,50);
         courantEnEntree = new Element_A_Afficher(0,50);
         puissanceFournie = new Element_A_Afficher(0,50);
         energieProduite = new Element_A_Afficher(0,50);
         temperatureAlternateur = new Element_A_Afficher(0,50);
         temperatureFrein = new Element_A_Afficher(0,50);
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

    public Element_A_Afficher getEnergieProduite() {
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
}
