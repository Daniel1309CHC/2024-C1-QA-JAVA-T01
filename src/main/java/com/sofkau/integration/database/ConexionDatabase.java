package com.sofkau.integration.database;

import com.sofkau.integration.database.mysql.MySqlOperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConexionDatabase {


    private static final String server = "localhost:3306";;
    private static final String databaseName = "biblioteca_pinguinera";
    private static final String user = "root";;
    private static final String password = "1234";;

    private static final MySqlOperation mySqlOperation = new MySqlOperation();

    public ConexionDatabase() {

    }

    public static void openConnection(){
        mySqlOperation.setServer(server);
        mySqlOperation.setDataBaseName(databaseName);
        mySqlOperation.setUser(user);
        mySqlOperation.setPassword(password);
        System.out.println("Se conecta a la BD"+databaseName);
    }

    public static Connection getConnection (){
        return mySqlOperation.getConnection();
    }

    public static MySqlOperation getMySqlOperation(){
        return mySqlOperation;
    }
    public void closeConnection() throws SQLException {
        mySqlOperation.close();
    }

}
