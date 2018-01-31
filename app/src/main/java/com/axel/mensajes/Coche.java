package com.axel.mensajes;

import java.io.Serializable;

public class Coche implements Serializable{

    //Propiedades del coche
    private float litros = 0;
    private String propietario = null;


    /***************** Constructores ****************/

    public Coche(float litros, String propietario){
        this.propietario = propietario;
        this.litros = litros;
    }


    /***************** Getters y setters ****************/

    public float getLitros() {
        return litros;
    }

    public void setLitros(float litros) {
        this.litros = litros;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

}

