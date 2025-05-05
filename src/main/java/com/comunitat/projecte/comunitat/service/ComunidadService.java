package com.comunitat.projecte.comunitat.service;

import com.comunitat.model.dto.*;
import com.comunitat.model.entity.*;
import com.comunitat.util.FileParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComunidadService {

    private Comunidad comunidad;
    private List<Zona> zonas;
    private List<Propietat> propietats;
    private List<Propietari> propietaris;
    private List<Despesa> despeses;
    private Map<String, List<Quota>> quotesPerPropietat;
    private Map<String, List<Quota>> quotesPerPropietari;

    public void leerYParsearArchivos(String rutaComunidad, String rutaDespeses) throws IOException {
        // Parsear archivo comunidad.txt
        FileParser parser = new FileParser();
        comunidad = parser.parseComunidad(rutaComunidad);
        zonas = parser.parseZones(rutaComunidad);
        propietats = parser.parsePropietats(rutaComunidad);
        propietaris = parser.parsePropietaris(rutaComunidad);

        // Parsear archivo despeses.txt
        despeses = parser.parseDespeses(rutaDespeses);
    }

    public void calcularRepartos() {
        quotesPerPropietat = new HashMap<>();
        quotesPerPropietari = new HashMap<>();

        // Agrupar despesas por zona
        Map<String, List<Despesa>> despesesPerZona = despeses.stream()
                .collect(Collectors.groupingBy(Despesa::getCodiZona));

        // Calcular total por zona
        Map<String, BigDecimal> totalPerZona = new HashMap<>();
        despesesPerZona.forEach((zona, listaDespesas) -> {
            BigDecimal total = listaDespesas.stream()
                    .map(Despesa::getImporte)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalPerZona.put(zona, total);
        });

        // Calcular cuotas por propiedad
        for (Propietat propietat : propietats) {
            List<Quota> quotes = new ArrayList<>();

            for (Zona zona : zonas) {
                BigDecimal quota = BigDecimal.ZERO;

                // Verificar si la propiedad pertenece a esta zona
                Optional<PercentatgeZona> percentatgeOpt = propietat.getPercentatgesZones().stream()
                        .filter(p -> p.getCodiZona().equals(zona.getCodi()))
                        .findFirst();

                if (percentatgeOpt.isPresent()) {
                    BigDecimal percentatge = new BigDecimal(percentatgeOpt.get().getPercentatge());
                    BigDecimal totalZona = totalPerZona.getOrDefault(zona.getCodi(), BigDecimal.ZERO);

                    if (zona.getTipusRepartiment() == TipusRepartiment.PROPORCIONAL) {
                        // Reparto proporcional
                        quota = totalZona.multiply(percentatge)
                                .divide(new BigDecimal(100), 2, RoundingMode.UP);
                    } else {
                        // Reparto igualitario
                        long numPropietatsZona = propietats.stream()
                                .filter(p -> p.pertanyAZona(zona.getCodi()))
                                .count();

                        if (numPropietatsZona > 0) {
                            quota = totalZona.divide(
                                    new BigDecimal(numPropietatsZona), 2, RoundingMode.UP);
                        }
                    }
                }

                quotes.add(new Quota(zona.getCodi(), percentatgeOpt.map(PercentatgeZona::getPercentatge).orElse(0), quota));
            }

            quotesPerPropietat.put(propietat.getCodi(), quotes);
        }

        // Calcular cuotas por propietario (suma de sus propiedades)
        for (Propietari propietari : propietaris) {
            List<Quota> quotesPropietari = new ArrayList<>();

            for (Zona zona : zonas) {
                int percentatgeTotal = 0;
                BigDecimal importTotal = BigDecimal.ZERO;

                // Obtener propiedades de este propietario
                List<Propietat> propietatsDelPropietari = propietats.stream()
                        .filter(p -> p.getCodiPropietari().equals(propietari.getCodi()))
                        .collect(Collectors.toList());

                for (Propietat propietat : propietatsDelPropietari) {
                    Optional<Quota> quotaOpt = quotesPerPropietat.get(propietat.getCodi()).stream()
                            .filter(q -> q.getCodiZona().equals(zona.getCodi()))
                            .findFirst();

                    if (quotaOpt.isPresent()) {
                        Quota quota = quotaOpt.get();
                        percentatgeTotal += quota.getPercentatge();
                        importTotal = importTotal.add(quota.getImporte());
                    }
                }

                quotesPropietari.add(new Quota(zona.getCodi(), percentatgeTotal, importTotal));
            }

            quotesPerPropietari.put(propietari.getCodi(), quotesPropietari);
        }
    }

    public void generarArchivosSalida() throws IOException {
        // Generar resum.txt
        generarResum();

        // Generar propietats.txt
        generarPropietats();

        // Generar propietaris.txt
        generarPropietaris();

        // Generar quotes.txt
        generarQuotes();
    }

    private void generarResum() throws IOException {
        // Implementación para generar el archivo resum.txt
        // ...
    }

    private void generarPropietats() throws IOException {
        // Implementación para generar el archivo propietats.txt
        // ...
    }

    private void generarPropietaris() throws IOException {
        // Implementación para generar el archivo propietaris.txt
        // ...
    }

    private void generarQuotes() throws IOException {
        // Implementación para generar el archivo quotes.txt
        // ...
    }
}