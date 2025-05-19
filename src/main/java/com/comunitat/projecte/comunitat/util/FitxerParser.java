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
                if (line.isEmpty()) continue; // Ignorar líneas vacías
                if (line.startsWith("#")) {
                    section = line; // Cambiar de sección
                    continue;
                }
                if (line.startsWith(".")) continue; // Ignorar comentarios

                System.out.println("Procesando línea: " + line); // Depuración

                switch (section) {
                    case "#Comunitat":
                        String[] cd = line.split(";");
                        if (cd.length != 3) throw new IllegalArgumentException("Línea mal formada en #Comunitat: " + line);
                        comunitat.setId(cd[0]);
                        comunitat.setNom(cd[1]);
                        comunitat.setPoblacio(cd[2]);
                        break;

                    case "#Zona":
                        String[] z = line.split(";");
                        if (z.length != 3) throw new IllegalArgumentException("Línea mal formada en #Zona");
                        Zona zona = new Zona(z[0], z[1], z[2].charAt(0));
                        comunitat.getZones().add(zona);
                        mapaZones.put(z[0], zona);
                        break;

                    case "#Propietari":
                        String[] pr = line.split(";");
                        if (pr.length != 4) throw new IllegalArgumentException("Línea mal formada en #Propietari");
                        Propietari propietari = new Propietari(pr[0], pr[1], pr[2], pr[3]);
                        comunitat.getPropietaris().add(propietari);
                        mapaPropietaris.put(pr[0], propietari);
                        break;

                    case "#Propietat":
                        String[] parts = line.split(";");
                        if (parts.length < 7) throw new IllegalArgumentException("Línea mal formada en #Propietat");
                        String tipus = parts[0];
                        String codi = parts[1];
                        int m2 = Integer.parseInt(parts[2]);
                        String codiPropietari = parts[3];
                        String zonesStr = parts[4];
                        String caracteristica1 = parts[5];
                        String caracteristica2 = parts[6];

                        // Crear la propiedad con el constructor completo
                        Propietat propietat = new Propietat(
                                tipus,
                                codi,
                                m2,
                                codiPropietari,
                                caracteristica1,
                                caracteristica2
                        );

                        // Procesar las zonas y porcentajes
                        String[] zps = zonesStr.split(",");
                        for (String zp : zps) {
                            if (zp.contains("-")) {
                                String[] zpParts = zp.split("-");
                                String codiZona = zpParts[0];
                                int percent = Integer.parseInt(zpParts[1]);
                                Zona zonaAssignada = mapaZones.get(codiZona);
                                if (zonaAssignada == null) throw new IllegalArgumentException("Zona no encontrada: " + codiZona);
                                PercentatgeZona pz = new PercentatgeZona(zonaAssignada, percent);
                                propietat.getPercentatgesZones().add(pz);
                                zonaAssignada.getPercentatges().add(pz);
                            }
                        }
                        comunitat.getPropietats().add(propietat);
                        break;

                    default:
                        throw new IllegalArgumentException("Sección desconocida: " + section);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo comunitat.txt: " + e.getMessage(), e);
        }

        System.out.println("comunitat.txt procesado correctamente");
        return comunitat;
    }

    public static void parseDespeses(MultipartFile file, Comunitat comunitat) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || line.startsWith(".")) continue;

                System.out.println("Procesando línea de despeses: " + line);
                String[] parts = line.split(";");
                if (parts.length != 4) {
                    throw new IllegalArgumentException("Formato incorrecto en línea de despeses: " + line);
                }

                Despesa d = new Despesa(
                        parts[0],  // código
                        parts[1],  // descripción
                        Double.parseDouble(parts[2]),  // importe
                        parts[3]   // zona
                );
                comunitat.getDespeses().add(d);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo despeses.txt", e);
        }
        System.out.println("despeses.txt procesado correctamente");
    }
}