package com.dutinfo.gimeole.ClassesUtiles;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Hérite de la classe ModeProduction.
 *  * Elle permet de contenir toutes les données à afficher/traiter dans le mode réglage de l'application (ModeReglageActivity.java).
 */
public class ModeReglage extends ModeProduction {

    private ArrayList<Point> pointsDuProfilAppli;
    private ArrayList<Point> pointsDuProfilConv;
    private int nombreDePointsDuProfilAppli;
    private int pointSelectionne;
    private double maxAbscisseDuGraphique;
    private double maxOrdonneeDuGraphique;
    private double maxAbscisseDuProfilAppli;
    private double maxOrdonneeDuProfilAppli;
    private double maxAbscisseDuProfilConv;
    private double maxOrdonneeDuProfilConv;
    private double maxAbscisseEquationGenere;
    private double maxOrdonneeEquationGenere;
    private double abscisseDuPointDeFonctionnement;
    private double ordonneeDuPointDeFonctionnement;
    private int courantEnEntreeReglageManuelUnite;
    private int courantEnEntreeReglageManuelDixieme;


    /**
     * Instantiates a new Mode reglage.
     */
    public ModeReglage(){
        super();
        this.nombreDePointsDuProfilAppli = 1;
        this.pointsDuProfilAppli = new ArrayList<>();
        this.pointsDuProfilAppli.add(new Point(0,0));
        this.pointSelectionne = 0;
        this.maxAbscisseDuProfilAppli = 0;
        this.maxOrdonneeDuProfilAppli = 0;
        this.maxAbscisseDuProfilConv = 0;
        this.maxOrdonneeDuProfilConv = 0;
        this.maxAbscisseEquationGenere = 0;
        this.maxOrdonneeEquationGenere = 0;
        this.courantEnEntreeReglageManuelUnite = 20;
        this.courantEnEntreeReglageManuelDixieme = 0;
        this.pointsDuProfilConv = new ArrayList<>();
        this.pointsDuProfilConv.add(new Point(0,0));


    }

