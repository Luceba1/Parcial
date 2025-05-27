package org.example.model;

public class Autor {
    private int id;
    private String nombre;

    public Autor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Autor(String nombre) {
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return "Autor{id=" + id + ", nombre='" + nombre + "'}";
    }
}
