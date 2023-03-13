package com.millan.ecartillav112;

import java.io.Serializable;

public class ListElement implements Serializable {
    public String color;
    public String accion;
    public String informacion;
    public String fecha;


    public ListElement(String color, String accion, String informacion, String fecha) {
        this.color = color;
        this.accion = accion;
        this.informacion = informacion;
        this.fecha = fecha;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
