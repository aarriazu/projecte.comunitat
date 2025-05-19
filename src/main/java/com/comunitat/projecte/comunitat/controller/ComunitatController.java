package com.comunitat.projecte.comunitat.controller;

import com.comunitat.projecte.comunitat.model.Comunitat;
import com.comunitat.projecte.comunitat.service.*;
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
            return "redirect:/resumen";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "formulari";
        }
    }

    @GetMapping("/resumen")
    public String resumen(Model model) {
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
        return "resumen";
    }

    @GetMapping("/propiedades")
    public String veurePropietats(Model model) {
        Comunitat c = comunitatService.getComunitat();
        if (c == null) {
            model.addAttribute("error", "Fitxers no carregats.");
            return "formulari";
        }
        model.addAttribute("propietats", c.getPropietats());
        return "propiedades";
    }

    @GetMapping("/propietarios")
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
        return "propietarios";
    }

    @GetMapping("/cuotas")
    public String veureDespeses(Model model) {
        Comunitat c = comunitatService.getComunitat();
        if (c == null) {
            model.addAttribute("error", "Fitxers no carregats.");
            return "formulari";
        }
        model.addAttribute("despeses", c.getDespeses());
        return "cuotas";
    }


}
