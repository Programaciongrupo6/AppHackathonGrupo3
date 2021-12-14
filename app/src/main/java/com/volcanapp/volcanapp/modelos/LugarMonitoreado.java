package com.volcanapp.volcanapp.modelos;

public class LugarMonitoreado {
    String id;
    String nombre;

    public LugarMonitoreado() {

    }

    public LugarMonitoreado(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
