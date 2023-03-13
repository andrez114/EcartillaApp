package com.millan.ecartillav112;

import java.io.Serializable;

public class Articulo implements Serializable {
    private String titulo;
    private String contenido;
    private String fecha;
    private String imageUrl;

    public Articulo(){

    }

    public Articulo(String titulo, String contenido, String fecha){
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.imageUrl = imageUrl;

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
