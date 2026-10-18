package com.example.alquilerherramientas.controller;

import com.example.alquilerherramientas.entity.Alquiler;
import com.example.alquilerherramientas.service.AlquilerService;
import org.springframework.web.bind.annotation.*;

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
    public Alquiler guardar(@RequestBody Alquiler alquiler) {
        return alquilerService.guardar(alquiler);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        alquilerService.eliminar(id);
    }

    @PutMapping("/{id}/devolucion")
    public Alquiler registrarDevolucion(@PathVariable Long id) {
        return alquilerService.registrarDevolucion(id);
    }
}