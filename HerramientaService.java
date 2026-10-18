package com.example.alquilerherramientas.service;

import com.example.alquilerherramientas.entity.Herramienta;
import com.example.alquilerherramientas.repository.HerramientaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HerramientaService {

    private final HerramientaRepository herramientaRepository;

    public HerramientaService(HerramientaRepository herramientaRepository) {
        this.herramientaRepository = herramientaRepository;
    }

    public List<Herramienta> listar() {
        return herramientaRepository.findAll();
    }

    public List<Herramienta> listarDisponibles() {
        return herramientaRepository.findAll()
                .stream()
                .filter(Herramienta::isDisponible)
                .toList();
    }

    public Herramienta buscarPorId(Long id) {
        return herramientaRepository.findById(id).orElse(null);
    }

    public Herramienta guardar(Herramienta herramienta) {
        return herramientaRepository.save(herramienta);
    }

    public void eliminar(Long id) {
        herramientaRepository.deleteById(id);
    }
}