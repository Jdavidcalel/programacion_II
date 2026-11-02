
package com.umg.reservas.service;

import com.umg.reservas.entity.Reserva;
import com.umg.reservas.entity.Sala;
import com.umg.reservas.repository.ReservaRepository;
import com.umg.reservas.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            SalaRepository salaRepository) {
        this.reservaRepository = reservaRepository;
        this.salaRepository = salaRepository;
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Reserva buscarPorId(Integer id) {
        return reservaRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe la reserva con ID: " + id));
    }

    public List<Reserva> buscarPorFecha(LocalDate fecha) {
        return reservaRepository.findByFecha(fecha);
    }

    public List<Reserva> buscarPorCliente(String cliente) {
        return reservaRepository
                .findByClienteContainingIgnoreCase(cliente);
    }

    @Transactional
    public Reserva registrarReserva(
            Integer idSala,
            String cliente,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La sala no existe"));

        if (!"ACTIVA".equals(sala.getEstado())) {
            throw new IllegalArgumentException(
                    "La sala no está disponible");
        }

        if (cliente == null || cliente.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del cliente es obligatorio");
        }

        if (fecha == null ||
                horaInicio == null ||
                horaFin == null) {
            throw new IllegalArgumentException(
                    "La fecha y los horarios son obligatorios");
        }

        if (!horaFin.isAfter(horaInicio)) {
            throw new IllegalArgumentException(
                    "La hora de finalización debe ser posterior al inicio");
        }

        boolean existeTraslape = reservaRepository
                .existsBySala_IdSalaAndFechaAndEstadoAndHoraInicioLessThanAndHoraFinGreaterThan(
                        idSala,
                        fecha,
                        "ACTIVA",
                        horaFin,
                        horaInicio
                );

        if (existeTraslape) {
            throw new IllegalArgumentException(
                    "La sala ya está reservada en ese horario");
        }

        long minutos = Duration.between(
                horaInicio, horaFin).toMinutes();

        BigDecimal horas = BigDecimal.valueOf(minutos)
                .divide(BigDecimal.valueOf(60), 4,
                        RoundingMode.HALF_UP);

        BigDecimal costoTotal = sala.getTarifaHora()
                .multiply(horas)
                .setScale(2, RoundingMode.HALF_UP);

        Reserva reserva = new Reserva();
        reserva.setSala(sala);
        reserva.setCliente(cliente.trim());
        reserva.setFecha(fecha);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraFin(horaFin);
        reserva.setCostoTotal(costoTotal);
        reserva.setEstado("ACTIVA");

        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva cancelarReserva(Integer id) {
        Reserva reserva = buscarPorId(id);

        if ("CANCELADA".equals(reserva.getEstado())) {
            throw new IllegalArgumentException(
                    "La reserva ya está cancelada");
        }

        reserva.setEstado("CANCELADA");
        return reservaRepository.save(reserva);
    }

    public long contarReservasActivas() {
        return reservaRepository.countByEstado("ACTIVA");
    }
}
