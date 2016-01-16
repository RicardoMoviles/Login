package com.restrepo.ricardo.login;

import java.net.PasswordAuthentication;

/**
 * Created by Usuario on 12/01/2016.
 */
public class Cliente {
    private String Nombre;
    private String Correo;
    private String Contraseña;

    public Cliente(){

    }

    public Cliente(String nombre, String correo, String contraseña) {
        this.Nombre = nombre;
        this.Correo = correo;
        this.Contraseña = contraseña;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public String getContraseña() {
        return Contraseña;
    }
}