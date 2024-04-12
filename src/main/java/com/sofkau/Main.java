package com.sofkau;

import com.github.javafaker.Faker;
import com.sofkau.Operaciones.*;
import com.sofkau.integration.database.mysql.MySqlOperation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.sofkau.integration.database.ConexionDatabase;
import com.sofkau.modelos.Usuario;
import com.sofkau.RolUsuario;


public class Main {


    public static void main(String[] args) throws SQLException {

        // Leer lo que ingresa el usuario por consola
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        // Formato para las fechas ingresadas
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        //Se abre la conexion
        ConexionDatabase.openConnection();


        // Clase que permite realizar operaciones sobre la clase autor
        AutorOperaciones autorOp = new AutorOperaciones();

        System.out.println("Se crean los autores");
        autorOp.generateAutores(5);

        //Clase que permite realizar operaciones sobre la clase Usuario
        UsuarioOperaciones usuarioOp = new UsuarioOperaciones();
        usuarioOp.getUsuarios();

        // Clase que permite operar sobre libros y novelas
    /*    TipoLecturaOperaciones lecOp = new TipoLecturaOperaciones();*/

        // Clase que permite realizar operaciones sobre los libros
        LibroOperaciones libroOp = new LibroOperaciones();

        // Clase que permite realizar operaciones sobre la novela
        NovelaOperaciones novelaOp = new NovelaOperaciones();





        RolUsuario rolUsuario;


        boolean a = true;
        int option;
        do {
            try {
                System.out.println("Opciónes");
                System.out.println("1. Iniciar sesión");
                System.out.println("2. Crear usuario");
                System.out.println("3: Salir");
                option = Integer.parseInt(teclado.readLine());

                switch (option) {
                    case 1: {
                        System.out.println("Por favor ingrese correo");
                        String correo = teclado.readLine();

                        System.out.println("Por favor ingrese su contraseña");
                        String contrasena = teclado.readLine();

                        if (usuarioOp.validarUsuario(correo,contrasena)){
                           Usuario usuarioActual = usuarioOp.getUsuarioActual();
                            boolean b = true;
                            int optionUsusuario;
                            do{
                                switch(usuarioActual.getRol()) {
                                    case "ADMINISTRADOR" :{
                                        System.out.println("OPCIONES");
                                        System.out.println("1. Ingresar libro o novela");
                                        System.out.println("2. Actualizar libro o novela");
                                        System.out.println("3. Listar libros");
                                        System.out.println("4. Listar novelas");
                                        System.out.println("3: Salir");
                                        int op = Integer.parseInt(teclado.readLine());
                                        switch (op) {
                                            case 1:
                                               usuarioOp.agregarUsuario(RolUsuario.ASISTENTE);
                                                break;
                                            case 2:

                                                break;
                                            case 3:
                                                b=false;
                                                break;
                                            default:
                                                System.out.println("Opción no reconocida");
                                        }
                                        break;
                                    }
                                    case "ASISTENTE" :{
                                        System.out.println("OPCIONES");
                                        System.out.println("1. Agregar libro");
                                        System.out.println("2. Agregar novela");
                                        System.out.println("3. Actualizar libros");
                                        System.out.println("4. Actualizar novelas");

                                        System.out.println("3: Salir");
                                        int op = Integer.parseInt(teclado.readLine());
                                        switch (op) {
                                            case 1:
                                                autorOp.listarAutores();
                                                libroOp.agregarLibro();
                                                break;
                                            case 2:
                                                autorOp.listarAutores();
                                                novelaOp.agregarNovela();
                                                break;
                                            case 3:
                                                libroOp.ingresoactualizarLibro();
                                            case 4:
                                                novelaOp.ingresoactualizarNovela();
                                            break;
                                            default:
                                                System.out.println("Opción no reconocida");
                                        }
                                        break;
                                    }
                                    case "LECTOR" :{
                                        System.out.println("455465");
                                        break;
                                    }

                                }

                            }while(b);
                        }else{
                            System.out.println("Por favor confirme sus credenciales");
                        }

                        break;
                    }
                    case 2:{

                        usuarioOp.agregarUsuario(RolUsuario.LECTOR);

                        break;
                    }
                    case 3: {
                        a = false;
                        break;
                    }
                    default: {
                        System.out.println("Por favor ingrese una opción valida");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        } while (a);

    }


}