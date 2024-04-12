package com.sofkau.Operaciones;

import com.sofkau.integration.database.ConexionDatabase;
import com.sofkau.integration.database.mysql.MySqlOperation;
import com.sofkau.modelos.Autor;
import com.sofkau.modelos.Libro;
import com.sofkau.modelos.Novela;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class NovelaOperaciones {

    private static MySqlOperation mySqlOperation =  ConexionDatabase.getMySqlOperation();


    private BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

    private static ResultSet result;

    private Map<String, Libro> libros = new HashMap<>();
    private Map<String, Autor> autores = new HashMap<>();

    private static Map<String, Novela> novelas = new HashMap<>();

    private AutorOperaciones autorOp;

    public NovelaOperaciones() throws SQLException {
        this.autorOp = new AutorOperaciones();
        this.autores = autorOp.getAutores();
        getNovelas();
    }

    // Se crea una novela en la bd
    protected static void crearNovela(Novela novela) throws SQLException {
        String query = String.format("INSERT INTO Novela (ID, Titulo, Genero, Edad_sugerida, Cantidad_ejemplares, Cantidad_prestados, Cantidad_disponible, Autor_ID) VALUES ('%s', '%s', '%s', '%s', %d, %d, %d, '%s')",
        novela.getID(), novela.getTitulo(), novela.getGenero(), novela.getEdadSugerida(), novela.getCantidadEjemplares(), novela.getCantidadPrestados(), novela.getCantidadDisponible(), novela.getAutor().getID());
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatementVoid();
        System.out.println("Se ingresó la novela correctamente");
    }

    protected static void actualizarNovela(Novela novela) throws SQLException {
        String query = String.format("UPDATE Novela SET Titulo = '%s', Genero = '%s', Edad_sugerida = %d, Cantidad_ejemplares = %d, Cantidad_prestados = %d, Cantidad_disponible = %d, Autor_ID = '%s' WHERE ID = '%s'",
                novela.getTitulo(), novela.getGenero(), novela.getEdadSugerida(), novela.getCantidadEjemplares(), novela.getCantidadPrestados(), novela.getCantidadDisponible(), novela.getAutor().getID(), novela.getID());
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatementVoid();
        System.out.println("Se actualizó la novela correctamente");
    }

    public void agregarNovela() throws SQLException, IOException {
        System.out.println("Por favor ingrese el título de la novela:");
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
            System.out.println("Por favor ingrese el género de la novela:");
            String genero = teclado.readLine();

            System.out.println("Por favor ingrese la edad sugerida para la novela:");
            int edadSugerida = Integer.parseInt(teclado.readLine());

            Novela novela = new Novela(CommonOperacion.generateID(), titulo, cantidadEjemplares, cantidadPrestados, cantidadDisponible, genero, edadSugerida);
            novela.setAutor(autor);
            NovelaOperaciones.crearNovela(novela);
        }
    }

    public void ingresoactualizarNovela() throws IOException, SQLException {
        System.out.println("Ingrese el ID de la novela que desea actualizar:");
        String idNovela = teclado.readLine();

        Novela novela = novelas.get(idNovela);

        if (novela == null) {
            System.out.println("La novela con el ID especificado no existe.");
            return;
        }

        System.out.println("Opciones de actualización:");
        System.out.println("1. Actualizar título");
        System.out.println("2. Actualizar género");
        System.out.println("3. Actualizar edad sugerida");
        System.out.println("4. Actualizar cantidad de ejemplares");
        System.out.println("5. Actualizar cantidad prestados");
        System.out.println("6. Actualizar autor");

        int opcion = Integer.parseInt(teclado.readLine());

        switch (opcion) {
            case 1:
                System.out.println("Ingrese el nuevo título:");
                String nuevoTitulo = teclado.readLine();
                novela.setTitulo(nuevoTitulo);
                break;
            case 2:
                System.out.println("Ingrese el nuevo género:");
                String nuevoGenero = teclado.readLine();
                novela.setGenero(nuevoGenero);
                break;
            case 3:
                System.out.println("Ingrese la nueva edad sugerida:");
                int nuevaEdadSugerida = Integer.parseInt(teclado.readLine());
                novela.setEdadSugerida(nuevaEdadSugerida);
                break;
            case 4:
                System.out.println("Ingrese la nueva cantidad de ejemplares:");
                int nuevaCantidadEjemplares = Integer.parseInt(teclado.readLine());
                novela.setCantidadEjemplares(nuevaCantidadEjemplares);
                break;
            case 5:
                System.out.println("Ingrese la nueva cantidad de ejemplares prestados:");
                int nuevaCantidadPrestados = Integer.parseInt(teclado.readLine());
                novela.setCantidadPrestados(nuevaCantidadPrestados);
                break;
            case 6:
                System.out.println("Ingrese el nuevo ID del autor:");
                String nuevoIdAutor = teclado.readLine();
                Autor autor = autorOp.buscarAutorId(nuevoIdAutor);
                if (autor != null) {
                    novela.setAutor(autor);
                } else {
                    System.out.println("No se encontró ningún autor con el ID especificado.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

        // Actualizar la novela en la base de datos
        actualizarNovela(novela);
    }

    private void getNovelas() throws SQLException {
        String query = "SELECT * FROM Novela";
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatement();
        ResultSet resultSet = mySqlOperation.getResulset();

        while (resultSet.next()) {
            Novela novela = new Novela();
            novela.setID(resultSet.getString("ID"));
            novela.setTitulo(resultSet.getString("Titulo"));
            novela.setGenero(resultSet.getString("Genero"));
            novela.setEdadSugerida(resultSet.getInt("Edad_sugerida"));
            novela.setCantidadEjemplares(resultSet.getInt("Cantidad_ejemplares"));
            novela.setCantidadPrestados(resultSet.getInt("Cantidad_prestados"));
            novela.setCantidadDisponible(resultSet.getInt("Cantidad_disponible"));
            // Suponiendo que el ID del autor está en una columna llamada "Autor_ID"
            String autorId = resultSet.getString("Autor_ID");
            Autor autor = autorOp.buscarAutorId(autorId);
            novela.setAutor(autor);
            novelas.put(novela.getID(), novela);
        }
    }

}
