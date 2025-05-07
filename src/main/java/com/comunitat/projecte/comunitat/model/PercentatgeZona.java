package com.comunitat.projecte.comunitat.model;

public class PercentatgeZona {
    private Zona zona;
    private int percentatge;

    public PercentatgeZona(Zona zona, int percentatge) {
        this.zona = zona;
        this.percentatge = percentatge;
    }

    public Zona getZona() { return zona; }
    public int getPercentatge() { return percentatge; }
}