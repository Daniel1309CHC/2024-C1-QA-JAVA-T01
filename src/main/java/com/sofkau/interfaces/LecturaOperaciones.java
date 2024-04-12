package com.sofkau.interfaces;

import java.util.List;

public interface LecturaOperaciones {
    // Método para crear un objeto
    <T> void crear(T objeto);

    // Método para actualizar un objeto
    <T> void actualizar(T objeto);

    // Método para buscar un objeto por su ID
    <T> T buscarPorId(String id);

    // Método para listar todos los objetos
    <T> List<T> listar();
}
