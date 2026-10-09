package com.umg.vetcare.vetcare.entity;

import jakarta.persistence.*;

public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@Entity
@Table(name = "mascota")
    private Long id;
    private String nombre;
    private String especie;

    public Mascota(String codigo, String nombre, String especie, Integer
            edadMeses, Double peso, Boolean activa) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.especie = especie;
        this.edadMeses = edadMeses;
        this.peso = peso;
        this.activa = activa;

    }


    public Long getId() { return id; }


    public String getNombre() { return nombre; }


    public String getEspecie() { return especie; }

}

