
package com.umg.reservas.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    // Atributos privados
    private int idReserva;
    private int idSala;
    private String cliente;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private BigDecimal costoTotal;
    private String estado;

    // Constructor
    public Reserva(int idReserva, int idSala,
                   String cliente, LocalDate fecha,
                   LocalTime horaInicio, LocalTime horaFin,
                   BigDecimal costoTotal, String estado) {

        this.idReserva = idReserva;
        this.idSala = idSala;
        this.cliente = cliente;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.costoTotal = costoTotal;
        this.estado = estado;
    }

    // Getters
    public int getIdReserva() {
        return idReserva;
    }

    public int getIdSala() {
        return idSala;
    }

    public String getCliente() {
        return cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Validar que la hora final sea posterior
    public boolean validarHorario() {
        return horaInicio != null
                && horaFin != null
                && horaFin.isAfter(horaInicio);
    }

    // Calcular costo de la reserva
    public BigDecimal calcularCosto(BigDecimal tarifaHora) {

        if (!validarHorario()) {
            throw new IllegalArgumentException(
                    "La hora final debe ser posterior a la inicial."
            );
        }

        if (tarifaHora == null ||
                tarifaHora.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "La tarifa debe ser mayor que cero."
            );
        }

        long minutos = Duration.between(
                horaInicio, horaFin
        ).toMinutes();

        BigDecimal horas = BigDecimal.valueOf(minutos)
                .divide(BigDecimal.valueOf(60),
                        10, RoundingMode.HALF_UP);

        costoTotal = tarifaHora.multiply(horas)
                .setScale(2, RoundingMode.HALF_UP);

        return costoTotal;
    }

    // Mostrar informacion de la reserva
    public void mostrarInformacion() {

        System.out.println("-----------------------------");
        System.out.println("RESERVA #" + idReserva);
        System.out.println("Sala ID: " + idSala);
        System.out.println("Cliente: " + cliente);
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora inicio: " + horaInicio);
        System.out.println("Hora fin: " + horaFin);
        System.out.println("Costo total: Q" + costoTotal);
        System.out.println("Estado: " + estado);
        System.out.println("-----------------------------");
    }
}
