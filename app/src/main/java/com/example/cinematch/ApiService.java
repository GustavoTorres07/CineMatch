package com.example.cinematch;

import com.example.cinematch.models.Pelicula;
import com.example.cinematch.models.PeliculaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // Endpoint para obtener las películas de tendencias de la semana
    @GET("trending/movie/week")
    Call<PeliculaResponse> obtenerPeliculasTendencia (
            @Query("api_key") String apiKey,   // Clave de API
            @Query("language") String language, // Idioma de la respuesta
            @Query("time_window") String timeWindow
    );


    @GET("movie/{movie_id}")
    Call<Pelicula> obtenerDetallePelicula(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

}
