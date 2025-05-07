package com.comunitat.projecte.comunitat.model;

import java.util.ArrayList;
import java.util.List;

public class Comunitat {
    private String id;
    private String nom;
    private String poblacio;

    private List<Zona> zones = new ArrayList<>();
    private List<Propietari> propietaris = new ArrayList<>();
    private List<Propietat> propietats = new ArrayList<>();
    private List<Despesa> despeses = new ArrayList<>();

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPoblacio() { return poblacio; }
    public void setPoblacio(String poblacio) { this.poblacio = poblacio; }
    public List<Zona> getZones() { return zones; }
    public List<Propietari> getPropietaris() { return propietaris; }
    public List<Propietat> getPropietats() { return propietats; }
    public List<Despesa> getDespeses() { return despeses; }
}