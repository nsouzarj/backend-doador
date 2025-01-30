package com.banco.doador.service;

import com.banco.doador.entity.Doador;
import com.banco.doador.repository.DoadorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoadorService {


    private final DoadorRepository doadorRepository;
    private static final Map<String, List<String>> RECEPTOR_DONOR_MAP = new HashMap<>();

    static {
        RECEPTOR_DONOR_MAP.put("A+", Arrays.asList("A+", "A-", "O+", "O-"));
        RECEPTOR_DONOR_MAP.put("A-", Arrays.asList("A-", "O-"));
        RECEPTOR_DONOR_MAP.put("B+", Arrays.asList("B+", "B-", "O+", "O-"));
        RECEPTOR_DONOR_MAP.put("B-", Arrays.asList("B-", "O-"));
        RECEPTOR_DONOR_MAP.put("AB+", Arrays.asList("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"));
        RECEPTOR_DONOR_MAP.put("AB-", Arrays.asList("A-", "B-", "O-", "AB-"));
        RECEPTOR_DONOR_MAP.put("O+", Arrays.asList("O+", "O-"));
        RECEPTOR_DONOR_MAP.put("O-", Collections.singletonList("O-"));
    }

    public DoadorService(DoadorRepository doadorRepository) {
        this.doadorRepository = doadorRepository;
    }

    public void saveAll(List<Doador> doadores) {
        doadorRepository.saveAll(doadores);
    }

    public Map<String, Integer> getCandidatesPerState() {
        List<Object[]> results = doadorRepository.countByEstado();
        return results.stream()
                .collect(Collectors.toMap(
                        e -> (String) e[0],
                        e -> ((Long) e[1]).intValue()
                ));
    }

    public Map<String, Double> getAverageImcPerAgeGroup() {
        List<Doador> doadores = doadorRepository.findAll();
        Map<String, List<Double>> imcGroups = new HashMap<>();

        for (Doador doador : doadores) {
            int age = Period.between(doador.getDatanasc(), LocalDate.now()).getYears();
            String groupKey = getAgeGroup(age);
            double imc = doador.getPeso() / Math.pow(doador.getAltura(), 2);
            imcGroups.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(imc);
        }

        return imcGroups.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0)
                ));
    }

    private String getAgeGroup(int age) {
        if (age < 11) {
            return "0-10";
        } else if (age < 21) {
            return "11-20";
        } else if (age < 31) {
            return "21-30";
        } else if (age < 41) {
            return "31-40";
        } else if (age < 51) {
            return "41-50";
        } else if (age < 61) {
            return "51-60";
        } else {
            return "61+";
        }
    }

    public Map<String, Double> getObesityPercentageByGender() {
        List<Doador> doadores = doadorRepository.findAll();
        Map<String, Long> totalByGender = new HashMap<>();
        Map<String, Long> obeseCountByGender = new HashMap<>();

        for (Doador doador : doadores) {
            String gender = doador.getSexo();
            double imc = doador.getPeso() / Math.pow(doador.getAltura(), 2);
            boolean isObese = imc > 30;

            totalByGender.merge(gender, 1L, Long::sum);
            if (isObese) {
                obeseCountByGender.merge(gender, 1L, Long::sum);
            }
        }

        return totalByGender.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> (obeseCountByGender.getOrDefault(e.getKey(), 0L) * 100.0) / e.getValue()
                ));
    }

    public Map<String, Double> getAverageAgePerBloodType() {
        List<Doador> doadores = doadorRepository.findAll();
        Map<String, DoubleSummaryStatistics> stats = doadores.stream()
                .collect(Collectors.groupingBy(
                        Doador::getTiposanguineo,
                        Collectors.summarizingDouble(d -> Period.between(d.getDatanasc(), LocalDate.now()).getYears())
                ));

        return stats.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().getAverage()
                ));
    }

    public Map<String, Integer> getPossibleDonorsPerReceptor() {
        return RECEPTOR_DONOR_MAP.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> doadorRepository.countEligibleDonors(e.getValue())
                ));
    }

    public boolean isDoadorTableEmpty() {
        return doadorRepository.count() == 0;
    }

    public Doador saveDoador(Doador doador){
        return doadorRepository.save(doador);
    }
}
