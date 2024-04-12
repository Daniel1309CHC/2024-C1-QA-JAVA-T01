package com.sofkau.Operaciones;

import com.sofkau.RolUsuario;
import com.sofkau.integration.database.ConexionDatabase;
import com.sofkau.integration.database.mysql.MySqlOperation;
import com.sofkau.modelos.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import  com.sofkau.Operaciones.CommonOperacion;

public class UsuarioOperaciones {

    private MySqlOperation mySqlOperation =  ConexionDatabase.getMySqlOperation();

    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

    private Map<String, Usuario> usuarios = new HashMap<>();


    private Usuario usuarioActual = new Usuario();

    private BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

    private ResultSet result;


    public UsuarioOperaciones() throws SQLException {
        this.getUsuarios();
    }

    private void crearUsuario(Usuario usuario)  throws SQLException {
        String query = String.format ("INSERT INTO Usuario (ID, Nombre, Correo, Contrasena, Rol, Documento, Fecha_nacimiento) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')", usuario.getID(),usuario.getNombre(),usuario.getCorreo(),usuario.getContrasena(),usuario.getRol(),usuario.getDocumento(),formato.format(usuario.getFechaNacimiento()));
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatementVoid();
        usuarios.put(usuario.getID(),usuario);
        System.out.println("Ususario creado con exito");
    }

    public void agregarUsuario(RolUsuario rol) throws IOException, ParseException, SQLException {
        System.out.println("Por favor ingrese su nombre");
        String nombre = teclado.readLine();

        System.out.println("Por favor ingrese su correo");
        String correo = teclado.readLine();

        System.out.println("Por favor ingrese su contraseña");
        String contrasena = teclado.readLine();

        System.out.println("Por favor ingrese su documento");
        String documento = teclado.readLine();

        System.out.println("Por favor ingrese su fecha de nacimiento (yyyy-MM-dd)");
        Date fechaNacimiento = formato.parse(teclado.readLine());

        Usuario usuario = new Usuario(CommonOperacion.generateID(), nombre, correo, contrasena, rol.toString(), documento, fechaNacimiento);
        crearUsuario(usuario);
    }

    public void getUsuarios() throws SQLException {
        String query = "Select * from biblioteca_pinguinera.usuario";
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatement();
        result = mySqlOperation.getResulset();

        while (result.next()){

            Usuario usuario = new Usuario();

           usuario.setID(result.getString(1));
           usuario.setNombre(result.getString(2));
           usuario.setCorreo(result.getString(3));
           usuario.setContrasena(result.getString(4));
           usuario.setRol(result.getString(5));
           usuario.setDocumento(result.getString(6));
           usuario.setFechaNacimiento(result.getDate(7));

           usuarios.put(usuario.getID(),usuario);

        }
    }

    public void listarUsuarios () throws SQLException {
        String query = "Select * from biblioteca_pinguinera.usuario";
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatement();
        mySqlOperation.printResulset();
    }

    public boolean validarUsuario (String correo, String contrasena){
        Optional<Usuario> usuarioValidado = usuarios.values().stream()
                .filter(usuario -> usuario.getCorreo().equals(correo) && usuario.getContrasena().equals(contrasena))
                .findFirst();

        if(!usuarioValidado.isEmpty()){
            usuarioActual = usuarioValidado.get();
        }

        return !usuarioValidado.isEmpty();

    }

    public Usuario getUsuarioActual(){
        return this.usuarioActual;
    }


}
