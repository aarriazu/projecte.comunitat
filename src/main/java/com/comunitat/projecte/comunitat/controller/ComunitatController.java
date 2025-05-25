package com.comunitat.projecte.comunitat.controller;

import com.comunitat.projecte.comunitat.model.Comunitat;
import com.comunitat.projecte.comunitat.model.Despesa;
import com.comunitat.projecte.comunitat.model.Propietari;
import com.comunitat.projecte.comunitat.model.Propietat;
import com.comunitat.projecte.comunitat.service.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ComunitatController {
    @Autowired
    private ComunitatService comunitatService;

    @GetMapping("/")
    public String formulari() {
        return "formulari";
    }

    @PostMapping("/procesar")
    public String procesar(@RequestParam("comunitatFile") MultipartFile comunitatFile,
                           @RequestParam("gastosFile") MultipartFile gastosFile,
                           Model model) {
        try {
            if (comunitatFile.isEmpty()) {
                throw new IllegalArgumentException("El fitxer comunitat.txt està buit.");
            }
            comunitatService.processarFitxers(comunitatFile, gastosFile);
            return "redirect:/resum";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "formulari";
        }
    }

    @GetMapping("/resum")
    public String resum(Model model) {
        Comunitat c = comunitatService.getComunitat();

        if (c == null) {
            model.addAttribute("error", "Has d'importar els fitxers abans de veure el resum.");
            return "formulari"; // Asegúrate de que existe
        }

        model.addAttribute("comunitat", c);
        model.addAttribute("zones", c.getZones());
        model.addAttribute("propietats", c.getPropietats());
        model.addAttribute("propietaris", c.getPropietaris());
        model.addAttribute("despeses", c.getDespeses());

        System.out.println("Datos enviados al modelo: " + model);
        return "resum";
    }

    @GetMapping("/propietats")
    public String veurePropietats(Model model) {
        Comunitat c = comunitatService.getComunitat();
        if (c == null) {
            model.addAttribute("error", "Fitxers no carregats.");
            return "formulari";
        }
        model.addAttribute("propietats", c.getPropietats());
        Map<String, String> propietariNoms = c.getPropietaris().stream().collect(Collectors.toMap(Propietari::getCodi, Propietari::getNom));
        model.addAttribute("propietariNoms", propietariNoms);
        return "propietats";
    }

    @GetMapping("/propietaris")
    public String veurePropietaris(Model model) {
        Comunitat c = comunitatService.getComunitat();

        if (c == null) {
            model.addAttribute("error", "Fitxers no carregats.");
            return "formulari";
        }

        if (c.getPropietaris() == null || c.getPropietaris().isEmpty()) {
            model.addAttribute("error", "No hi ha propietaris disponibles.");
            return "formulari";
        }

        model.addAttribute("propietaris", c.getPropietaris());
        return "propietaris";
    }

    @GetMapping("/despeses")
    public String veureDespeses(Model model) {
        Comunitat c = comunitatService.getComunitat();
        if (c == null) {
            model.addAttribute("error", "Fitxers no carregats.");
            return "formulari";
        }
        model.addAttribute("despeses", c.getDespeses());
        Map<String, List<Despesa>> despesesPerZona = c.getDespeses().stream().collect(Collectors.groupingBy(Despesa::getCodiZona));
        model.addAttribute("despesesPerZona", despesesPerZona);
        Map<String, Double> totalsPerZona = new HashMap<>();
        for (Map.Entry<String, List<Despesa>> entry : despesesPerZona.entrySet()) {
            double total = entry.getValue().stream().mapToDouble(Despesa::getImportTotal).sum();
            totalsPerZona.put(entry.getKey(), total);
        }
        model.addAttribute("totalsPerZona", totalsPerZona);
        model.addAttribute("propietaris", c.getPropietaris());
        
        Map<String, Map<String, Double>> importesPropietat = new HashMap<>();

        for (Propietari p : c.getPropietaris()) {
            for (Propietat prop : p.getPropietats()) {
                Map<String, Double> importes = new LinkedHashMap<>();
                String zonesPercentatge = prop.getZonesPercentatge();
                if (zonesPercentatge != null && !zonesPercentatge.isEmpty()) {
                    String[] zps = zonesPercentatge.split(",");
                    for (String zp : zps) {
                        String[] parts = zp.split("-");
                        if (parts.length == 2) {
                            String zona = parts[0];
                            try {
                                double percent = Double.parseDouble(parts[1]);
                                Double totalZona = totalsPerZona.get(zona);
                                if (totalZona != null) {
                                    double importe = totalZona * percent / 100.0;
                                    importes.put(zona, importe);
                                }
                            } catch (NumberFormatException e) {
                                // Maneja el error si el porcentaje no es un número
                            }
                        }
                    }
                }
                importesPropietat.put(prop.getCodi(), importes);
            }
        }
        model.addAttribute("importesPropietat", importesPropietat);
        return "despeses";
    }


}
