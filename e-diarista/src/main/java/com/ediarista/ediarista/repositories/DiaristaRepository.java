package com.ediarista.ediarista.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ediarista.ediarista.models.Diarista;

public interface DiaristaRepository extends JpaRepository<Diarista,Long>{
    Page<Diarista> findByCodigoIbge(String codigoIbge, Pageable pageable);
}
