package com.sofkau.modelos;

import java.util.Date;

public class Prestamo {
    private String ID;
    private String usuarioID;
    private String libroID;
    private String novelaID;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private String estado;

    public Prestamo(String ID, String usuarioID, String libroID, String novelaID, Date fechaPrestamo, Date fechaDevolucion, String estado) {
        this.ID = ID;
        this.usuarioID = usuarioID;
        this.libroID = libroID;
        this.novelaID = novelaID;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getLibroID() {
        return libroID;
    }

    public void setLibroID(String libroID) {
        this.libroID = libroID;
    }

    public String getNovelaID() {
        return novelaID;
    }

    public void setNovelaID(String novelaID) {
        this.novelaID = novelaID;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Constructor, getters y setters
}
