package com.umg.reservas.repository;

import com.umg.reservas.entity.Sala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SalaRepository
        extends JpaRepository<Sala, Integer> {

    // ==========================================
    // BUSCAR SALAS POR ESTADO
    // ==========================================

    List<Sala> findByEstado(String estado);

    // ==========================================
    // BUSCAR SALAS POR CAPACIDAD MINIMA
    // ==========================================

    List<Sala> findByCapacidadGreaterThanEqual(
            Integer capacidad
    );

    // ==========================================
    // CONSULTAR SALAS DISPONIBLES
    // ==========================================

    @Query("""
        SELECT s
        FROM Sala s
        WHERE s.estado = 'ACTIVA'
          AND NOT EXISTS (
              SELECT r.idReserva
              FROM Reserva r
              WHERE r.sala = s
                AND r.fecha = :fecha
                AND r.estado = 'ACTIVA'
                AND r.horaInicio < :horaFin
                AND r.horaFin > :horaInicio
          )
        ORDER BY s.idSala
        """)
    List<Sala> buscarSalasDisponibles(
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin
    );
}
