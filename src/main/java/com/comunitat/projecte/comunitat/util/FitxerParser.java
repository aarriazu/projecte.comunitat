// utils/FitxerParser.java
package com.comunitat.projecte.comunitat.util;

import com.comunitat.projecte.comunitat.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class FitxerParser {

    public static Comunitat parseComunitat(MultipartFile file) {
        Comunitat comunitat = new Comunitat();
        Map<String, Zona> mapaZones = new HashMap<>();
        Map<String, Propietari> mapaPropietaris = new HashMap<>();
        String section = "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith(".")) continue;
                if (line.startsWith("#")) {
                    section = line;
                    continue;
                }

                switch (section) {
                    case "#Comunitat":
                        String[] cd = line.split(";");
                        comunitat.setId(cd[0]);
                        comunitat.setNom(cd[1]);
                        comunitat.setPoblacio(cd[2]);
                        break;

                    case "#Zona":
                        String[] z = line.split(";");
                        Zona zona = new Zona(z[0], z[1], z[2].charAt(0));
                        comunitat.getZones().add(zona);
                        mapaZones.put(z[0], zona);
                        break;

                    case "#Propietari":
                        String[] pr = line.split(";");
                        Propietari propietari = new Propietari(pr[0], pr[1], pr[2], pr[3]);
                        comunitat.getPropietaris().add(propietari);
                        mapaPropietaris.put(pr[0], propietari);
                        break;

                    case "#Propietat":
                        String[] parts = line.split(";");
                        String tipus = parts[0];
                        String codi = parts[1];
                        int m2 = Integer.parseInt(parts[2]);
                        String codiPropietari = parts[3];
                        String zonesStr = parts[4];
                        Propietari prop = mapaPropietaris.get(codiPropietari);
                        Propietat propietat;

                        switch (tipus) {
                            case "P":
                                propietat = new Pis(codi, m2, prop, parts[5], Integer.parseInt(parts[6]));
                                break;
                            case "L":
                                propietat = new Local(codi, m2, prop, parts[5], parts[6]);
                                break;
                            case "G":
                                propietat = new Garatge(codi, m2, prop, parts[5], parts[6]);
                                break;
                            default:
                                throw new IllegalArgumentException("Tipus de propietat desconegut: " + tipus);
                        }

                        comunitat.getPropietats().add(propietat);
                        prop.getPropietats().add(propietat);

                        String[] zps = zonesStr.split(",");
                        for (String zp : zps) {
                            if (zp.contains("-")) {
                                String[] zpParts = zp.split("-");
                                String codiZona = zpParts[0];
                                int percent = Integer.parseInt(zpParts[1]);
                                Zona zonaAssignada = mapaZones.get(codiZona);
                                PercentatgeZona pz = new PercentatgeZona(zonaAssignada, percent);
                                propietat.getPercentatgesZones().add(pz);
                                zonaAssignada.getPercentatges().add(pz);
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error llegint comunitat.txt", e);
        }

        return comunitat;
    }

    public static void parseDespeses(MultipartFile file, Comunitat comunitat) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(";");
                Despesa d = new Despesa(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]);
                comunitat.getDespeses().add(d);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error llegint despeses.txt", e);
        }
    }
}