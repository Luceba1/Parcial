package org.example.dao;

import java.util.List;

public interface GenericDAO<T> {
    void crear(T t);
    T buscarPorId(int id);
    List<T> listarTodos();
    void actualizar(T t);
    void eliminar(int id);
}
