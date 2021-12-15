package com.volcanapp.volcanapp.modelos;

public class LugarMonitoreado {
    String id;
    String nombre;
    String descripcion;
    String tipo_alarma;
    String ultima_actualizacion;
    String latitud;
    String longitud;

    public LugarMonitoreado() {

    }

    public LugarMonitoreado(String id,
                            String nombre,
                            String descripcion,
                            String tipo_alarma,
                            String ultima_actualizacion,
                            String latitutud,
                            String longitud) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo_alarma = tipo_alarma;
        this.ultima_actualizacion = ultima_actualizacion;
        this.latitud = latitutud;
        this.longitud = longitud;
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


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo_alarma() {
        return tipo_alarma;
    }

    public void setTipo_alarma(String tipo_alarma) {
        this.tipo_alarma = tipo_alarma;
    }

    public String getUltima_actualizacion() {
        return ultima_actualizacion;
    }

    public void setUltima_actualizacion(String ultima_actualizacion) {
        this.ultima_actualizacion = ultima_actualizacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo_alarma='" + tipo_alarma + '\'' +
                ", ultima_actualizacion='" + ultima_actualizacion + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }
}
