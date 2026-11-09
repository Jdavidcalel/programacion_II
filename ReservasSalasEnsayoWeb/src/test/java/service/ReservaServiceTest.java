
package com.umg.reservas.service;

import com.umg.reservas.entity.Reserva;
import com.umg.reservas.entity.Sala;
import com.umg.reservas.repository.ReservaRepository;
import com.umg.reservas.repository.SalaRepository;
import com.umg.reservas.exception.ConflictoReservaException;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Sala sala;

    @BeforeEach
    void configurar() {

        sala = new Sala();

        sala.setIdSala(1);
        sala.setNombre("Sala Rock");
        sala.setCapacidad(6);
        sala.setTarifaHora(new BigDecimal("75.00"));
        sala.setEstado("ACTIVA");
    }

    @Test
    void debeCalcularCorrectamenteCostoReserva() {

        LocalDate fecha = LocalDate.now(
                java.time.ZoneId.of("America/Guatemala")
        ).plusDays(5);

        LocalTime inicio = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(12, 0);

        when(salaRepository.findById(1))
                .thenReturn(Optional.of(sala));

        when(reservaRepository
                .existsBySala_IdSalaAndFechaAndEstadoAndHoraInicioLessThanAndHoraFinGreaterThan(
                        1, fecha, "ACTIVA", fin, inicio
                ))
                .thenReturn(false);

        when(reservaRepository.saveAndFlush(any(Reserva.class)))
                .thenAnswer(invocacion ->
                        invocacion.getArgument(0)
                );

        Reserva resultado = reservaService.registrarReserva(
                1,
                "Banda de Prueba",
                fecha,
                inicio,
                fin
        );

        assertNotNull(resultado);

        assertEquals(
                0,
                new BigDecimal("150.00")
                        .compareTo(resultado.getCostoTotal())
        );

        assertEquals("ACTIVA", resultado.getEstado());

        verify(reservaRepository, times(1))
                .saveAndFlush(any(Reserva.class));
    }

    @Test
    void debeRechazarReservaConHorarioOcupado() {

        LocalDate fecha = LocalDate.now(
                ZoneId.of("America/Guatemala")
        ).plusDays(5);

        LocalTime inicio = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(12, 0);

        when(salaRepository.findById(1))
                .thenReturn(Optional.of(sala));

        when(reservaRepository
                .existsBySala_IdSalaAndFechaAndEstadoAndHoraInicioLessThanAndHoraFinGreaterThan(
                        1, fecha, "ACTIVA", fin, inicio
                ))
                .thenReturn(true);

        assertThrows(
                ConflictoReservaException.class,
                () -> reservaService.registrarReserva(
                        1,
                        "Banda con Horario Ocupado",
                        fecha,
                        inicio,
                        fin
                )
        );

        verify(reservaRepository, never())
                .saveAndFlush(any(Reserva.class));
    }

    @Test
    void debeCancelarReservaActiva() {

        Reserva reserva = new Reserva();
        reserva.setEstado("ACTIVA");

        when(reservaRepository.findById(1))
                .thenReturn(Optional.of(reserva));

        when(reservaRepository.saveAndFlush(any(Reserva.class)))
                .thenAnswer(invocacion ->
                        invocacion.getArgument(0)
                );

        Reserva resultado = reservaService.cancelarReserva(1);

        assertEquals("CANCELADA", resultado.getEstado());

        verify(reservaRepository, times(1))
                .saveAndFlush(reserva);
    }

    @Test
    void debeRechazarReservaYaCancelada() {

        Reserva reserva = new Reserva();
        reserva.setEstado("CANCELADA");

        when(reservaRepository.findById(1))
                .thenReturn(Optional.of(reserva));

        assertThrows(
                ConflictoReservaException.class,
                () -> reservaService.cancelarReserva(1)
        );

        verify(reservaRepository, never())
                .saveAndFlush(any(Reserva.class));
    }

    @Test
    void debeRechazarReservaConFechaPasada() {

        LocalDate fechaPasada = LocalDate.now(
                ZoneId.of("America/Guatemala")
        ).minusDays(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> reservaService.registrarReserva(
                        1,
                        "Cliente Fecha Pasada",
                        fechaPasada,
                        LocalTime.of(10, 0),
                        LocalTime.of(12, 0)
                )
        );

        verifyNoInteractions(
                salaRepository,
                reservaRepository
        );
    }

    @Test
    void debeRechazarHorarioPasadoDelDiaActual() {

        ZoneId zona = ZoneId.of("America/Guatemala");

        LocalDate fechaActual = LocalDate.now(zona);

        // Un horario que ya finalizo durante el dia actual.
        LocalTime inicio = LocalTime.MIDNIGHT;
        LocalTime fin = LocalTime.of(0, 1);

        assertThrows(
                IllegalArgumentException.class,
                () -> reservaService.registrarReserva(
                        1,
                        "Cliente Horario Pasado",
                        fechaActual,
                        inicio,
                        fin
                )
        );

        verifyNoInteractions(
                salaRepository,
                reservaRepository
        );
    }

    @Test
    void debeActualizarReservaYRecalcularCosto() {

        LocalDate fecha = LocalDate.now(
                ZoneId.of("America/Guatemala")
        ).plusDays(10);

        LocalTime inicio = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(13, 0);

        Reserva reservaExistente = new Reserva();
        reservaExistente.setEstado("ACTIVA");
        reservaExistente.setSala(sala);

        when(reservaRepository.findById(1))
                .thenReturn(Optional.of(reservaExistente));

        when(salaRepository.findById(1))
                .thenReturn(Optional.of(sala));

        when(reservaRepository
                .existsBySala_IdSalaAndFechaAndEstadoAndHoraInicioLessThanAndHoraFinGreaterThanAndIdReservaNot(
                        1,
                        fecha,
                        "ACTIVA",
                        fin,
                        inicio,
                        1
                ))
                .thenReturn(false);

        when(reservaRepository.saveAndFlush(any(Reserva.class)))
                .thenAnswer(invocacion ->
                        invocacion.getArgument(0)
                );

        Reserva resultado = reservaService.actualizarReserva(
                1,
                1,
                "Cliente Actualizado",
                fecha,
                inicio,
                fin
        );

        // Sala Rock: Q75 por hora x 3 horas = Q225
        assertEquals(
                0,
                new BigDecimal("225.00")
                        .compareTo(resultado.getCostoTotal())
        );

        assertEquals(
                "Cliente Actualizado",
                resultado.getCliente()
        );

        assertEquals(fecha, resultado.getFecha());

        verify(reservaRepository, times(1))
                .saveAndFlush(any(Reserva.class));
    }
}
