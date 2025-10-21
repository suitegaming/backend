package com.souldevec.security.repositories;

import com.souldevec.security.entities.YapeDetalle;
import com.souldevec.security.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YapeDetalleRepository extends JpaRepository<YapeDetalle, Long> {
    List<YapeDetalle> findByTurno(Turno turno);
}
