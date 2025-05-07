package com.comunitat.projecte.comunitat.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Propietat {
    protected String tipus;
    protected String codi;
    protected int metresQuadrats;
    protected Propietari propietari;
    protected List<PercentatgeZona> percentatgesZones = new ArrayList<>();

    public String getTipus() { return tipus; }
    public String getCodi() { return codi; }
    public int getMetresQuadrats() { return metresQuadrats; }
    public Propietari getPropietari() { return propietari; }
    public List<PercentatgeZona> getPercentatgesZones() { return percentatgesZones; }
}