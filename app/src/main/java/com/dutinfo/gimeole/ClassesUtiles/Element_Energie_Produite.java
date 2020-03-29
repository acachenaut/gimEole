package com.dutinfo.gimeole.ClassesUtiles;

public class Element_Energie_Produite extends Element_A_Afficher {

    private double valRAZ;

    public Element_Energie_Produite(double valMinJauge, double valMaxJauge){
        super(valMinJauge, valMaxJauge);
        this.valRAZ=0;
    }

    public void setValCourante(double valCourante) {
        this.valCourante = valCourante-this.valRAZ;
        if (this.getValMaxCourante()<this.getValCourante()){
            setValMaxCourante(this.getValCourante());
        }
    }

    public void setValRAZ(double valRAZ) {
        this.valRAZ += valRAZ;
    }

    public double getValRAZ() {
        return valRAZ;
    }
}
