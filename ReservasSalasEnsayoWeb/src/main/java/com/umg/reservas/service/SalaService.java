
package com.umg.reservas.service;

import com.umg.reservas.dto.SalaRequestDTO;
import com.umg.reservas.entity.Sala;
import com.umg.reservas.repository.SalaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    // Constructor
    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    // 1. REGISTRAR UNA NUEVA SALA
    @Transactional
    public Sala registrarSala(SalaRequestDTO datos) {

        Sala sala = new Sala();

        sala.setNombre(datos.getNombre().trim());
        sala.setCapacidad(datos.getCapacidad());
        sala.setTarifaHora(datos.getTarifaHora());
        sala.setEstado("ACTIVA");

        return salaRepository.save(sala);
    }

    // 2. ACTUALIZAR UNA SALA EXISTENTE
    @Transactional
    public Sala actualizarSala(
            Integer id,
            SalaRequestDTO datos) {

        Sala sala = salaRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe la sala con ID: " + id
                        )
                );

        sala.setNombre(datos.getNombre().trim());
        sala.setCapacidad(datos.getCapacidad());
        sala.setTarifaHora(datos.getTarifaHora());

        return salaRepository.save(sala);
    }

    // 3. DESACTIVAR UNA SALA
    @Transactional
    public Sala desactivarSala(Integer id) {

        Sala sala = salaRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe la sala con ID: " + id
                        )
                );

        if ("INACTIVA".equals(sala.getEstado())) {
            throw new IllegalArgumentException(
                    "La sala ya se encuentra inactiva"
            );
        }

        sala.setEstado("INACTIVA");

        return salaRepository.save(sala);
    }

}
