
package com.umg.reservas.service;

import com.umg.reservas.entity.Reserva;
import com.umg.reservas.entity.Sala;
import com.umg.reservas.exception.ConflictoReservaException;
import com.umg.reservas.exception.RecursoNoEncontradoException;
import com.umg.reservas.repository.ReservaRepository;
import com.umg.reservas.repository.SalaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;

    private static final ZoneId ZONA_GUATEMALA =
            ZoneId.of("America/Guatemala");

    public ReservaService(
            ReservaRepository reservaRepository,
            SalaRepository salaRepository) {

        this.reservaRepository = reservaRepository;
        this.salaRepository = salaRepository;
    }

    // ==========================================
    // CONSULTAR RESERVAS
    // ==========================================

    @Transactional(readOnly = true)
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Reserva buscarPorId(Integer id) {
        return reservaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe la reserva con ID: " + id
                        ));
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscarPorFecha(LocalDate fecha) {
        return reservaRepository.findByFecha(fecha);
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscarPorCliente(String cliente) {
        return reservaRepository
                .findByClienteContainingIgnoreCase(cliente);
    }

    // ==========================================
    // REGISTRAR RESERVA
    // ==========================================

    @Transactional
    public Reserva registrarReserva(
            Integer idSala,
            String cliente,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        validarDatos(cliente, fecha, horaInicio, horaFin);
        validarFechaHora(fecha, horaInicio);

        Sala sala = obtenerSalaActiva(idSala);

        boolean existeTraslape = reservaRepository
                .existsBySala_IdSalaAndFechaAndEstadoAndHoraInicioLessThanAndHoraFinGreaterThan(
                        idSala,
                        fecha,
                        "ACTIVA",
                        horaFin,
                        horaInicio
                );

        if (existeTraslape) {
            throw new ConflictoReservaException(
                    "La sala ya está reservada en ese horario"
            );
        }

        BigDecimal costoTotal =
                calcularCosto(sala, horaInicio, horaFin);

        Reserva reserva = new Reserva();
        reserva.setSala(sala);
        reserva.setCliente(cliente.trim());
        reserva.setFecha(fecha);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraFin(horaFin);
        reserva.setCostoTotal(costoTotal);
        reserva.setEstado("ACTIVA");

        // saveAndFlush permite detectar restricciones SQL
        // antes de finalizar este metodo.
        return reservaRepository.saveAndFlush(reserva);
    }

    // ==========================================
    // ACTUALIZAR RESERVA
    // ==========================================

    @Transactional
    public Reserva actualizarReserva(
            Integer idReserva,
            Integer idSala,
            String cliente,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        Reserva reserva = buscarPorId(idReserva);

        if (!"ACTIVA".equals(reserva.getEstado())) {
            throw new ConflictoReservaException(
                    "No se puede modificar una reserva cancelada"
            );
        }

        validarDatos(cliente, fecha, horaInicio, horaFin);
        validarFechaHora(fecha, horaInicio);

        Sala sala = obtenerSalaActiva(idSala);

        boolean existeTraslape = reservaRepository
                .existsBySala_IdSalaAndFechaAndEstadoAndHoraInicioLessThanAndHoraFinGreaterThanAndIdReservaNot(
                        idSala,
                        fecha,
                        "ACTIVA",
                        horaFin,
                        horaInicio,
                        idReserva
                );

        if (existeTraslape) {
            throw new ConflictoReservaException(
                    "La sala ya tiene otra reserva activa en ese horario"
            );
        }

        BigDecimal costoTotal =
                calcularCosto(sala, horaInicio, horaFin);

        reserva.setSala(sala);
        reserva.setCliente(cliente.trim());
        reserva.setFecha(fecha);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraFin(horaFin);
        reserva.setCostoTotal(costoTotal);

        return reservaRepository.saveAndFlush(reserva);
    }

    // ==========================================
    // CANCELAR RESERVA
    // ==========================================

    @Transactional
    public Reserva cancelarReserva(Integer id) {

        Reserva reserva = buscarPorId(id);

        if ("CANCELADA".equals(reserva.getEstado())) {
            throw new ConflictoReservaException(
                    "La reserva ya está cancelada"
            );
        }

        reserva.setEstado("CANCELADA");

        return reservaRepository.saveAndFlush(reserva);
    }

    // ==========================================
    // INDICADOR
    // ==========================================

    @Transactional(readOnly = true)
    public long contarReservasActivas() {
        return reservaRepository.countByEstado("ACTIVA");
    }

    // ==========================================
    // VALIDAR DATOS
    // ==========================================

    private void validarDatos(
            String cliente,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        if (cliente == null || cliente.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del cliente es obligatorio"
            );
        }

        if (fecha == null ||
                horaInicio == null ||
                horaFin == null) {

            throw new IllegalArgumentException(
                    "La fecha y los horarios son obligatorios"
            );
        }

        if (!horaFin.isAfter(horaInicio)) {
            throw new IllegalArgumentException(
                    "La hora de finalización debe ser posterior al inicio"
            );
        }
    }

    // ==========================================
    // VALIDAR FECHA Y HORA
    // ==========================================

    private void validarFechaHora(
            LocalDate fecha,
            LocalTime horaInicio) {

        LocalDateTime ahora =
                LocalDateTime.now(ZONA_GUATEMALA);

        LocalDateTime inicioReserva =
                LocalDateTime.of(fecha, horaInicio);

        if (!inicioReserva.isAfter(ahora)) {
            throw new IllegalArgumentException(
                    "No se permiten reservas en fechas " +
                            "u horarios que ya pasaron"
            );
        }
    }

    // ==========================================
    // OBTENER SALA ACTIVA
    // ==========================================

    private Sala obtenerSalaActiva(Integer idSala) {

        if (idSala == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar una sala"
            );
        }

        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "La sala seleccionada no existe"
                        ));

        if (!"ACTIVA".equals(sala.getEstado())) {
            throw new ConflictoReservaException(
                    "La sala seleccionada esta inactiva"
            );
        }

        return sala;
    }

    // ==========================================
    // CALCULAR COSTO
    // ==========================================

    private BigDecimal calcularCosto(
            Sala sala,
            LocalTime horaInicio,
            LocalTime horaFin) {

        long minutos = Duration.between(
                horaInicio, horaFin
        ).toMinutes();

        BigDecimal horas = BigDecimal.valueOf(minutos)
                .divide(
                        BigDecimal.valueOf(60),
                        4,
                        RoundingMode.HALF_UP
                );

        return sala.getTarifaHora()
                .multiply(horas)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
