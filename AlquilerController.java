package com.example.alquilerherramientas.controller;

import com.example.alquilerherramientas.entity.Alquiler;
import com.example.alquilerherramientas.service.AlquilerService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/alquileres")
public class AlquilerController {

    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    @GetMapping
    public List<Alquiler> listar() {
        return alquilerService.listar();
    }

    @GetMapping("/activos")
    public List<Alquiler> listarActivos() {
        return alquilerService.listarActivos();
    }

    @GetMapping("/atrasados")
    public List<Alquiler> listarAtrasados() {
        return alquilerService.listarAtrasados();
    }

    @GetMapping("/{id}")
    public Alquiler buscarPorId(@PathVariable Long id) {
        return alquilerService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Alquiler> guardar(
            @Valid @RequestBody Alquiler alquiler) {

        Alquiler nuevoAlquiler = alquilerService.guardar(alquiler);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoAlquiler);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alquilerService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/devolucion")
    public Alquiler registrarDevolucion(@PathVariable Long id) {
        return alquilerService.registrarDevolucion(id);
    }
}