package com.dutinfo.gimeole.ClassesUtiles;

import java.util.ArrayList;
import java.util.Collections;

public class ModeReglage extends ModeProduction {

    private ArrayList<Point> pointsDuProfilAppli;
    private int nombreDePoints;
    private int pointSelectionne;
    private double maxAbscisse;
    private double maxOrdonee;

    public ModeReglage(){
        super();
        this.nombreDePoints=0;
        this.pointsDuProfilAppli = new ArrayList<>();
        this.pointSelectionne=-1;
        this.maxAbscisse=0;
        this.maxOrdonee=0;


    }

    public boolean ajouterUnPointEtTrierTableau(double abscisse, double ordonnee){
        if(this.getNombreDePoints()<10){
            pointsDuProfilAppli.add(new Point(abscisse,ordonnee));
            this.setNombreDePoints(this.getNombreDePoints()+1);
            Collections.sort(this.getPointsDuProfilAppli());
            if(this.getMaxAbscisse()<abscisse){
                this.setMaxAbscisse(abscisse);
            }
            if(this.getMaxOrdonee()<ordonnee){
                this.setMaxOrdonee(ordonnee);
            }
            return true;
        }
        else{
            return false;
        }
    }

    public void setNombreDePoints(int nombreDePoints) {
        this.nombreDePoints = nombreDePoints;
    }

    public int getNombreDePoints() {
        return nombreDePoints;
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
        this.getPointsDuProfilAppli().remove(positionDuPoint);
        Collections.sort(this.getPointsDuProfilAppli());
        this.setNombreDePoints(this.getNombreDePoints()-1);
    }

    public void setMaxAbscisse(double maxAbscisse) {
        this.maxAbscisse = maxAbscisse;
    }

    public double getMaxAbscisse() {
        return maxAbscisse;
    }

    public void setMaxOrdonee(double maxOrdonee) {
        this.maxOrdonee = maxOrdonee;
    }

    public double getMaxOrdonee() {
        return maxOrdonee;
    }
}
