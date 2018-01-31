package com.axel.mensajes;

import java.io.Serializable;

public class Casa implements Serializable{

    //Propiedades de la casa
    private int pisos = 0;
    private String direccion = null;


    /***************** Constructores ****************/

    public Casa(int pisos, String direccion){
        this.pisos = pisos;
        this.direccion = direccion;
    }


    /***************** Getters y setters ****************/

    public int getPisos() {
        return pisos;
    }

    public void setPisos(int pisos) {
        this.pisos = pisos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDirección(String direccion) {
        this.direccion = direccion;
    }

}
