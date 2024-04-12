package com.sofkau.modelos;

import java.util.Date;
import java.util.UUID;

public class Usuario {
    private String ID;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;
    private String documento;

    private Date fechaNacimiento;

    // Constructor
    public Usuario(String ID, String nombre, String correo, String contrasena, String rol, String documento, Date fechaNacimiento1) {
        this.ID = ID;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento1;
    }

    public Usuario(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


}
