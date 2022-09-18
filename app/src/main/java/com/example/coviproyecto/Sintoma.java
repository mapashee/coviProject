package com.example.coviproyecto;

public class Sintoma {
    private String Nombre;
    private int ID;

    public Sintoma(String nombre, int ID) {
        Nombre = nombre;
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
