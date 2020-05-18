package com.dutinfo.gimeole.ClassesUtiles;

import java.util.ArrayList;
import java.util.Collections;

public class ModeReglage extends ModeProduction {

    private ArrayList<Point> pointsDuGraphique;
    private int nombreDePoints;
    private int pointSelectionne;
    private double maxAbscisse;
    private double maxOrdonee;

    public ModeReglage(){
        super();
        this.nombreDePoints=0;
        this.pointsDuGraphique = new ArrayList<>();
        this.pointSelectionne=-1;
        this.maxAbscisse=0;
        this.maxOrdonee=0;


    }

    public boolean ajouterUnPointEtTrierTableau(double abscisse, double ordonnee){
        if(this.getNombreDePoints()<10){
            pointsDuGraphique.add(new Point(abscisse,ordonnee));
            this.setNombreDePoints(this.getNombreDePoints()+1);
            Collections.sort(this.getPointsDuGraphique());
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

    public ArrayList<Point> getPointsDuGraphique() {
        return pointsDuGraphique;
    }

    public void setPointSelectionne(int pointSelectionne) {
        this.pointSelectionne = pointSelectionne;
    }

    public int getPointSelectionne() {
        return pointSelectionne;
    }

    public void supprimerPointSelectionne(int positionDuPoint){
        this.getPointsDuGraphique().remove(positionDuPoint);
        Collections.sort(this.getPointsDuGraphique());
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
