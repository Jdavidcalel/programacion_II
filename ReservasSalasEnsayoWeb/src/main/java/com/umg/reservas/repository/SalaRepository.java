package com.umg.reservas.repository;

import com.umg.reservas.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Integer> {

    List<Sala> findByEstado(String estado);

    List<Sala> findByCapacidadGreaterThanEqual(Integer capacidad);
}
