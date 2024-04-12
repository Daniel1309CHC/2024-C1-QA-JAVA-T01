package com.sofkau.modelos;

public class Novela extends Lectura {
    private String genero;
    private int edadSugerida;

    public Novela(String ID, String titulo, int cantidadEjemplares, int cantidadPrestados, int cantidadDisponible, String genero, int edadSugerida) {
        super(ID, titulo, cantidadEjemplares, cantidadPrestados, cantidadDisponible);
        this.genero = genero;
        this.edadSugerida = edadSugerida;
    }

    public Novela() {
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdadSugerida() {
        return edadSugerida;
    }

    public void setEdadSugerida(int edadSugerida) {
        this.edadSugerida = edadSugerida;
    }

    @Override
    public String toString() {
        return  super.toString()+ ", genero='" + genero + '\'' +
                ", edad sugerida=" + edadSugerida;
    }

}
