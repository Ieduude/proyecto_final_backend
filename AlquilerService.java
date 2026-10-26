package com.example.alquilerherramientas.service;

import com.example.alquilerherramientas.entity.Alquiler;
import com.example.alquilerherramientas.entity.Herramienta;
import com.example.alquilerherramientas.repository.AlquilerRepository;
import com.example.alquilerherramientas.repository.HerramientaRepository;
import com.example.alquilerherramientas.exception.DevolucionYaRegistradaException;
import com.example.alquilerherramientas.exception.FechasInvalidasException;
import com.example.alquilerherramientas.exception.RecursoNoDisponibleException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
        return alquilerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("El alquiler no existe"));
    }

    public Alquiler guardar(Alquiler alquiler) {

        // Validar que se haya seleccionado una herramienta con ID.
        if (alquiler.getHerramienta() == null
                || alquiler.getHerramienta().getId() == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar una herramienta válida");
        }

        Long idHerramienta = alquiler.getHerramienta().getId();

        // Buscar la herramienta en la base de datos.
        Herramienta herramienta = herramientaRepository.findById(idHerramienta)
                .orElseThrow(() ->
                        new RuntimeException("La herramienta no existe"));

        // Verificar que la herramienta esté disponible.
        if (!herramienta.isDisponible()) {
            throw new RecursoNoDisponibleException(
                    "La herramienta no está disponible para alquilar");
        }

        // Calcular los días del alquiler.
        long dias = ChronoUnit.DAYS.between(
                alquiler.getFechaAlquiler(),
                alquiler.getFechaPrevistaDevolucion()
        );

        // Validar que la devolución prevista sea posterior al alquiler.
        if (dias <= 0) {
            throw new FechasInvalidasException(
                    "La fecha prevista debe ser posterior a la fecha de alquiler");
        }

        // Calcular el costo estimado.
        double costo = dias * herramienta.getTarifaDiaria();

        alquiler.setHerramienta(herramienta);
        alquiler.setCostoEstimado(costo);

        // Marcar la herramienta como no disponible.
        herramienta.setDisponible(false);
        herramientaRepository.save(herramienta);

        return alquilerRepository.save(alquiler);
    }

    public void eliminar(Long id) {

        Alquiler alquiler = alquilerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("El alquiler no existe"));

        // No permitir eliminar alquileres activos.
        if (alquiler.getFechaDevolucion() == null) {
            throw new RecursoNoDisponibleException(
                    "No se puede eliminar un alquiler activo; primero debe registrarse su devolución");
        }

        alquilerRepository.delete(alquiler);
    }

    public Alquiler registrarDevolucion(Long id) {

        Alquiler alquiler = alquilerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("El alquiler no existe"));

        // Evitar registrar dos veces la misma devolución.
        if (alquiler.getFechaDevolucion() != null) {
            throw new DevolucionYaRegistradaException(
                    "Este alquiler ya tiene registrada su devolución");
        }

        // Registrar la fecha de devolución.
        alquiler.setFechaDevolucion(LocalDate.now());

        // Habilitar nuevamente la herramienta.
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
                .filter(alquiler ->
                        alquiler.getFechaDevolucion() == null)
                .toList();
    }
}