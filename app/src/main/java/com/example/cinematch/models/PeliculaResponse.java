package com.example.cinematch.models;

import java.util.List;

public class PeliculaResponse {
    private int page; // Página actual de la respuesta
    private List<Pelicula> results; // Lista de películas

    // Constructor
    public PeliculaResponse(int page, List<Pelicula> results) {
        this.page = page;
        this.results = results;
    }

    // Getters y Setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Pelicula> getResults() {
        return results;
    }

    public void setResults(List<Pelicula> results) {
        this.results = results;
    }
}