    /**
     * Ajouter un point au profil appli et trier l'ArrayList dans l'ordre croissant des abscisses.
     *
     * @param abscisse the abscisse
     * @param ordonnee the ordonnee
     * @return Retourne un booléen afin de savoir si le point a été ajouté.
     */
    public boolean ajouterUnPointAuProfilAppliEtTrierArrayList(double abscisse, double ordonnee){
        if(this.getNombreDePointsDuProfilAppli()<11){
            pointsDuProfilAppli.add(new Point(abscisse,ordonnee));
            this.setNombreDePointsDuProfilAppli(this.getNombreDePointsDuProfilAppli()+1);
            Collections.sort(this.getPointsDuProfilAppli());
            modifierMaxAbscisseEtOrdonneeDuProfilAppliEnFonctionDuNouveauPoint(abscisse,ordonnee);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Ajouter un point au profil conv et trier l'ArrayList dans l'ordre croissant des abscisses.
     *
     * @param abscisse the abscisse
     * @param ordonnee the ordonnee
     */
    public void ajouterUnPointAuProfilConvEtTrierArrayList(double abscisse, double ordonnee){
            pointsDuProfilConv.add(new Point(abscisse,ordonnee));
            Collections.sort(this.getPointsDuProfilAppli());
            modifierMaxAbscisseEtOrdonneeDuProfilConvEnFonctionDuNouveauPoint(abscisse,ordonnee);
    }

    /**
     * Adapter max abscisse du graphique.
     */
    public void adapterMaxAbscisseDuGraphique(){
        if (this.getMaxAbscisseDuProfilAppli()>this.getAbscisseDuPointDeFonctionnement()){
            if(this.getMaxAbscisseDuProfilAppli()>this.getMaxAbscisseEquationGenere()){
                if (this.getMaxAbscisseDuProfilAppli()>this.getMaxAbscisseDuProfilConv()){
                    this.setMaxAbscisseDuGraphique(this.getMaxAbscisseDuProfilAppli());
                }
                else {
                    this.setMaxAbscisseDuGraphique(this.getMaxAbscisseDuProfilConv());
                }
            }
            else {
                if (this.getMaxAbscisseEquationGenere()>this.getMaxAbscisseDuProfilConv()){
                    this.setMaxAbscisseDuGraphique(this.getMaxAbscisseEquationGenere());
                }
                else{
                    this.setMaxAbscisseDuGraphique(this.getMaxAbscisseDuProfilConv());
                }
            }
        }
        else{
            if(this.getAbscisseDuPointDeFonctionnement()>this.getMaxAbscisseEquationGenere()){
                if(this.getAbscisseDuPointDeFonctionnement()>this.getMaxAbscisseDuProfilConv()){
                    this.setMaxAbscisseDuGraphique(this.getAbscisseDuPointDeFonctionnement());
                }
                else {
                    this.setMaxAbscisseDuGraphique(this.getMaxAbscisseDuProfilConv());
                }
            }
            else {
                if (this.getMaxAbscisseEquationGenere()>this.getMaxAbscisseDuProfilConv()){
                    this.setMaxAbscisseDuGraphique(this.getMaxAbscisseEquationGenere());
                }
                else{
                    this.setMaxAbscisseDuGraphique(this.getMaxAbscisseDuProfilConv());
                }

            }
        }
    }

    /**
     * Adapter max ordonnee du graphique.
     */
    public void adapterMaxOrdonneeDuGraphique(){
        if (this.getMaxOrdonneeDuProfilAppli()>this.getOrdonneeDuPointDeFonctionnement()){
            if(this.getMaxOrdonneeDuProfilAppli()>this.getMaxOrdonneeEquationGenere()){
                if (this.getMaxOrdonneeDuProfilAppli()>this.getMaxOrdonneeDuProfilConv()){
                    this.setMaxOrdonneeDuGraphique(this.getMaxOrdonneeDuProfilAppli());
                }
                else{
                    this.setMaxOrdonneeDuGraphique(this.getMaxOrdonneeDuProfilConv());
                }
            }
            else {
                if (this.getMaxOrdonneeEquationGenere()>this.getMaxOrdonneeDuProfilConv()){
                    this.setMaxOrdonneeDuGraphique(this.getMaxOrdonneeEquationGenere());
                }
                else {
                    this.setMaxOrdonneeDuGraphique(this.getMaxOrdonneeDuProfilConv());
                }
            }
        }
        else{
            if(this.getOrdonneeDuPointDeFonctionnement()>this.getMaxOrdonneeEquationGenere()){
                if (this.getOrdonneeDuPointDeFonctionnement()>this.getMaxOrdonneeDuProfilConv()){
                    this.setMaxOrdonneeDuGraphique(this.getOrdonneeDuPointDeFonctionnement());
                }
                else {
                    this.setMaxOrdonneeDuGraphique(this.getMaxOrdonneeDuProfilConv());
                }
            }
            else {
                if (this.getMaxOrdonneeEquationGenere()>this.getMaxOrdonneeDuProfilConv()){
                    this.setMaxOrdonneeDuGraphique(this.getMaxOrdonneeEquationGenere());
                }
                else {
                    this.setMaxOrdonneeDuGraphique(this.getMaxOrdonneeDuProfilConv());
                }
            }
        }
    }

    /**
     * Modifier max abscisse et ordonnee du profil appli en fonction du nouveau point.
     *
     * @param abscisse the abscisse
     * @param ordonnee the ordonnee
     */
    public void modifierMaxAbscisseEtOrdonneeDuProfilAppliEnFonctionDuNouveauPoint(double abscisse, double ordonnee){
        if(this.getMaxAbscisseDuProfilAppli()<abscisse){
            this.setMaxAbscisseDuProfilAppli(abscisse);
        }
        if(this.getMaxOrdonneeDuProfilAppli()<ordonnee){
            this.setMaxOrdonneeDuProfilAppli(ordonnee);
        }
    }

    /**
     * Modifier max abscisse et ordonnee du profil conv en fonction du nouveau point.
     *
     * @param abscisse the abscisse
     * @param ordonnee the ordonnee
     */
    public void modifierMaxAbscisseEtOrdonneeDuProfilConvEnFonctionDuNouveauPoint(double abscisse, double ordonnee){
        if(this.getMaxAbscisseDuProfilConv()<abscisse){
            this.setMaxAbscisseDuProfilConv(abscisse);
        }
        if(this.getMaxOrdonneeDuProfilConv()<ordonnee){
            this.setMaxOrdonneeDuProfilConv(ordonnee);
        }
    }

    /**
     * Modifier max abscisse et ordonnee de l equation genere en fonction du nouveau point.
     *
     * @param abscisse the abscisse
     * @param ordonnee the ordonnee
     */
    public void modifierMaxAbscisseEtOrdonneeDeLEquationGenereEnFonctionDuNouveauPoint(double abscisse, double ordonnee){
        if(this.getMaxAbscisseEquationGenere()<abscisse){
            this.setMaxAbscisseEquationGenere(abscisse);
        }
        if(this.getMaxOrdonneeEquationGenere()<ordonnee){
            this.setMaxOrdonneeEquationGenere(ordonnee);
        }
    }

    /**
     * Reinitialiser max abscisse et ordonnee de l equation genere.
     */
    public void reinitialiserMaxAbscisseEtOrdonneeDeLEquationGenere(){
        this.setMaxAbscisseEquationGenere(0);
        this.setMaxOrdonneeEquationGenere(0);
    }

    /**
     * Modifier point du profil appli.
     *
     * @param abscisse        the abscisse
     * @param ordonnee        the ordonnee
     * @param positionDuPoint the position du point
     */
    public void modifierPointDuProfilAppli(double abscisse, double ordonnee, int positionDuPoint){
        this.getPointsDuProfilAppli().get(positionDuPoint).setAbscisse(abscisse);
        this.getPointsDuProfilAppli().get(positionDuPoint).setOrdonnee(ordonnee);
        modifierMaxAbscisseEtOrdonneeDuProfilAppliEnFonctionDuNouveauPoint(abscisse,ordonnee);
        Collections.sort(this.getPointsDuProfilAppli());
        int i = -1;
        for(Point pointCourant : pointsDuProfilAppli){
            i++;
            if(pointCourant.getAbscisse()==abscisse && pointCourant.getOrdonnee()==ordonnee){
                this.setPointSelectionne(i);
            }
        }

    }

    /**
     * Sets nombre de points du profil appli.
     *
     * @param nombreDePointsDuProfilAppli the nombre de points du profil appli
     */
    public void setNombreDePointsDuProfilAppli(int nombreDePointsDuProfilAppli) {
        this.nombreDePointsDuProfilAppli = nombreDePointsDuProfilAppli;
    }

    /**
     * Gets nombre de points du profil appli.
     *
     * @return the nombre de points du profil appli
     */
    public int getNombreDePointsDuProfilAppli() {
        return nombreDePointsDuProfilAppli;
    }

    /**
     * Gets points du profil appli.
     *
     * @return the points du profil appli
     */
    public ArrayList<Point> getPointsDuProfilAppli() {
        return pointsDuProfilAppli;
    }

    /**
     * Sets point selectionne.
     *
     * @param pointSelectionne the point selectionne
     */
    public void setPointSelectionne(int pointSelectionne) {
        this.pointSelectionne = pointSelectionne;
    }

    /**
     * Gets point selectionne.
     *
     * @return the point selectionne
     */
    public int getPointSelectionne() {
        return pointSelectionne;
    }

    /**
     * Supprimer point selectionne.
     *
     * @param positionDuPoint the position du point
     */
    public void supprimerPointSelectionne(int positionDuPoint){
        if(this.getPointsDuProfilAppli().get(positionDuPoint).getAbscisse()== maxAbscisseDuProfilAppli || this.getPointsDuProfilAppli().get(positionDuPoint).getOrdonnee()== maxOrdonneeDuProfilAppli){
            this.getPointsDuProfilAppli().remove(positionDuPoint);
            this.setMaxAbscisseDuProfilAppli(0);
            this.setMaxOrdonneeDuProfilAppli(0);
            for(Point pointCourant : pointsDuProfilAppli){
                modifierMaxAbscisseEtOrdonneeDuProfilAppliEnFonctionDuNouveauPoint(pointCourant.getAbscisse(),pointCourant.getOrdonnee());
            }
        }
        else {
            this.getPointsDuProfilAppli().remove(positionDuPoint);
        }
        Collections.sort(this.getPointsDuProfilAppli());
        this.setPointSelectionne(0);
        this.setNombreDePointsDuProfilAppli(this.getNombreDePointsDuProfilAppli()-1);
        if (this.getNombreDePointsDuProfilAppli()==0){
            this.setMaxAbscisseDuProfilAppli(0);
            this.setMaxOrdonneeDuProfilAppli(0);
        }
    }

    /**
     * Supprimer profil appli.
     */
    public void supprimerProfilAppli(){
        this.getPointsDuProfilAppli().clear();
        this.setNombreDePointsDuProfilAppli(0);
        this.setMaxAbscisseDuProfilAppli(0);
        this.setMaxOrdonneeDuProfilAppli(0);
        this.ajouterUnPointAuProfilAppliEtTrierArrayList(0,0);
    }

    /**
     * Supprimer profil conv.
     */
    public void supprimerProfilConv(){
        this.getPointsDuProfilConv().clear();
        this.setMaxAbscisseDuProfilConv(0);
        this.setMaxOrdonneeDuProfilConv(0);
        this.getPointsDuProfilConv().add(new Point(0,0));
    }

    /**
     * Sets max abscisse du profil appli.
     *
     * @param maxAbscisseDuProfilAppli the max abscisse du profil appli
     */
    public void setMaxAbscisseDuProfilAppli(double maxAbscisseDuProfilAppli) {
        this.maxAbscisseDuProfilAppli = maxAbscisseDuProfilAppli;
    }

    /**
     * Gets max abscisse du profil appli.
     *
     * @return the max abscisse du profil appli
     */
    public double getMaxAbscisseDuProfilAppli() {
        return maxAbscisseDuProfilAppli;
    }

    /**
     * Sets max ordonnee du profil appli.
     *
     * @param maxOrdonneeDuProfilAppli the max ordonnee du profil appli
     */
    public void setMaxOrdonneeDuProfilAppli(double maxOrdonneeDuProfilAppli) {
        this.maxOrdonneeDuProfilAppli = maxOrdonneeDuProfilAppli;
    }

    /**
     * Gets max ordonnee du profil appli.
     *
     * @return the max ordonnee du profil appli
     */
    public double getMaxOrdonneeDuProfilAppli() {
        return maxOrdonneeDuProfilAppli;
    }

    /**
     * Gets abscisse du point de fonctionnement.
     *
     * @return the abscisse du point de fonctionnement
     */
    public double getAbscisseDuPointDeFonctionnement() {
        return abscisseDuPointDeFonctionnement;
    }

    /**
     * Sets abscisse du point de fonctionnement.
     *
     * @param abscisseDuPointDeFonctionnement the abscisse du point de fonctionnement
     */
    public void setAbscisseDuPointDeFonctionnement(double abscisseDuPointDeFonctionnement) {
        this.abscisseDuPointDeFonctionnement = abscisseDuPointDeFonctionnement;
    }

    /**
     * Gets ordonnee du point de fonctionnement.
     *
     * @return the ordonnee du point de fonctionnement
     */
    public double getOrdonneeDuPointDeFonctionnement() {
        return ordonneeDuPointDeFonctionnement;
    }

    /**
     * Sets ordonnee du point de fonctionnement.
     *
     * @param ordonneeDuPointDeFonctionnement the ordonnee du point de fonctionnement
     */
    public void setOrdonneeDuPointDeFonctionnement(double ordonneeDuPointDeFonctionnement) {
        this.ordonneeDuPointDeFonctionnement = ordonneeDuPointDeFonctionnement;
    }

    /**
     * Gets max abscisse du graphique.
     *
     * @return the max abscisse du graphique
     */
    public double getMaxAbscisseDuGraphique() {
        adapterMaxAbscisseDuGraphique();
        return maxAbscisseDuGraphique;
    }

    /**
     * Sets max abscisse du graphique.
     *
     * @param maxAbscisseDuGraphique the max abscisse du graphique
     */
    public void setMaxAbscisseDuGraphique(double maxAbscisseDuGraphique) {
        this.maxAbscisseDuGraphique = maxAbscisseDuGraphique;
    }

    /**
     * Gets max ordonnee du graphique.
     *
     * @return the max ordonnee du graphique
     */
    public double getMaxOrdonneeDuGraphique() {
        adapterMaxOrdonneeDuGraphique();
        return maxOrdonneeDuGraphique;
    }

    /**
     * Sets max ordonnee du graphique.
     *
     * @param maxOrdonneeDuGraphique the max ordonnee du graphique
     */
    public void setMaxOrdonneeDuGraphique(double maxOrdonneeDuGraphique) {
        this.maxOrdonneeDuGraphique = maxOrdonneeDuGraphique;
    }

    /**
     * Gets max abscisse equation genere.
     *
     * @return the max abscisse equation genere
     */
    public double getMaxAbscisseEquationGenere() {
        return maxAbscisseEquationGenere;
    }

    /**
     * Sets max abscisse equation genere.
     *
     * @param maxAbscisseEquationGenere the max abscisse equation genere
     */
    public void setMaxAbscisseEquationGenere(double maxAbscisseEquationGenere) {
        this.maxAbscisseEquationGenere = maxAbscisseEquationGenere;
    }

    /**
     * Gets max ordonnee equation genere.
     *
     * @return the max ordonnee equation genere
     */
    public double getMaxOrdonneeEquationGenere() {
        return maxOrdonneeEquationGenere;
    }

    /**
     * Sets max ordonnee equation genere.
     *
     * @param maxOrdonneeEquationGenere the max ordonnee equation genere
     */
    public void setMaxOrdonneeEquationGenere(double maxOrdonneeEquationGenere) {
        this.maxOrdonneeEquationGenere = maxOrdonneeEquationGenere;
    }

    /**
     * Gets max abscisse du profil conv.
     *
     * @return the max abscisse du profil conv
     */
    public double getMaxAbscisseDuProfilConv() {
        return maxAbscisseDuProfilConv;
    }

    /**
     * Sets max abscisse du profil conv.
     *
     * @param maxAbscisseDuProfilConv the max abscisse du profil conv
     */
    public void setMaxAbscisseDuProfilConv(double maxAbscisseDuProfilConv) {
        this.maxAbscisseDuProfilConv = maxAbscisseDuProfilConv;
    }

    /**
     * Gets max ordonnee du profil conv.
     *
     * @return the max ordonnee du profil conv
     */
    public double getMaxOrdonneeDuProfilConv() {
        return maxOrdonneeDuProfilConv;
    }

    /**
     * Sets max ordonnee du profil conv.
     *
     * @param maxOrdonneeDuProfilConv the max ordonnee du profil conv
     */
    public void setMaxOrdonneeDuProfilConv(double maxOrdonneeDuProfilConv) {
        this.maxOrdonneeDuProfilConv = maxOrdonneeDuProfilConv;
    }

    /**
     * Gets courant en entree reglage manuel unite.
     *
     * @return the courant en entree reglage manuel unite
     */
    public int getCourantEnEntreeReglageManuelUnite() {
        return courantEnEntreeReglageManuelUnite;
    }

    /**
     * Sets courant en entree reglage manuel unite.
     *
     * @param courantEnEntreeReglageManuelUnite the courant en entree reglage manuel unite
     */
    public void setCourantEnEntreeReglageManuelUnite(int courantEnEntreeReglageManuelUnite) {
        this.courantEnEntreeReglageManuelUnite = courantEnEntreeReglageManuelUnite;
    }

    /**
     * Gets courant en entree reglage manuel dixieme.
     *
     * @return the courant en entree reglage manuel dixieme
     */
    public int getCourantEnEntreeReglageManuelDixieme() {
        return courantEnEntreeReglageManuelDixieme;
    }

    /**
     * Sets courant en entree reglage manuel dixieme.
     *
     * @param courantEnEntreeReglageManuelDixieme the courant en entree reglage manuel dixieme
     */
    public void setCourantEnEntreeReglageManuelDixieme(int courantEnEntreeReglageManuelDixieme) {
        this.courantEnEntreeReglageManuelDixieme = courantEnEntreeReglageManuelDixieme;
    }

    /**
     * Gets points du profil conv.
     *
     * @return the points du profil conv
     */
    public ArrayList<Point> getPointsDuProfilConv() {
        return pointsDuProfilConv;
    }

    /**
     * Sets points du profil conv.
     *
     * @param pointsDuProfilConv the points du profil conv
     */
    public void setPointsDuProfilConv(ArrayList<Point> pointsDuProfilConv) {
        this.pointsDuProfilConv = pointsDuProfilConv;
    }

    /**
     * Sets points du profil appli.
     *
     * @param pointsDuProfilAppli the points du profil appli
     */
    public void setPointsDuProfilAppli(ArrayList<Point> pointsDuProfilAppli) {
        this.pointsDuProfilAppli = pointsDuProfilAppli;
    }

}
