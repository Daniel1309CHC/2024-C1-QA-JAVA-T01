package com.sofkau.Operaciones;

import com.sofkau.enums.EstadoPrestamo;
import com.sofkau.enums.TipoLectura;
import com.sofkau.integration.database.ConexionDatabase;
import com.sofkau.integration.database.mysql.MySqlOperation;
import com.sofkau.modelos.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PrestamoOperaciones {

    private final static MySqlOperation mySqlOperation =  ConexionDatabase.getMySqlOperation();

    private final static Connection conexion = ConexionDatabase.getConnection();
    private  Map<String, Libro> libros = new HashMap<>();
    private  Map<String, Novela> novelas = new HashMap<>();

    private Map<String, Prestamo> prestamos = new HashMap<>();

    private NovelaOperaciones novelaOp;
    private LibroOperaciones libroOp;

    private UsuarioOperaciones usuarioOp;

    private BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
    private static ResultSet result;

    public PrestamoOperaciones() throws SQLException {
        this.libros = libroOp.getLibros();
        this.novelas = novelaOp.getNovelas();
    }

    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    public void realizarPrestamo(Usuario usuario, TipoLectura tipoLectura) throws IOException, ParseException, SQLException {

        String libroId = null, novelaId= null;

        if(tipoLectura == TipoLectura.LIBRO){
            // Solicitar al usuario el ID del libro
            System.out.println("Por favor ingrese el ID del libro:");
            libroId = teclado.readLine();
        }

        if(tipoLectura == TipoLectura.NOVELA){
            // solicitar ID novela
            System.out.println("Por favor ingrese el ID de la novela ):");
            novelaId = teclado.readLine();
        }

        // Obtener la fecha actual como fecha de préstamo
        Date fechaPrestamo = new Date();

        // Solicitar al usuario la fecha de devolución y validarla
        Date fechaDevolucion = new Date();
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.println("Por favor ingrese la fecha de devolución (formato: yyyy-MM-dd):");
            String fechaDevolucionIngresada = teclado.readLine();
            fechaDevolucion = formato.parse(fechaDevolucionIngresada);

            // calcula la diferencia en milisegundos y lo convierte a días
            long diferenciaEnDias = (fechaDevolucion.getTime() - fechaPrestamo.getTime()) / (1000 * 60 * 60 * 24);

            // Validar que la fecha de devolución no sea menor que la fecha actual y sea máximo 15 días después de la fecha de préstamo
            if (fechaDevolucion.after(fechaPrestamo) && diferenciaEnDias <= 15) {
                fechaValida = true;
            } else {
                System.out.println("Por favor verifique la fecha ingresada");
            }
        }

        // Establecer el estado del préstamo como "activo" por defecto
        String estado = "activo";

        // Crear una instancia de Prestamo con los datos ingresados
        Prestamo prestamo = new Prestamo(CommonOperacion.generateID(), usuario.getID(), libroId, novelaId, fechaPrestamo, fechaDevolucion, EstadoPrestamo.SOLICITADO.toString());

        // Llamar al método para crear el préstamo en la base de datos
        crearPrestamo(prestamo);
    }

    public void crearPrestamo(Prestamo prestamo) throws SQLException {

        String INSERT_PRESTAMO_SQL = "INSERT INTO Prestamo (ID, Usuario_ID, Libro_ID, Novela_ID, Fecha_prestamo, Fecha_devolucion, Estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(INSERT_PRESTAMO_SQL);
        ps.setString(1, prestamo.getID());
        ps.setString(2, prestamo.getUsuarioID());
        ps.setString(3, prestamo.getLibroID());
        ps.setString(4, prestamo.getNovelaID());
        ps.setDate(5, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
        ps.setDate(6, new java.sql.Date(prestamo.getFechaDevolucion().getTime()));
        ps.setString(7, prestamo.getEstado());
        ps.executeUpdate();
        System.out.println("Se ingresó el préstamo correctamente");
    }

    public Map<String, Prestamo> getPrestamos() throws SQLException {
        String query = "SELECT * FROM Prestamo";
        mySqlOperation.setSqlStatement(query);
        mySqlOperation.executeSqlStatement();
        ResultSet resultSet = mySqlOperation.getResulset();


        while (resultSet.next()) {
            Prestamo prestamo = new Prestamo();
            prestamo.setID(resultSet.getString("ID"));
            prestamo.setUsuarioID(resultSet.getString("Usuario_ID"));
            prestamo.setLibroID(resultSet.getString("Libro_ID"));
            prestamo.setNovelaID(resultSet.getString("Novela_ID"));
            prestamo.setFechaPrestamo(resultSet.getDate("Fecha_prestamo"));
            prestamo.setFechaDevolucion(resultSet.getDate("Fecha_devolucion"));
            prestamo.setEstado(resultSet.getString("Estado"));
            prestamos.put(prestamo.getID(), prestamo);
        }

        return prestamos;
    }

    // Busca un prestamo por el correo de un usuario
    public Map<String, Prestamo> buscarPrestamosCorreoUsuario(String correo) throws SQLException {
        UsuarioOperaciones usuarioOp = new UsuarioOperaciones();
        Usuario usuario = usuarioOp.buscarUsuarioCorreo(correo);

        Map<String, Prestamo> prestamosUsuario = new HashMap<>();

        // Si el usuario existe entonces se crea un Stream que devuelve un hashmap con todos los prestamos de ese usuario
        if (usuario != null) {
            prestamosUsuario = prestamos.values().stream()
                    .filter(prestamo -> prestamo.getUsuarioID().equals(usuario.getID()))
                    .collect(Collectors.toMap(Prestamo::getID, prestamo -> prestamo));
        }
        if(!prestamosUsuario.isEmpty()){
            return  prestamosUsuario;
        }
        return null;
    }



    public void mostrarPrestamo(Prestamo prestamo) {
        if (prestamo != null) {
            System.out.println("Información del préstamo:");
            System.out.println("ID: " + prestamo.getID());
            System.out.println("Nombre del usuario: " + usuarioOp.buscarUsuarioPorId(prestamo.getUsuarioID()).getNombre());
            System.out.println("Título del libro: " + libros.get(prestamo.getLibroID()).getTitulo());
            System.out.println("Titulo de la novela: " + novelas.get(prestamo.getNovelaID()).getTitulo());
            System.out.println("Fecha de préstamo: " + formato.format(prestamo.getFechaPrestamo()));
            System.out.println("Fecha de devolución: " + formato.format(prestamo.getFechaDevolucion()));
            System.out.println("Estado: " + prestamo.getEstado());
        } else {
            System.out.println("No se encontró ningún préstamo para el usuario especificado.");
        }
    }

    public void cambiarEstadoPrestamo(Prestamo prestamo, EstadoPrestamo nuevoEstado) throws SQLException {
            prestamo.setEstado(nuevoEstado.toString());
            String query = String.format("UPDATE Prestamo SET Estado = '%s' WHERE ID = '%s'", nuevoEstado.toString(), prestamo.getID());
            mySqlOperation.setSqlStatement(query);
            mySqlOperation.executeSqlStatementVoid();
            System.out.println("Se actualizó el estado del préstamo correctamente.");
    }

/*    public void ingresoActualizacionPrestamo() throws IOException, SQLException {
        System.out.println("Ingrese el correo del usuario:");
        String correoUsuario = teclado.readLine();

        Map<String, Prestamo> prestamosUsuario = buscarPrestamosCorreoUsuario(correoUsuario);

        if (prestamosUsuario.isEmpty()) {
            System.out.println("No hay prestamos para este usuario");
            return;
        }

        System.out.println("Opciones de actualización Prestamo:");
        System.out.println("2. Cambiar a 'Realizado'");
        System.out.println("3. Cambiar a 'Finalizado'");
        int opcion = Integer.parseInt(teclado.readLine());

        EstadoPrestamo nuevoEstado = null;

        switch (opcion) {
            case 1:
                nuevoEstado = EstadoPrestamo.REALIZADO;
                break;
            case 2:
                nuevoEstado = EstadoPrestamo.FINALIZADO;
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        for (Prestamo prestamo : prestamosUsuario.values()) {
            if (prestamo.getEstado().equals()) {
                System.out.println(libro);
            }
        }

        // Actualizar el estado del préstamo
        cambiarEstadoPrestamo(prestamo, nuevoEstado);
    }*/



}
