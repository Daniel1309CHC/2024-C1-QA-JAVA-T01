/*
package com.sofkau.Operaciones;

import com.sofkau.TipoLectura;
import com.sofkau.integration.database.ConexionDatabase;
import com.sofkau.integration.database.mysql.MySqlOperation;
import com.sofkau.modelos.Autor;
import com.sofkau.modelos.Libro;
import com.sofkau.modelos.Novela;
import com.sofkau.modelos.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TipoLecturaOperaciones {

    private static MySqlOperation mySqlOperation =  ConexionDatabase.getMySqlOperation();

    private  Map<String, Libro> libros = new HashMap<>();
    private  Map<String, Novela> novelas = new HashMap<>();

    private BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

    private static ResultSet result;

    */
/*public TipoLecturaOperaciones() throws SQLException {
        libros = LibroOperaciones.getLibros();
    }*//*




    // Metodo para ingreso de un tipo de lectura libro o novela
    public void agregarLectura() throws IOException, SQLException {
        System.out.println("Ingrese el tipo de lectura");
        System.out.println("1. Libro");
        System.out.println("2. Novela");
        int op = Integer.parseInt(teclado.readLine());

        System.out.println("Por favor ingrese el título: ");
        String titulo = teclado.readLine();

        System.out.println("Por favor ingrese la cantidad de ejemplares :");
        int cantidadEjemplares = Integer.parseInt(teclado.readLine());

        System.out.println("Por favor ingrese la cantidad de ejemplares prestados : ");
        int cantidadPrestados = Integer.parseInt(teclado.readLine());

        int cantidadDisponible = cantidadEjemplares-cantidadPrestados;


        System.out.println("Ingrese el nombre del autor");
        String nombreAutor = teclado.readLine();

        Autor autor = AutorOperaciones.buscarAutorNombre(nombreAutor);

        if(autor == null){
            System.out.println("Por favor ingrese un autor valido");
        }else{
            if (op == 1){

                System.out.println("Por favor ingrese el área de conocimiento del libro:");
                String areaConocimiento = teclado.readLine();

                System.out.println("Por favor ingrese el número de páginas del libro:");
                int numPaginas = Integer.parseInt(teclado.readLine());

                Libro libro = new Libro(CommonOperacion.generateID() ,titulo, cantidadEjemplares, cantidadPrestados, cantidadDisponible, areaConocimiento, numPaginas);
                libro.setAutor(autor);
                LibroOperaciones.crearLibro(libro);

            }

            if(op == 2){

                System.out.println("Por favor ingrese el género de la novela:");
                String genero = teclado.readLine();

                System.out.println("Por favor ingrese la edad sugerida para la novela:");
                int edadSugerida = Integer.parseInt(teclado.readLine());

                Novela novela = new Novela(CommonOperacion.generateID(), titulo, cantidadEjemplares, cantidadPrestados, cantidadDisponible, genero, edadSugerida);
                novela.setAutor(autor);
                NovelaOperaciones.crearNovela(novela);

            }
        }

    }

*/
/*    public void ingresActualizarLibro() throws IOException, SQLException {

        String ID;
        String titulo;
        int cantidadEjemplares;
        int cantidadPrestados;
        int cantidadDisponible;


        System.out.println("Ingrese el tipo de lectura");
        System.out.println("1. Libro");
        System.out.println("2. Novela");
        int op = Integer.parseInt(teclado.readLine());
        Libro libro = new Libro();

        if(op == 1){

            System.out.println("Ingrese el ID de la lectura que desea actualizar");
            String IdBus = teclado.readLine();

            libro = libros.get(IdBus);

        }else if(op == 2){
            System.out.println("Ingrese el ID de la lectura que desea actualizar");
            *//*
*/
/*String IdBus = teclado.readLine();
            LibroOperaciones.buscarLibroPorId(IdBus);*//*
*/
/*
        }


        System.out.println("Opciones de actualización ");
        System.out.println("1. Actualizar título");
        if(op == 1){
            System.out.print("2. Actualizar area de conocimiento: ");
            System.out.print("3. Actualizar numero de paginas : ");
            String nuevaAreaConocimiento = teclado.readLine();
        }else if(op == 2){
            System.out.print("2. Actualizar el genero: ");
            System.out.print("3. Actualizar edad sugerida : ");
            String nuevoGenero = teclado.readLine();
        }
        System.out.println("4. Actualizar cantidad de ejemplares");
        System.out.println("5. Actualizar cantidad prestados");
        System.out.println("6. Actualizar autor");
        int opUpdate = Integer.parseInt(teclado.readLine());

        switch (opUpdate) {
            case 1:
                System.out.print("Ingrese el nuevo título: ");
                titulo = teclado.readLine();

                break;
            case 2:
                if(op == 1){
                    System.out.print("Ingrese el nueva área de conocimiento: ");
                    String nuevaAreaConocimiento = teclado.readLine();
                    libro.setAreaConocimiento(nuevaAreaConocimiento);

                }else if(op == 2){
                    System.out.println("Ingrese el nuevo genero:");
                    String nuevoGenero = teclado.readLine();
                }
                break;
            case 3:
                if(op == 1){
                    System.out.print("Ingrese el nuevo número de páginas: ");
                    int nuevoNumPaginas = Integer.parseInt(teclado.readLine());
                    libro.setNumPaginas(nuevoNumPaginas);

                }else if(op == 2){
                    System.out.print("Ingrese la nueva edad sugerida: ");
                    int edadSugeridad = Integer.parseInt(teclado.readLine());
                }
                break;
            case 4:
                System.out.print("Ingrese la nueva cantidad de ejemplares: ");
                cantidadEjemplares = Integer.parseInt(teclado.readLine());

                break;
            case 6:
                System.out.print("Ingrese el nuevo autor (su Id): ");
                int nuevaCantidadPrestados = Integer.parseInt(teclado.readLine());

                break;
            case 5:
                System.out.print("Ingrese la nueva cantidad de ejemplares prestados ");
                int nuevaCantidadPrestados = Integer.parseInt(teclado.readLine());
                break;

        }


        }

    }*//*





}
*/
