package com.umg.vetcare.vetcare.controller;

import com.umg.vetcare.vetcare.entity.Mascota;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
    @GetMapping

    public List<Mascota> listar() {

        return List.of(

                new Mascota(1L, "Luna", "Canino"),

                new Mascota(2L, "Milo", "Felino"),

                new Mascota(3L, "Piolín", "Ave")

        );

    }
    @GetMapping("/{id}")

    public Mascota buscar(@PathVariable Long id) {

        return new Mascota(

                id,

                "Mascota de ejemplo",

                "Sin definir"

        );

    }
}
