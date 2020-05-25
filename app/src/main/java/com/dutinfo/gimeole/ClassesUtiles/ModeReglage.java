package com.dutinfo.gimeole.ClassesUtiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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


    public ModeReglage(){
        super();
        this.nombreDePointsDuProfilAppli = 1;
        this.pointsDuProfilAppli = new ArrayList<>();
        this.pointsDuProfilAppli.add(new Point(0,0));
        this.pointsDuProfilConv = new ArrayList<>();
        this.pointsDuProfilConv.add(new Point(0,0));
        this.pointSelectionne = 0;
        this.maxAbscisseDuProfilAppli = 0;
        this.maxOrdonneeDuProfilAppli = 0;
        this.maxAbscisseDuProfilConv = 0;
        this.maxOrdonneeDuProfilConv = 0;
        this.maxAbscisseEquationGenere = 0;
        this.maxOrdonneeEquationGenere = 0;


    }

    public boolean ajouterUnPointAuProfilAppliEtTrierTableau(double abscisse, double ordonnee){
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

    public void ajouterUnPointAuProfilConvEtTrierTableau(double abscisse, double ordonnee, int positionDuPoint){
            pointsDuProfilConv.set(positionDuPoint,new Point(abscisse,ordonnee));
            modifierMaxAbscisseEtOrdonneeDuProfilConvEnFonctionDuNouveauPoint(abscisse,ordonnee);
    }

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

    public void modifierMaxAbscisseEtOrdonneeDuProfilAppliEnFonctionDuNouveauPoint(double abscisse, double ordonnee){
        if(this.getMaxAbscisseDuProfilAppli()<abscisse){
            this.setMaxAbscisseDuProfilAppli(abscisse);
        }
        if(this.getMaxOrdonneeDuProfilAppli()<ordonnee){
            this.setMaxOrdonneeDuProfilAppli(ordonnee);
        }
    }

    public void modifierMaxAbscisseEtOrdonneeDuProfilConvEnFonctionDuNouveauPoint(double abscisse, double ordonnee){
        if(this.getMaxAbscisseDuProfilConv()<abscisse){
            this.setMaxAbscisseDuProfilConv(abscisse);
        }
        if(this.getMaxOrdonneeDuProfilConv()<ordonnee){
            this.setMaxOrdonneeDuProfilConv(ordonnee);
        }
    }

    public void modifierMaxAbscisseEtOrdonneeDeLEquationGenereEnFonctionDuNouveauPoint(double abscisse, double ordonnee){
        if(this.getMaxAbscisseEquationGenere()<abscisse){
            this.setMaxAbscisseEquationGenere(abscisse);
        }
        if(this.getMaxOrdonneeEquationGenere()<ordonnee){
            this.setMaxOrdonneeEquationGenere(ordonnee);
        }
    }

    public void reinitialiserMaxAbscisseEtOrdonneeDeLEquationGenere(){
        this.setMaxAbscisseEquationGenere(0);
        this.setMaxOrdonneeEquationGenere(0);
    }

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

    public void supprimerProfilAppli(){
        this.getPointsDuProfilAppli().clear();
        this.setNombreDePointsDuProfilAppli(0);
        this.setMaxAbscisseDuProfilAppli(0);
        this.setMaxOrdonneeDuProfilAppli(0);
    }

    public void supprimerProfilConv(){
        this.getPointsDuProfilConv().clear();
        this.setMaxAbscisseDuProfilConv(0);
        this.setMaxOrdonneeDuProfilConv(0);
        this.getPointsDuProfilAppli().add(new Point(0,0));
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

    public double getMaxAbscisseEquationGenere() {
        return maxAbscisseEquationGenere;
    }

    public void setMaxAbscisseEquationGenere(double maxAbscisseEquationGenere) {
        this.maxAbscisseEquationGenere = maxAbscisseEquationGenere;
    }

    public double getMaxOrdonneeEquationGenere() {
        return maxOrdonneeEquationGenere;
    }

    public void setMaxOrdonneeEquationGenere(double maxOrdonneeEquationGenere) {
        this.maxOrdonneeEquationGenere = maxOrdonneeEquationGenere;
    }

    public ArrayList<Point> getPointsDuProfilConv() {
        return pointsDuProfilConv;
    }

    public void setPointsDuProfilConv(ArrayList<Point> pointsDuProfilConv) {
        this.pointsDuProfilConv = pointsDuProfilConv;
    }

    public double getMaxAbscisseDuProfilConv() {
        return maxAbscisseDuProfilConv;
    }

    public void setMaxAbscisseDuProfilConv(double maxAbscisseDuProfilConv) {
        this.maxAbscisseDuProfilConv = maxAbscisseDuProfilConv;
    }

    public double getMaxOrdonneeDuProfilConv() {
        return maxOrdonneeDuProfilConv;
    }

    public void setMaxOrdonneeDuProfilConv(double maxOrdonneeDuProfilConv) {
        this.maxOrdonneeDuProfilConv = maxOrdonneeDuProfilConv;
    }
}
