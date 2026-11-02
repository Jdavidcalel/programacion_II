
package com.umg.reservas.controller;

import com.umg.reservas.dto.ReservaRequestDTO;
import com.umg.reservas.entity.Reserva;
import com.umg.reservas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // GET: listar todas las reservas
    @GetMapping
    public List<Reserva> listar() {
        return reservaService.listarReservas();
    }

    // GET: buscar reserva por ID
    @GetMapping("/{id}")
    public Reserva buscarPorId(@PathVariable Integer id) {
        return reservaService.buscarPorId(id);
    }

    // GET: filtrar reservas por fecha
    @GetMapping("/fecha")
    public List<Reserva> buscarPorFecha(
            @RequestParam LocalDate fecha) {
        return reservaService.buscarPorFecha(fecha);
    }

    // GET: filtrar reservas por cliente
    @GetMapping("/cliente")
    public List<Reserva> buscarPorCliente(
            @RequestParam String nombre) {
        return reservaService.buscarPorCliente(nombre);
    }

    // POST: registrar una nueva reserva
    @PostMapping
    public ResponseEntity<Reserva> registrar(
            @Valid @RequestBody ReservaRequestDTO datos) {

        Reserva reserva = reservaService.registrarReserva(
                datos.getIdSala(),
                datos.getCliente(),
                datos.getFecha(),
                datos.getHoraInicio(),
                datos.getHoraFin()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reserva);
    }

    // PATCH: cancelar una reserva
    @PatchMapping("/{id}/cancelar")
    public Reserva cancelar(@PathVariable Integer id) {
        return reservaService.cancelarReserva(id);
    }

    // GET: indicador de reservas activas
    @GetMapping("/indicadores/activas")
    public Map<String, Long> contarActivas() {
        return Map.of(
                "reservasActivas",
                reservaService.contarReservasActivas()
        );
    }
}
