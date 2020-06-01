package com.dutinfo.gimeole.ClassesUtiles;

/**
 * The type Point.
 */
public class Point implements Comparable<Point> {
    private double abscisse;
    private double ordonnee;

    /**
     * Instantiates a new Point.
     *
     * @param abscisse the abscisse
     * @param ordonnee the ordonnee
     */
    public Point (double abscisse, double ordonnee){
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    /**
     * Gets abscisse.
     *
     * @return the abscisse
     */
    public double getAbscisse() {
        return abscisse;
    }

    /**
     * Gets ordonnee.
     *
     * @return the ordonnee
     */
    public double getOrdonnee() {
        return ordonnee;
    }

    /**
     * Sets abscisse.
     *
     * @param abscisse the abscisse
     */
    public void setAbscisse(double abscisse) {
        this.abscisse = abscisse;
    }

    /**
     * Sets ordonnee.
     *
     * @param ordonnee the ordonnee
     */
    public void setOrdonnee(double ordonnee) {
        this.ordonnee = ordonnee;
    }

    @Override
    public int compareTo(Point o) {
        return Double.compare(abscisse, o.getAbscisse());//-1,0,1
    }
}
