package com.souldevec.security.repositories;

import com.souldevec.security.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    @Query("SELECT t FROM Turno t JOIN FETCH t.user")
    List<Turno> findAllWithUser();

    List<Turno> findByFechaBetween(LocalDate startDate, LocalDate endDate);

    Optional<Turno> findTopByFechaOrderByHoraSalidaDesc(LocalDate fecha);
}
