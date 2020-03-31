package com.dutinfo.gimeole.ClassesUtiles;

public class Point implements Comparable<Point> {
    private double abscisse;
    private double ordonnee;

    public Point (double abscisse, double ordonnee){
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    public double getAbscisse() {
        return abscisse;
    }

    public double getOrdonnee() {
        return ordonnee;
    }

    public void setAbscisse(double abscisse) {
        this.abscisse = abscisse;
    }

    public void setOrdonnee(double ordonnee) {
        this.ordonnee = ordonnee;
    }

    @Override
    public int compareTo(Point o) {
        return Double.compare(abscisse, o.getAbscisse());//-1,0,1
    }
}
