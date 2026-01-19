package com.souldevec.security.controllers;

import com.souldevec.security.dtos.CreateTurnoDto;
import com.souldevec.security.dtos.TurnoResponseDto;
import com.souldevec.security.dtos.TurnoSummaryDto;
import com.souldevec.security.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @PostMapping
    public ResponseEntity<TurnoResponseDto> createTurno(@RequestBody CreateTurnoDto createTurnoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return ResponseEntity.ok(turnoService.save(createTurnoDto, userName));
    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> getAllTurnos() {
        return ResponseEntity.ok(turnoService.findAll());
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<TurnoSummaryDto> getTurnoSummary(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.getTurnoSummary(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoResponseDto> updateTurno(@PathVariable Long id, @RequestBody CreateTurnoDto createTurnoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return ResponseEntity.ok(turnoService.update(id, createTurnoDto, userName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTurno(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        turnoService.deleteById(id, userName);
        return ResponseEntity.ok("Turno eliminado correctamente.");
    }
}
