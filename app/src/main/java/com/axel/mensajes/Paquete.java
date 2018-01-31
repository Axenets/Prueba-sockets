package com.axel.mensajes;

import com.axel.mensajes.Casa;
import com.axel.mensajes.Coche;

import java.io.Serializable;

public class Paquete implements Serializable{

    //Definición de los tipos de paquetes
    public static final int TIPO_ERROR = 0;
    public static final int TIPO_CASA = 1;
    public static final int TTPO_COCHE = 2;

    //Tipo del paquete actual
    private int tipo = 0;

    //Contenido del paquete (dependiendo del tipo de paquete tendrá uno u otro
    private Casa casa = null;
    private Coche coche = null;

    /* ***************** Constructores **********************/

    public Paquete(Casa casa) {
        this.tipo = TIPO_CASA;
        this.casa = casa;
    }

    public Paquete(Coche coche){
        this.tipo = TTPO_COCHE;
        this.coche = coche;
    }


    /* ****************** Getters y setters *******************/

    public int getTipo() {
        return tipo;
    }

    public Casa getCasa() {
        return casa;
    }

    public Coche getCoche() {
        return coche;
    }
}
