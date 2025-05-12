package com.comunitat.projecte.comunitat.model;

import java.util.ArrayList;
import java.util.List;

public class Propietat {
    protected String tipus;
    protected String codi;
    protected int metresQuadrats;
    protected String propietari;
    protected List<PercentatgeZona> percentatgesZones = new ArrayList<>();

    public Propietat(String codi, int m2, String codiPropietari, String part, String part1) {
    }

    public String getTipus() { return tipus; }
    public String getCodi() { return codi; }
    public int getMetresQuadrats() { return metresQuadrats; }
    public String getPropietari() { return propietari; }
    public List<PercentatgeZona> getPercentatgesZones() { return percentatgesZones; }
}