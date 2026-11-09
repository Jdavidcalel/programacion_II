
package com.umg.reservas.service;

import com.umg.reservas.dto.SalaRequestDTO;
import com.umg.reservas.entity.Sala;

import com.umg.reservas.exception.ConflictoReservaException;
import com.umg.reservas.exception.RecursoNoEncontradoException;

import com.umg.reservas.repository.SalaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    private static final ZoneId ZONA_GUATEMALA =
            ZoneId.of("America/Guatemala");

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    // ==========================================
    // LISTAR TODAS LAS SALAS
    // ==========================================

    @Transactional(readOnly = true)
    public List<Sala> listarSalas() {
        return salaRepository.findAll();
    }

    // ==========================================
    // BUSCAR SALA POR ID
    // ==========================================

    @Transactional(readOnly = true)
    public Sala buscarPorId(Integer id) {

        return salaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe la sala con ID: " + id
                        ));
    }

    // ==========================================
    // LISTAR SALAS ACTIVAS
    // ==========================================

    @Transactional(readOnly = true)
    public List<Sala> listarSalasActivas() {

        return salaRepository.findByEstado("ACTIVA");
    }

    // ==========================================
    // BUSCAR SALAS POR CAPACIDAD
    // ==========================================

    @Transactional(readOnly = true)
    public List<Sala> buscarPorCapacidad(Integer minimo) {

        if (minimo == null || minimo < 1) {
            throw new IllegalArgumentException(
                    "La capacidad minima debe ser mayor que cero"
            );
        }

        return salaRepository
                .findByCapacidadGreaterThanEqual(minimo);
    }

    // ==========================================
    // REGISTRAR NUEVA SALA
    // ==========================================

    @Transactional
    public Sala registrarSala(SalaRequestDTO datos) {

        validarDatosSala(datos);

        Sala sala = new Sala();

        sala.setNombre(datos.getNombre().trim());
        sala.setCapacidad(datos.getCapacidad());
        sala.setTarifaHora(datos.getTarifaHora());
        sala.setEstado("ACTIVA");

        return salaRepository.saveAndFlush(sala);
    }

    // ==========================================
    // ACTUALIZAR SALA
    // ==========================================

    @Transactional
    public Sala actualizarSala(
            Integer id,
            SalaRequestDTO datos) {

        validarDatosSala(datos);

        Sala sala = buscarPorId(id);

        if ("INACTIVA".equals(sala.getEstado())) {
            throw new ConflictoReservaException(
                    "No se puede modificar una sala inactiva"
            );
        }

        sala.setNombre(datos.getNombre().trim());
        sala.setCapacidad(datos.getCapacidad());
        sala.setTarifaHora(datos.getTarifaHora());

        return salaRepository.saveAndFlush(sala);
    }

    // ==========================================
    // DESACTIVAR SALA
    // ==========================================

    @Transactional
    public Sala desactivarSala(Integer id) {

        Sala sala = buscarPorId(id);

        if ("INACTIVA".equals(sala.getEstado())) {
            throw new ConflictoReservaException(
                    "La sala ya se encuentra inactiva"
            );
        }

        sala.setEstado("INACTIVA");

        return salaRepository.saveAndFlush(sala);
    }

    // ==========================================
    // CONSULTAR DISPONIBILIDAD REAL
    // ==========================================

    @Transactional(readOnly = true)
    public List<Sala> consultarDisponibilidad(
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        // Validar campos obligatorios
        if (fecha == null ||
                horaInicio == null ||
                horaFin == null) {

            throw new IllegalArgumentException(
                    "La fecha y los horarios son obligatorios"
            );
        }

        // Validar horario
        if (!horaFin.isAfter(horaInicio)) {
            throw new IllegalArgumentException(
                    "La hora de finalizacion debe ser " +
                            "posterior al inicio"
            );
        }

        // Validar fecha y hora de Guatemala
        LocalDateTime ahora =
                LocalDateTime.now(ZONA_GUATEMALA);

        LocalDateTime inicioSolicitado =
                LocalDateTime.of(fecha, horaInicio);

        if (!inicioSolicitado.isAfter(ahora)) {
            throw new IllegalArgumentException(
                    "No se puede consultar disponibilidad " +
                            "para fechas u horarios que ya pasaron"
            );
        }

        // Consultar salas disponibles
        return salaRepository.buscarSalasDisponibles(
                fecha,
                horaInicio,
                horaFin
        );
    }

    // ==========================================
    // VALIDAR DATOS DE SALA
    // ==========================================

    private void validarDatosSala(SalaRequestDTO datos) {

        if (datos == null) {
            throw new IllegalArgumentException(
                    "Los datos de la sala son obligatorios"
            );
        }

        if (datos.getNombre() == null ||
                datos.getNombre().isBlank()) {

            throw new IllegalArgumentException(
                    "El nombre de la sala es obligatorio"
            );
        }

        if (datos.getNombre().length() > 100) {
            throw new IllegalArgumentException(
                    "El nombre de la sala no puede " +
                            "superar los 100 caracteres"
            );
        }

        if (datos.getCapacidad() == null ||
                datos.getCapacidad() <= 0) {

            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero"
            );
        }

        if (datos.getTarifaHora() == null ||
                datos.getTarifaHora().signum() <= 0) {

            throw new IllegalArgumentException(
                    "La tarifa por hora debe ser mayor que cero"
            );
        }
    }
}
