package com.comunitat.projecte.comunitat.controller;

import com.comunitat.projecte.comunitat.service.ComunidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ComunidadController {

    private final ComunidadService comunidadService;

    @Autowired
    public ComunidadController(ComunidadService comunidadService) {
        this.comunidadService = comunidadService;
    }

    public void procesarComunidad(String rutaComunidad, String rutaDespeses) throws IOException {
        // 1. Leer y parsear los archivos
        comunidadService.leerYParsearArchivos(rutaComunidad, rutaDespeses);

        // 2. Calcular repartos
        comunidadService.calcularRepartos();

        // 3. Generar archivos de salida
        comunidadService.generarArchivosSalida();
    }
}
