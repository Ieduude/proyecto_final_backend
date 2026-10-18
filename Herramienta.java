package com.example.alquilerherramientas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Herramienta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double tarifaDiaria;
    private Boolean disponible;

    public Herramienta() {
    }

    public Herramienta(String nombre, String descripcion, Double tarifaDiaria, Boolean disponible) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tarifaDiaria = tarifaDiaria;
        this.disponible = disponible;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public void setTarifaDiaria(Double tarifaDiaria) {
        this.tarifaDiaria = tarifaDiaria;
    }

    public Boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}