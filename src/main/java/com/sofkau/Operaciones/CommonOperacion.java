package com.sofkau.Operaciones;

import java.util.UUID;

public class CommonOperacion {

    public static String generateID(){
        UUID uuid = UUID.randomUUID();
        // Tomar los primeros 8 caracteres del UUID
        return uuid.toString().substring(0, 8);
    }


}
