package com.restrepo.ricardo.login;

import java.net.PasswordAuthentication;

/**
 * Created by Usuario on 12/01/2016.
 */
public class Cliente {
    private String nombre;
    private String edad;
    private String correo;
    private String password;


    public Cliente() {

    }

    public String getNombre() {
        return nombre;
    }

    public String getEdad() {
        return edad;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }
}
