package com.dutinfo.gimeole.ClassesUtiles;

import java.util.ArrayList;
import java.util.Collections;

public class ModeReglage extends ModeProduction {

    private ArrayList<Point> pointsDuProfilAppli;
    private int nombreDePointsDuProfilAppli;
    private int pointSelectionne;
    private double maxAbscisseDuGraphique;
    private double maxOrdonneeDuGraphique;
    private double maxAbscisseDuProfilAppli;
    private double maxOrdonneeDuProfilAppli;
    private double abscisseDuPointDeFonctionnement;
    private double ordonneeDuPointDeFonctionnement;


    public ModeReglage(){
        super();
        this.nombreDePointsDuProfilAppli =0;
        this.pointsDuProfilAppli = new ArrayList<>();
        this.pointSelectionne=-1;
        this.maxAbscisseDuProfilAppli =0;
        this.maxOrdonneeDuProfilAppli =0;


    }

    public boolean ajouterUnPointEtTrierTableau(double abscisse, double ordonnee){
        if(this.getNombreDePointsDuProfilAppli()<10){
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

    public void adapterMaxAbscisseDuGraphique(){
        if (this.getMaxAbscisseDuProfilAppli()>this.getAbscisseDuPointDeFonctionnement()){
            this.setMaxAbscisseDuGraphique(this.getMaxAbscisseDuProfilAppli());
        }
        else{
            this.setMaxAbscisseDuGraphique(this.getAbscisseDuPointDeFonctionnement());
        }
    }

    public void adapterMaxOrdonneeDuGraphique(){
        if (this.getMaxOrdonneeDuProfilAppli()>this.getOrdonneeDuPointDeFonctionnement()){
            this.setMaxOrdonneeDuGraphique(this.getMaxOrdonneeDuProfilAppli());
        }
        else{
            this.setMaxOrdonneeDuGraphique(this.getOrdonneeDuPointDeFonctionnement());
        }
    }

    public void modifierMaxAbscisseEtOrdonneeDuProfilAppliEnFonctionDuNouveauPoint(double abscisse, double ordonnee){
        if(this.getMaxAbscisseDuProfilAppli()<abscisse){
            this.setMaxAbscisseDuProfilAppli(abscisse);
        }
        if(this.getMaxOrdonneeDuProfilAppli()<ordonnee){
            this.setMaxOrdonneeDuProfilAppli(ordonnee);
        }
    }

    public void setNombreDePointsDuProfilAppli(int nombreDePointsDuProfilAppli) {
        this.nombreDePointsDuProfilAppli = nombreDePointsDuProfilAppli;
    }

    public int getNombreDePointsDuProfilAppli() {
        return nombreDePointsDuProfilAppli;
    }

    public ArrayList<Point> getPointsDuProfilAppli() {
        return pointsDuProfilAppli;
    }

    public void setPointSelectionne(int pointSelectionne) {
        this.pointSelectionne = pointSelectionne;
    }

    public int getPointSelectionne() {
        return pointSelectionne;
    }

    public void supprimerPointSelectionne(int positionDuPoint){
        if(this.getPointsDuProfilAppli().get(positionDuPoint).getAbscisse()== maxAbscisseDuProfilAppli || this.getPointsDuProfilAppli().get(positionDuPoint).getOrdonnee()== maxOrdonneeDuProfilAppli){
            for(Point pointCourant : pointsDuProfilAppli){
                modifierMaxAbscisseEtOrdonneeDuProfilAppliEnFonctionDuNouveauPoint(pointCourant.getAbscisse(),pointCourant.getOrdonnee());
            }
        }
        this.getPointsDuProfilAppli().remove(positionDuPoint);
        Collections.sort(this.getPointsDuProfilAppli());
        this.setNombreDePointsDuProfilAppli(this.getNombreDePointsDuProfilAppli()-1);
        if (this.getNombreDePointsDuProfilAppli()==0){
            this.setMaxAbscisseDuProfilAppli(0);
            this.setMaxOrdonneeDuProfilAppli(0);
        }
    }

    public void setMaxAbscisseDuProfilAppli(double maxAbscisseDuProfilAppli) {
        this.maxAbscisseDuProfilAppli = maxAbscisseDuProfilAppli;
    }

    public double getMaxAbscisseDuProfilAppli() {
        return maxAbscisseDuProfilAppli;
    }

    public void setMaxOrdonneeDuProfilAppli(double maxOrdonneeDuProfilAppli) {
        this.maxOrdonneeDuProfilAppli = maxOrdonneeDuProfilAppli;
    }

    public double getMaxOrdonneeDuProfilAppli() {
        return maxOrdonneeDuProfilAppli;
    }

    public double getAbscisseDuPointDeFonctionnement() {
        return abscisseDuPointDeFonctionnement;
    }

    public void setAbscisseDuPointDeFonctionnement(double abscisseDuPointDeFonctionnement) {
        this.abscisseDuPointDeFonctionnement = abscisseDuPointDeFonctionnement;
    }

    public double getOrdonneeDuPointDeFonctionnement() {
        return ordonneeDuPointDeFonctionnement;
    }

    public void setOrdonneeDuPointDeFonctionnement(double ordonneeDuPointDeFonctionnement) {
        this.ordonneeDuPointDeFonctionnement = ordonneeDuPointDeFonctionnement;
    }

    public double getMaxAbscisseDuGraphique() {
        adapterMaxAbscisseDuGraphique();
        return maxAbscisseDuGraphique;
    }

    public void setMaxAbscisseDuGraphique(double maxAbscisseDuGraphique) {
        this.maxAbscisseDuGraphique = maxAbscisseDuGraphique;
    }

    public double getMaxOrdonneeDuGraphique() {
        adapterMaxOrdonneeDuGraphique();
        return maxOrdonneeDuGraphique;
    }

    public void setMaxOrdonneeDuGraphique(double maxOrdonneeDuGraphique) {
        this.maxOrdonneeDuGraphique = maxOrdonneeDuGraphique;
    }
}
