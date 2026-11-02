
package com.umg.reservas.repository;

import com.umg.reservas.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByFecha(LocalDate fecha);

    List<Reserva> findByClienteContainingIgnoreCase(String cliente);

    boolean existsBySala_IdSalaAndFechaAndEstadoAndHoraInicioLessThanAndHoraFinGreaterThan(
            Integer idSala,
            LocalDate fecha,
            String estado,
            LocalTime horaFin,
            LocalTime horaInicio
    );

    long countByEstado(String estado);
}
