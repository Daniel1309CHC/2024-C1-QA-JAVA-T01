package com.sofkau.modelos;

public class Autor {
    private String ID;
    private String nombre;

    // Constructor
    public Autor(String ID, String nombre) {
        this.ID = ID;
        this.nombre = nombre;
    }

    public Autor() {

    }

    // Getters y Setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ID='" + ID + '\'' + ", nombre='" + nombre;
    }
}
