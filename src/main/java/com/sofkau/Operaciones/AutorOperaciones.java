package com.sofkau.Operaciones;

import com.github.javafaker.Faker;
import com.sofkau.integration.database.ConexionDatabase;
import com.sofkau.integration.database.mysql.MySqlOperation;
import com.sofkau.modelos.Autor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class AutorOperaciones {
    private MySqlOperation mySqlOperation =  ConexionDatabase.getMySqlOperation();

    private  Map<String, Autor> autores = new HashMap<>();


    private BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

    private  ResultSet result;

    public AutorOperaciones() throws SQLException {
        getAutores();
    }

    // Metodo que crea el autor en la BD
    private void crearAutor(Autor autor) throws SQLException {
        String query = String.format ("INSERT INTO Autor (ID, Nombre) VALUES ('%s', '%s')", autor.getID(),autor.getNombre());
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatementVoid();
        autores.put(autor.getID(),autor);
        System.out.println(autor);
    }


    // Metodo para gregar autor manualmente
    public void agregarAutor() throws IOException, ParseException, SQLException {
        System.out.println("Por favor ingrese el nombre del autor a registrar");
        String nombre = teclado.readLine();

        Autor autor = new Autor(CommonOperacion.generateID(), nombre);
        crearAutor(autor);
    }

    // se traen todos los autores y se guardan en la lista autores
    public Map<String, Autor> getAutores() throws SQLException {
        String query = "Select * from biblioteca_pinguinera.autor";
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatement();
        result = mySqlOperation.getResulset();

        while (result.next()){

            Autor autor = new Autor();

            autor.setID(result.getString(1));
            autor.setNombre(result.getString(2));

            autores.put(autor.getID(),autor);
        }

        return autores;
    }

    //Se generan el numero de autores que se le pase por parametros en la bd
    public void generateAutores(int numAutores) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numAutores; i++) {
            String nombre = faker.name().fullName();
            Autor autor = new Autor(CommonOperacion.generateID(), nombre);
            crearAutor(autor);
        }


    }

    public Autor buscarAutorNombre(String nombre) {
        for (Autor autor : autores.values()) {
            if (autor.getNombre().equalsIgnoreCase(nombre)) {
                return autor;
            }
        }
        return null; // Retorna null si no se encuentra el autor con el nombre especificado
    }

    public Autor buscarAutorId(String id) {
        for (Autor autor : autores.values()) {
            if (autor.getID().equalsIgnoreCase(id)) {
                return autor;
            }
        }
        return null; // Retorna null si no se encuentra el autor con el nombre especificado
    }

    public void listarAutores() {
        System.out.println("Listado de autores:");
        for (Autor autor : autores.values()) {
            System.out.println(autor);
        }
    }


}
