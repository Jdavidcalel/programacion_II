
package com.umg.reservas.controller;

import com.umg.reservas.dto.ReservaRequestDTO;
import com.umg.reservas.entity.Reserva;
import com.umg.reservas.service.ReservaService;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // ==========================================
    // LISTAR TODAS LAS RESERVAS
    // GET /api/reservas
    // ==========================================

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.listarReservas();
    }

    // ==========================================
    // BUSCAR RESERVA POR ID
    // GET /api/reservas/{id}
    // ==========================================

    @GetMapping("/{id}")
    public Reserva buscarPorId(@PathVariable Integer id) {
        return reservaService.buscarPorId(id);
    }

    // ==========================================
    // BUSCAR RESERVAS POR FECHA
    // GET /api/reservas/fecha?fecha=2026-11-20
    // ==========================================

    @GetMapping("/fecha")
    public List<Reserva> buscarPorFecha(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        return reservaService.buscarPorFecha(fecha);
    }

    // ==========================================
    // BUSCAR RESERVAS POR CLIENTE
    // GET /api/reservas/cliente?nombre=Juan
    // ==========================================

    @GetMapping("/cliente")
    public List<Reserva> buscarPorCliente(
            @RequestParam String nombre) {

        return reservaService.buscarPorCliente(nombre);
    }

    // ==========================================
    // REGISTRAR RESERVA
    // POST /api/reservas
    // ==========================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva registrarReserva(
            @Valid @RequestBody ReservaRequestDTO datos) {

        return reservaService.registrarReserva(
                datos.getIdSala(),
                datos.getCliente(),
                datos.getFecha(),
                datos.getHoraInicio(),
                datos.getHoraFin()
        );
    }

    // ==========================================
    // ACTUALIZAR RESERVA
    // PUT /api/reservas/{id}
    // ==========================================

    @PutMapping("/{id}")
    public Reserva actualizarReserva(
            @PathVariable Integer id,
            @Valid @RequestBody ReservaRequestDTO datos) {

        return reservaService.actualizarReserva(
                id,
                datos.getIdSala(),
                datos.getCliente(),
                datos.getFecha(),
                datos.getHoraInicio(),
                datos.getHoraFin()
        );
    }

    // ==========================================
    // CANCELAR RESERVA
    // PATCH /api/reservas/{id}/cancelar
    // ==========================================

    @PatchMapping("/{id}/cancelar")
    public Reserva cancelarReserva(
            @PathVariable Integer id) {

        return reservaService.cancelarReserva(id);
    }

    // ==========================================
    // INDICADOR DE RESERVAS ACTIVAS
    // GET /api/reservas/indicadores/activas
    // ==========================================

    @GetMapping("/indicadores/activas")
    public Map<String, Long> contarReservasActivas() {

        return Map.of(
                "reservasActivas",
                reservaService.contarReservasActivas()
        );
    }
}
