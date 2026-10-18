package com.example.alquilerherramientas.controller;

import com.example.alquilerherramientas.entity.Herramienta;
import com.example.alquilerherramientas.service.HerramientaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/herramientas")
public class HerramientaController {

    private final HerramientaService herramientaService;

    public HerramientaController(HerramientaService herramientaService) {
        this.herramientaService = herramientaService;
    }

    @GetMapping
    public List<Herramienta> listar() {
        return herramientaService.listar();
    }

    @GetMapping("/disponibles")
    public List<Herramienta> listarDisponibles() {
        return herramientaService.listarDisponibles();
    }

    @GetMapping("/{id}")
    public Herramienta buscarPorId(@PathVariable Long id) {
        return herramientaService.buscarPorId(id);
    }

    @PostMapping
    public Herramienta guardar(@RequestBody Herramienta herramienta) {
        return herramientaService.guardar(herramienta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        herramientaService.eliminar(id);
    }
}