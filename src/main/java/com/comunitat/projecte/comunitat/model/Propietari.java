package com.comunitat.projecte.comunitat.model;

import java.util.ArrayList;
import java.util.List;

public class Propietari {
    private String codi;
    private String nom;
    private String poblacio;
    private String email;
    private List<Propietat> propietats = new ArrayList<>();

    public Propietari(String codi, String nom, String poblacio, String email) {
        this.codi = codi;
        this.nom = nom;
        this.poblacio = poblacio;
        this.email = email;
    }

    public String getCodi() { return codi; }
    public String getNom() { return nom; }
    public String getPoblacio() { return poblacio; }
    public String getEmail() { return email; }
    public List<Propietat> getPropietats() { return propietats; }
}