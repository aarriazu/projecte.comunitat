package com.comunitat.projecte.comunitat.model;

public class Despesa {
    private String id;
    private String descripcio;
    private double importTotal;
    private String codiZona;

    public Despesa(String id, String descripcio, double importTotal, String codiZona) {
        this.id = id;
        this.descripcio = descripcio;
        this.importTotal = importTotal;
        this.codiZona = codiZona;
    }

    public String getId() { return id; }
    public String getDescripcio() { return descripcio; }
    public double getImportTotal() { return importTotal; }
    public String getCodiZona() { return codiZona; }
}