package com.comunitat.projecte.comunitat.model;

import java.util.ArrayList;
import java.util.List;

public class Propietat {
    private String tipus;       // P (Pis), L (Local), G (Garatge)
    private String codi;        // Código único de la propiedad (ej: "1-A")
    private int metresQuadrats; // Metros cuadrados
    private String propietari;  // Código del propietario
    private String zonesPercentatge;  // Zona i el percentatge
    private String caracteristica1; // Depende del tipo (empresa para Local, orientación para Pis, etc.)
    private String caracteristica2; // Depende del tipo (sector para Local, planta para Pis, etc.)
    //private List<PercentatgeZona> percentatgesZones = new ArrayList<>();

    // Constructor completo
    public Propietat(String tipus, String codi, int metresQuadrats,
                     String propietari, String zonesPercentatge, String caracteristica1, String caracteristica2) {
        this.tipus = tipus;
        this.codi = codi;
        this.metresQuadrats = metresQuadrats;
        this.propietari = propietari;
        this.zonesPercentatge = zonesPercentatge;
        this.caracteristica1 = caracteristica1;
        this.caracteristica2 = caracteristica2;
    }

    // Getters y Setters
    public String getTipus() {
        return tipus;
    }

    public String getCodi() {
        return codi;
    }

    public int getMetresQuadrats() {
        return metresQuadrats;
    }

    public String getPropietari() {
        return propietari;
    }

    public String getZonesPercentatge() {
        return zonesPercentatge;
    }

    public String getCaracteristica1() {
        return caracteristica1;
    }

    public String getCaracteristica2() {
        return caracteristica2;
    }

    /* 
    public List<PercentatgeZona> getPercentatgesZones() {
        return percentatgesZones;
    }

    public void addPercentatgeZona(PercentatgeZona percentatgeZona) {
        this.percentatgesZones.add(percentatgeZona);
    }
    */

    @Override
    public String toString() {
        return "Propietat{" +
                "tipus='" + tipus + '\'' +
                ", codi='" + codi + '\'' +
                ", metresQuadrats=" + metresQuadrats +
                ", propietari='" + propietari + '\'' +
                ", caracteristica1='" + caracteristica1 + '\'' +
                ", caracteristica2='" + caracteristica2 + '\'' +
                '}';
    }
}