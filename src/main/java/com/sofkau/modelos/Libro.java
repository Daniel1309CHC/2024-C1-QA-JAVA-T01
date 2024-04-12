package com.sofkau.modelos;

public class Libro extends Lectura {
        private String areaConocimiento;
        private int numPaginas;

    public Libro(String ID, String titulo, int cantidadEjemplares, int cantidadPrestados, int cantidadDisponible, String areaConocimiento, int numPaginas) {
        super(ID, titulo, cantidadEjemplares, cantidadPrestados, cantidadDisponible);
        this.areaConocimiento = areaConocimiento;
        this.numPaginas = numPaginas;
    }

    public Libro() {

    }

    public String getAreaConocimiento() {
        return areaConocimiento;
    }

    public void setAreaConocimiento(String areaConocimiento) {
        this.areaConocimiento = areaConocimiento;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    // Constructores, getters y setters

}
