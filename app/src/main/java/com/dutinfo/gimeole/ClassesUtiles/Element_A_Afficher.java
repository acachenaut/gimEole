package com.dutinfo.gimeole.ClassesUtiles;

public class Element_A_Afficher {

    public double valCourante;
    private double valMaxCourante;
    private double valMinJauge;
    private double valMaxJauge;
    private int nbVal;
    private double sommeVal;

    public Element_A_Afficher(double valMinJauge, double valMaxJauge){
        this.valMinJauge = valMinJauge;
        this.valMaxJauge = valMaxJauge;
        this.valCourante = 0;
        this.valMaxCourante = 0;
        this.nbVal = 0;
        this.sommeVal = 0;
    }
    public double getValCourante() {
        return valCourante;
    }

    public double getValMaxJauge() {
        return valMaxJauge;
    }

    public double getValMaxCourante() {
        return valMaxCourante;
    }

    public double getValMinJauge() {
        return valMinJauge;
    }


    public void setValCourante(double valCourante) {
        setSommeVal(valCourante);
        setNbVal(1);
        if (this.valMaxCourante<valCourante){
            setValMaxCourante(valCourante);
        }
        this.valCourante = valCourante;
    }

    public void setValMaxJauge(double valMaxJauge) {
        this.valMaxJauge = valMaxJauge;
    }

    public void setValMaxCourante(double valMaxCourante) {
        this.valMaxCourante = valMaxCourante;
    }

    public void setValMinJauge(double valMinJauge) {
        this.valMinJauge = valMinJauge;
    }

    public int getNbVal() {
        return nbVal;
    }

    public double getSommeVal() {
        return sommeVal;
    }

    public void setNbVal(int nbVal) {
        this.nbVal += nbVal;
    }

    public void setSommeVal(double sommeVal) {
        this.sommeVal += sommeVal;
    }

    public double getValMoyenne(){
        double valMoyenne = this.getSommeVal()/this.getNbVal();
        valMoyenne = valMoyenne*100;
        valMoyenne = Math.round(valMoyenne);
        valMoyenne = valMoyenne/100;
        return valMoyenne;
    }


}
