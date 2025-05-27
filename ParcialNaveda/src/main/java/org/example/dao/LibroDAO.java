package org.example.dao;

import org.example.model.Libro;
import java.util.List;

public interface LibroDAO extends GenericDAO<Libro> {
    List<Libro> listarPorAutorId(int autorId);
}



