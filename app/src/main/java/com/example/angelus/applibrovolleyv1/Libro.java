package com.example.angelus.applibrovolleyv1;

/**
 * Created by Angelus on 11/07/2017.
 */

public class Libro {
    private  int idlibros;
    private String nombre;
    private String descripcion;
    private String foto;

    public Libro() {
    }

    public Libro(int idlibros, String nombre, String descripcion, String foto) {
        this.idlibros = idlibros;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "{" +
                "idlibros:" + idlibros +
                ", nombre:'" + nombre + '\'' +
                ", descripcion:'" + descripcion + '\'' +
                ", foto:'" + foto + '\'' +
                '}';
    }

    public int getIdlibros() {
        return idlibros;
    }

    public void setIdlibros(int idlibros) {
        this.idlibros = idlibros;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
