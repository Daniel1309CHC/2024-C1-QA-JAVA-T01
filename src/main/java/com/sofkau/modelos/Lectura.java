package com.sofkau.modelos;

import java.util.ArrayList;
import java.util.List;

public abstract class Lectura {

        private String ID;
        private String titulo;
        private int cantidadEjemplares;
        private int cantidadPrestados;
        private int cantidadDisponible;

        private Autor autor;

    public Lectura(String ID, String titulo, int cantidadEjemplares, int cantidadPrestados, int cantidadDisponible) {
        this.ID = ID;
        this.titulo = titulo;
        this.cantidadEjemplares = cantidadEjemplares;
        this.cantidadPrestados = cantidadPrestados;
        this.cantidadDisponible = cantidadDisponible;
    }

    public Lectura(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCantidadEjemplares() {
        return cantidadEjemplares;
    }

    public void setCantidadEjemplares(int cantidadEjemplares) {
        this.cantidadEjemplares = cantidadEjemplares;
    }

    public int getCantidadPrestados() {
        return cantidadPrestados;
    }

    public void setCantidadPrestados(int cantidadPrestados) {
        this.cantidadPrestados = cantidadPrestados;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public void setAutor (Autor autor){
        this.autor = autor;
    }

    public Autor getAutor (){
        return autor;
    }

}
