package com.souldevec.security.repositories;

import com.souldevec.security.entities.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findAllByOrderByTimestampDesc();
    List<Gasto> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
