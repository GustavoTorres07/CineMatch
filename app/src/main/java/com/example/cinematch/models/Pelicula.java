package com.example.cinematch.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Pelicula implements Serializable {
    @SerializedName("id")
    private final int id;  // Declarar como final

    @SerializedName("title")
    private final String titulo;  // Declarar como final

    @SerializedName("poster_path")
    private final String rutaPoster;

    @SerializedName("release_date")
    private final String fechaEstreno;

    @SerializedName("popularity")
    private final double popularidad;

    @SerializedName("vote_average")
    private final double promedioVotos;

    @SerializedName("overview")
    private final String descripcion;

    // Constructor con parámetros
    public Pelicula(int id, String titulo, String rutaPoster, String fechaEstreno, double popularidad, double promedioVotos, String descripcion, List<String> genero) {
        this.id = id;
        this.titulo = titulo;
        this.rutaPoster = rutaPoster;
        this.fechaEstreno = fechaEstreno;
        this.promedioVotos = promedioVotos;
        this.popularidad = popularidad;
        this.descripcion = descripcion;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getRutaPoster() {
        return rutaPoster;
    }

    public String getFechaEstreno() {
        return fechaEstreno;
    }

    public double getPromedioVotos() {
        return promedioVotos;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
