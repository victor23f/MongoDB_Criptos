package com.example.mongodb_criptos;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Criptomoneda implements Serializable {//implements Parcelable
    private ArrayList<Criptomoneda> arrayCriptomonedas=new ArrayList<>();

    private String token, nombre;
    private String precio;

    public Criptomoneda(String token, String nombre, String precio) {
        this.token = token;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public ArrayList<Criptomoneda> getArrayCriptomonedas() {
        return arrayCriptomonedas;
    }

    public void setArrayCriptomonedas(ArrayList<Criptomoneda> arrayCriptomonedas) {
        this.arrayCriptomonedas = arrayCriptomonedas;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre ;
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
    public static final Parcelable.Creator<Criptomoneda> CREATOR = new Parcelable.Creator<Criptomoneda>() {
        @Override
        public Criptomoneda createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public Criptomoneda[] newArray(int i) {
            return new Criptomoneda[0];
        }
    };*/
}