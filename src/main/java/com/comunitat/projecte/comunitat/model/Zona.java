package com.comunitat.projecte.comunitat.model;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    private String codi;
    private String nom;
    private char tipusRepartiment; // 'P' o 'I'
    private List<PercentatgeZona> percentatges = new ArrayList<>();

    public Zona(String codi, String nom, char tipusRepartiment) {
        this.codi = codi;
        this.nom = nom;
        this.tipusRepartiment = tipusRepartiment;
    }

    public String getCodi() { return codi; }
    public String getNom() { return nom; }
    public char getTipusRepartiment() { return tipusRepartiment; }
    public List<PercentatgeZona> getPercentatges() { return percentatges; }
}