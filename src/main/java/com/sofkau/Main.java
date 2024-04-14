package com.sofkau;

import com.sofkau.Operaciones.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.sofkau.enums.TipoLectura;
import com.sofkau.integration.database.ConexionDatabase;
import com.sofkau.modelos.Usuario;
import com.sofkau.enums.RolUsuario;


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

        // Clase que permite realizar operaciones sobre los libros
        LibroOperaciones libroOp = new LibroOperaciones();

        // Clase que permite realizar operaciones sobre la novela
        NovelaOperaciones novelaOp = new NovelaOperaciones();

        // Clase que permite realizar operaciones sobre los prestamos
        PrestamoOperaciones prestamoOp = new PrestamoOperaciones();

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
                                        System.out.println("3. Actualizar prestamo por correo");
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
                                        System.out.println("OPCIONES");
                                        System.out.println("1. Realizar prestamo libro");
                                        System.out.println("2. Realizar prestamo novela");
                                        System.out.println("3. Actualizar libros");
                                        System.out.println("4. Actualizar novelas");
                                        int op = Integer.parseInt(teclado.readLine());

                                        switch (op) {
                                            case 1:
                                                libroOp.listarLibrosDisponibles();
                                                prestamoOp.realizarPrestamo(usuarioOp.getUsuarioActual(), TipoLectura.LIBRO);
                                                break;
                                            case 2:
                                                novelaOp.listarNovelasDisponibles();
                                                prestamoOp.realizarPrestamo(usuarioOp.getUsuarioActual(),TipoLectura.NOVELA);
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