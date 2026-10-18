package com.example.alquilerherramientas.service;

import com.example.alquilerherramientas.entity.Alquiler;
import com.example.alquilerherramientas.entity.Herramienta;
import com.example.alquilerherramientas.repository.AlquilerRepository;
import com.example.alquilerherramientas.repository.HerramientaRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.time.LocalDate;

@Service
public class AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final HerramientaRepository herramientaRepository;

    public AlquilerService(
            AlquilerRepository alquilerRepository,
            HerramientaRepository herramientaRepository) {
        this.alquilerRepository = alquilerRepository;
        this.herramientaRepository = herramientaRepository;
    }

    public List<Alquiler> listar() {
        return alquilerRepository.findAll();
    }

    public Alquiler buscarPorId(Long id) {
        return alquilerRepository.findById(id).orElse(null);
    }

    public Alquiler guardar(Alquiler alquiler) {

        Long idHerramienta = alquiler.getHerramienta().getId();

        Herramienta herramienta = herramientaRepository.findById(idHerramienta)
                .orElseThrow(() ->
                        new RuntimeException("La herramienta no existe"));

        if (!herramienta.isDisponible()) {
            throw new com.example.alquilerherramientas.exception.RecursoNoDisponibleException(
                    "La herramienta no está disponible para alquilar");
        }

        long dias = ChronoUnit.DAYS.between(
                alquiler.getFechaAlquiler(),
                alquiler.getFechaPrevistaDevolucion()
        );

        if (dias <= 0) {
            throw new RuntimeException(
                    "La fecha prevista debe ser posterior a la fecha de alquiler");
        }

        double costo = dias * herramienta.getTarifaDiaria();

        alquiler.setHerramienta(herramienta);
        alquiler.setCostoEstimado(costo);

        herramienta.setDisponible(false);
        herramientaRepository.save(herramienta);

        return alquilerRepository.save(alquiler);
    }

    public void eliminar(Long id) {
        alquilerRepository.deleteById(id);
    }

    public Alquiler registrarDevolucion(Long id) {

        Alquiler alquiler = alquilerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("El alquiler no existe"));

        if (alquiler.getFechaDevolucion() != null) {
            throw new RuntimeException(
                    "Este alquiler ya tiene registrada su devolución");
        }

        alquiler.setFechaDevolucion(java.time.LocalDate.now());

        Herramienta herramienta = alquiler.getHerramienta();
        herramienta.setDisponible(true);

        herramientaRepository.save(herramienta);

        return alquilerRepository.save(alquiler);
    }

    public List<Alquiler> listarAtrasados() {
        LocalDate hoy = LocalDate.now();

        return alquilerRepository.findAll().stream()
                .filter(alquiler ->
                        alquiler.getFechaDevolucion() == null
                                && alquiler.getFechaPrevistaDevolucion().isBefore(hoy))
                .toList();
    }

    public List<Alquiler> listarActivos() {
        return alquilerRepository.findAll().stream()
                .filter(alquiler -> alquiler.getFechaDevolucion() == null)
                .toList();
    }
}