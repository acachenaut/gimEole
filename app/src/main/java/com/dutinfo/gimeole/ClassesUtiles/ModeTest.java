package com.dutinfo.gimeole.ClassesUtiles;

import java.util.ArrayList;
import java.util.Collections;

public class ModeTest extends ModeProduction {

    private ArrayList<Point> pointsDuGraphique;
    private int nombreDePoints;

    public ModeTest (){
        super();
        this.nombreDePoints=0;
        pointsDuGraphique = new ArrayList<>();

    }

    public boolean ajouterUnPointEtTrierTableau(double abscisse, double ordonnee){
        if(this.getNombreDePoints()<10){
            pointsDuGraphique.add(new Point(abscisse,ordonnee));
            this.setNombreDePoints(this.getNombreDePoints()+1);
            Collections.sort(this.getPointsDuGraphique());
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
}
