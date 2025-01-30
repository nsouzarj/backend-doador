package com.banco.doador.controller;

import com.banco.doador.entity.Doador;
import com.banco.doador.service.DoadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/api")
public class DoadorController {
    private final DoadorService doadorService;
    public DoadorController(DoadorService doadorService) {
        this.doadorService = doadorService;
    }

    @PostMapping ("/doadores")
    public ResponseEntity<Void> saveDoadores(@RequestBody List<Doador> doadores) {
        if(doadorService.isDoadorTableEmpty()){
            doadorService.saveAll(doadores);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDoador(@RequestBody Doador doador) {
        Doador doador1 = doadorService.saveDoador(doador);
        return ResponseEntity.status(HttpStatus.CREATED).body(doador1);
    }

    @GetMapping ("/results/state-count")
    public ResponseEntity<Map<String, Integer>> getStateCounts() {
        return ResponseEntity.ok(doadorService.getCandidatesPerState());
    }

    @GetMapping("/results/average-imc")
    public ResponseEntity<Map<String, Double>> getAverageImc() {
        return ResponseEntity.ok(doadorService.getAverageImcPerAgeGroup());
    }

    @GetMapping("/results/obesity-percentage")
    public ResponseEntity<Map<String, Double>> getObesityPercentage() {
        return ResponseEntity.ok(doadorService.getObesityPercentageByGender());
    }

    @GetMapping("/results/average-age-blood")
    public ResponseEntity<Map<String, Double>> getAverageAgePerBloodType() {
        return ResponseEntity.ok(doadorService.getAverageAgePerBloodType());
    }

    @GetMapping("/results/possible-doadores")
    public ResponseEntity<Map<String, Integer>> getPossibleDonors() {
        return ResponseEntity.ok(doadorService.getPossibleDonorsPerReceptor());
    }
}
