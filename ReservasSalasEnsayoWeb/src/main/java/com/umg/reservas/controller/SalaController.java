
package com.umg.reservas.controller;

import com.umg.reservas.dto.SalaRequestDTO;
import com.umg.reservas.entity.Sala;
import com.umg.reservas.repository.SalaRepository;
import com.umg.reservas.service.SalaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaRepository salaRepository;
    private final SalaService salaService;

    // Constructor
    public SalaController(
            SalaRepository salaRepository,
            SalaService salaService) {

        this.salaRepository = salaRepository;
        this.salaService = salaService;
    }

    // 1. LISTAR TODAS LAS SALAS
    @GetMapping
    public List<Sala> listarSalas() {
        return salaRepository.findAll();
    }

    // 2. BUSCAR UNA SALA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Sala> buscarSala(
            @PathVariable Integer id) {

        return salaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // 3. LISTAR SALAS ACTIVAS
    @GetMapping("/activas")
    public List<Sala> listarSalasActivas() {
        return salaRepository.findByEstado("ACTIVA");
    }

    // 4. FILTRAR SALAS POR CAPACIDAD
    @GetMapping("/capacidad")
    public List<Sala> buscarPorCapacidad(
            @RequestParam Integer minimo) {

        return salaRepository
                .findByCapacidadGreaterThanEqual(minimo);
    }

    // 5. REGISTRAR UNA NUEVA SALA
    @PostMapping
    public ResponseEntity<Sala> registrarSala(
            @Valid @RequestBody SalaRequestDTO datos) {

        Sala nuevaSala =
                salaService.registrarSala(datos);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevaSala);
    }

    // 6. ACTUALIZAR UNA SALA EXISTENTE
    @PutMapping("/{id}")
    public ResponseEntity<Sala> actualizarSala(
            @PathVariable Integer id,
            @Valid @RequestBody SalaRequestDTO datos) {

        Sala salaActualizada =
                salaService.actualizarSala(id, datos);

        return ResponseEntity.ok(salaActualizada);
    }

    // 7. DESACTIVAR UNA SALA (ELIMINACIÓN LÓGICA)
    @DeleteMapping("/{id}")
    public ResponseEntity<Sala> desactivarSala(
            @PathVariable Integer id) {

        Sala salaDesactivada =
                salaService.desactivarSala(id);

        return ResponseEntity.ok(salaDesactivada);
    }
}
