
package com.umg.reservas.modelo;

import java.math.BigDecimal;

public class Sala {

    // Atributos privados: encapsulamiento
    private int idSala;
    private String nombre;
    private int capacidad;
    private BigDecimal tarifaHora;
    private String estado;

    // Constructor
    public Sala(int idSala, String nombre,
                int capacidad, BigDecimal tarifaHora,
                String estado) {

        this.idSala = idSala;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.tarifaHora = tarifaHora;
        this.estado = estado;
    }

    // Metodos getters
    public int getIdSala() {
        return idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public BigDecimal getTarifaHora() {
        return tarifaHora;
    }

    public String getEstado() {
        return estado;
    }

    // Metodos setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setTarifaHora(BigDecimal tarifaHora) {
        this.tarifaHora = tarifaHora;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Mostrar informacion de la sala
    public void mostrarInformacion() {
        System.out.println("--------------------------");
        System.out.println("ID: " + idSala);
        System.out.println("Sala: " + nombre);
        System.out.println("Capacidad: " + capacidad);
        System.out.println("Tarifa por hora: Q" + tarifaHora);
        System.out.println("Estado: " + estado);
    }
}

