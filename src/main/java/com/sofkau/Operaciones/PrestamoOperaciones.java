package com.sofkau.Operaciones;

import com.sofkau.EstadoPrestamo;
import com.sofkau.TipoLectura;
import com.sofkau.integration.database.ConexionDatabase;
import com.sofkau.integration.database.mysql.MySqlOperation;
import com.sofkau.modelos.Autor;
import com.sofkau.modelos.Libro;
import com.sofkau.modelos.Prestamo;
import com.sofkau.modelos.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrestamoOperaciones {

    private final static MySqlOperation mySqlOperation =  ConexionDatabase.getMySqlOperation();

    private final static Connection conexion = ConexionDatabase.getConnection();
    private static Map<String, Libro> libros = new HashMap<>();
    private static Map<String, Autor> autores = new HashMap<>();
    private AutorOperaciones autorOp;
    private BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
    private static ResultSet result;

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
            System.out.println("Por favor ingrese el ID de la novela (si aplica, de lo contrario deje este campo en blanco):");
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



}
