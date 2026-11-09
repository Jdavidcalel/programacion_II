
package com.umg.reservas.repository;

import com.umg.reservas.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository
        extends JpaRepository<Reserva, Integer> {

    // Buscar reservas por fecha
    List<Reserva> findByFecha(LocalDate fecha);

    // Buscar reservas por nombre del cliente
    List<Reserva> findByClienteContainingIgnoreCase(
            String cliente
    );

    // Verificar traslapes al registrar una reserva
    boolean existsBySala_IdSalaAndFechaAndEstadoAndHoraInicioLessThanAndHoraFinGreaterThan(
            Integer idSala,
            LocalDate fecha,
            String estado,
            LocalTime horaFin,
            LocalTime horaInicio
    );

    // Verificar traslapes al modificar una reserva,
    // excluyendo la propia reserva que se edita
    boolean existsBySala_IdSalaAndFechaAndEstadoAndHoraInicioLessThanAndHoraFinGreaterThanAndIdReservaNot(
            Integer idSala,
            LocalDate fecha,
            String estado,
            LocalTime horaFin,
            LocalTime horaInicio,
            Integer idReserva
    );

    // Contar reservas según su estado
    long countByEstado(String estado);
}
