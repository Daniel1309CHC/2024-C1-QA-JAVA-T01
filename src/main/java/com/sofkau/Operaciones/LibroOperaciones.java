package com.sofkau.Operaciones;

import com.sofkau.integration.database.ConexionDatabase;
import com.sofkau.integration.database.mysql.MySqlOperation;
import com.sofkau.modelos.Autor;
import com.sofkau.modelos.Libro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LibroOperaciones {

    private final static MySqlOperation mySqlOperation =  ConexionDatabase.getMySqlOperation();
    private static Map<String, Libro> libros = new HashMap<>();
    private static Map<String, Autor> autores = new HashMap<>();
    private AutorOperaciones autorOp;
    private BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
    private static ResultSet result;

    public LibroOperaciones() throws SQLException {

        autorOp = new AutorOperaciones();
        autores = autorOp.getAutores();
        this.getLibros();
    }

    // Se crea un libro en la bd
    protected static void crearLibro(Libro libro)  throws SQLException {
        String query = String.format("INSERT INTO Libro (ID, Titulo, Area_conocimiento, Num_paginas, Cantidad_ejemplares, Cantidad_prestados, Cantidad_disponible, Autor_ID) VALUES ('%s', '%s', '%s', %d, %d, %d, %d, '%s')",
        libro.getID(), libro.getTitulo(), libro.getAreaConocimiento(), libro.getNumPaginas(), libro.getCantidadEjemplares(), libro.getCantidadPrestados(), libro.getCantidadDisponible(), libro.getAutor().getID());
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatementVoid();
        System.out.println("Se ingreso el libro correctamente");
        libros.put(libro.getID(), libro);
    }

    private void actualizarLibro(Libro libro) throws SQLException {
        String query = String.format("UPDATE Libro SET Titulo = '%s', Area_conocimiento = '%s', Num_paginas = %d, Cantidad_ejemplares = %d, Cantidad_prestados = %d, Cantidad_disponible = %d, Autor_ID = '%s' WHERE ID = '%s'",
                libro.getTitulo(), libro.getAreaConocimiento(), libro.getNumPaginas(), libro.getCantidadEjemplares(), libro.getCantidadPrestados(), libro.getCantidadDisponible(), libro.getAutor().getID(), libro.getID());
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatementVoid();
        System.out.println("Se actualizó el libro correctamente");
    }


    private void getLibros() throws SQLException {
        String query = "SELECT * FROM Libro";
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatement();
        ResultSet resultSet = mySqlOperation.getResulset();

        while (resultSet.next()) {
            Libro libro = new Libro();
            libro.setID(resultSet.getString("ID"));
            libro.setTitulo(resultSet.getString("Titulo"));
            libro.setAreaConocimiento(resultSet.getString("Area_conocimiento"));
            libro.setNumPaginas(resultSet.getInt("Num_paginas"));
            libro.setCantidadEjemplares(resultSet.getInt("Cantidad_ejemplares"));
            libro.setCantidadPrestados(resultSet.getInt("Cantidad_prestados"));
            libro.setCantidadDisponible(resultSet.getInt("Cantidad_disponible"));
            // Suponiendo que el ID del autor está en una columna llamada "Autor_ID"
            String autorId = resultSet.getString("Autor_ID");
            Autor autor = autorOp.buscarAutorId(autorId);
            libro.setAutor(autor);
            libros.put(libro.getID(), libro);
        }
    }



    public void agregarLibro() throws IOException, SQLException, IOException {
        System.out.println("Por favor ingrese el título del libro:");
        String titulo = teclado.readLine();

        System.out.println("Por favor ingrese la cantidad de ejemplares:");
        int cantidadEjemplares = Integer.parseInt(teclado.readLine());

        System.out.println("Por favor ingrese la cantidad de ejemplares prestados:");
        int cantidadPrestados = Integer.parseInt(teclado.readLine());

        int cantidadDisponible = cantidadEjemplares - cantidadPrestados;

        System.out.println("Ingrese el nombre del autor:");
        String nombreAutor = teclado.readLine();

        Autor autor = autorOp.buscarAutorNombre(nombreAutor);

        if (autor == null) {
            System.out.println("Por favor ingrese un autor válido");
        } else {
            System.out.println("Por favor ingrese el área de conocimiento del libro:");
            String areaConocimiento = teclado.readLine();

            System.out.println("Por favor ingrese el número de páginas del libro:");
            int numPaginas = Integer.parseInt(teclado.readLine());

            Libro libro = new Libro(CommonOperacion.generateID(), titulo, cantidadEjemplares, cantidadPrestados, cantidadDisponible, areaConocimiento, numPaginas);
            libro.setAutor(autor);
            LibroOperaciones.crearLibro(libro);
        }
    }

    public void ingresoactualizarLibro() throws IOException, SQLException {
        System.out.println("Ingrese el ID del libro que desea actualizar:");
        String idLibro = teclado.readLine();

       Libro libro = libros.get(idLibro);

        if (libro == null) {
            System.out.println("El libro con el ID especificado no existe.");
            return;
        }

        System.out.println("Opciones de actualización:");
        System.out.println("1. Actualizar título");
        System.out.println("2. Actualizar área de conocimiento");
        System.out.println("3. Actualizar número de páginas");
        System.out.println("4. Actualizar cantidad de ejemplares");
        System.out.println("5. Actualizar cantidad prestados");
        System.out.println("6. Actualizar autor");

        int opcion = Integer.parseInt(teclado.readLine());

        switch (opcion) {
            case 1:
                System.out.println("Ingrese el nuevo título:");
                String nuevoTitulo = teclado.readLine();
                libro.setTitulo(nuevoTitulo);
                break;
            case 2:
                System.out.println("Ingrese la nueva área de conocimiento:");
                String nuevaAreaConocimiento = teclado.readLine();
                libro.setAreaConocimiento(nuevaAreaConocimiento);
                break;
            case 3:
                System.out.println("Ingrese el nuevo número de páginas:");
                int nuevoNumPaginas = Integer.parseInt(teclado.readLine());
                libro.setNumPaginas(nuevoNumPaginas);
                break;
            case 4:
                System.out.println("Ingrese la nueva cantidad de ejemplares:");
                int nuevaCantidadEjemplares = Integer.parseInt(teclado.readLine());
                libro.setCantidadEjemplares(nuevaCantidadEjemplares);
                break;
            case 5:
                System.out.println("Ingrese la nueva cantidad de ejemplares prestados:");
                int nuevaCantidadPrestados = Integer.parseInt(teclado.readLine());
                libro.setCantidadPrestados(nuevaCantidadPrestados);
                break;
            case 6:
                System.out.println("Ingrese el nuevo ID del autor:");
                String nuevoIdAutor = teclado.readLine();
                Autor autor = autorOp.buscarAutorId(nuevoIdAutor);
                if (autor != null) {
                    libro.setAutor(autor);
                } else {
                    System.out.println("No se encontró ningún autor con el ID especificado.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

        // Actualizar el libro en la base de datos
        actualizarLibro(libro);
    }




}
