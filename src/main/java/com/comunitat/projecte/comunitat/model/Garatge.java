package com.comunitat.projecte.comunitat.model;

public class Garatge extends Propietat {
    private String obertaTancada;
    private String teTraster;

    public Garatge(String codi, int m2, Propietari propietari, String obertaTancada, String teTraster) {
        this.tipus = "G";
        this.codi = codi;
        this.metresQuadrats = m2;
        this.propietari = propietari;
        this.obertaTancada = obertaTancada;
        this.teTraster = teTraster;
    }

    public String getObertaTancada() { return obertaTancada; }
    public String getTeTraster() { return teTraster; }
}
