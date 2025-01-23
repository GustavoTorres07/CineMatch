package com.example.cinematch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinematch.models.Pelicula;
import com.example.cinematch.models.PeliculaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements PeliculasAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PeliculasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewPeliculas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Hacer la solicitud a la API para obtener las películas recomendadas de la semana
        obtenerPeliculas();
    }

    // Método para hacer la solicitud a la API y obtener las películas
    private void obtenerPeliculas() {
        // Configuración de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/") // Base URL de la API
                .addConverterFactory(GsonConverterFactory.create()) // Conversión automática de JSON a objetos Java
                .build();

        // Crear una instancia de la interfaz ApiService
        ApiService apiService = retrofit.create(ApiService.class);

        // Llamar al endpoint que obtiene las películas más populares de la semana
        Call<PeliculaResponse> call = apiService.obtenerPeliculasTendencia("2906707de840f7248be0553932f13808", "es-AR", "week");

        // Hacer la llamada de manera asíncrona
        call.enqueue(new Callback<PeliculaResponse>() {
            @Override
            public void onResponse(@NonNull Call<PeliculaResponse> call, @NonNull Response<PeliculaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtener los resultados directamente sin usar una variable global
                    List<Pelicula> peliculas = response.body().getResults();
                    if (peliculas != null && !peliculas.isEmpty()) {
                        adapter = new PeliculasAdapter(MainActivity.this, peliculas, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(MainActivity.this, "No hay películas disponibles", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error al cargar las películas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PeliculaResponse> call, @NonNull Throwable t) {
                // Mostrar un mensaje de error si la solicitud falla
                Toast.makeText(MainActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(Pelicula pelicula) {
        Intent intent = new Intent(MainActivity.this, DetallePeliculaActivity.class);
        intent.putExtra("pelicula_id", pelicula.getId()); // Pasar el ID de la película seleccionada
        startActivity(intent);
    }




}
