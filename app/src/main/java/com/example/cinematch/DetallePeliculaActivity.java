package com.example.cinematch;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinematch.models.Pelicula;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetallePeliculaActivity extends AppCompatActivity {

    private TextView txtTitulo, txtDescripcion, txtFecha, txtPuntuacion;
    private ImageView imgPoster;
    private Button btnVolverAtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);

        // Referencias a las vistas
        txtTitulo = findViewById(R.id.txtTituloDetalle);
        txtDescripcion = findViewById(R.id.txtDescripcionDetalle);
        txtFecha = findViewById(R.id.txtFechaDetalle);
        txtPuntuacion = findViewById(R.id.txtPuntuacionDetalle);
        imgPoster = findViewById(R.id.imgPosterDetalle);
        btnVolverAtras = findViewById(R.id.btnVolverAtras);


        // Obtener el id de la película del Intent
        int peliculaId = getIntent().getIntExtra("pelicula_id", -1);

        if (peliculaId != -1) {
            cargarDetallePelicula(peliculaId);
        } else {
            Toast.makeText(this, "Error: No se recibió el ID de la película", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad si no hay ID válido
        }
        // Configurar el botón "Volver atrás"
        btnVolverAtras.setOnClickListener(v -> {
            // Cerrar la actividad y volver a la anterior (MainActivity)
            finish();
        });
    }

    private void cargarDetallePelicula(int peliculaId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Construir la llamada
        Call<Pelicula> call = apiService.obtenerDetallePelicula(
                peliculaId,
                "2906707de840f7248be0553932f13808",
                "es-AR"
        );

        call.enqueue(new Callback<Pelicula>() {  // Especificar que es retrofit2.Callback
            @Override
            public void onResponse(@NonNull Call<Pelicula> call, @NonNull Response<Pelicula> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarDetalle(response.body());
                } else {
                    Toast.makeText(DetallePeliculaActivity.this,
                            "Error al obtener los detalles: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Pelicula> call, @NonNull Throwable t) {
                Toast.makeText(DetallePeliculaActivity.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDetalle(Pelicula pelicula) {
        txtTitulo.setText(pelicula.getTitulo());
        txtDescripcion.setText(pelicula.getDescripcion() != null ?
                pelicula.getDescripcion() : "Sin descripción disponible");
        txtFecha.setText(pelicula.getFechaEstreno());
        txtPuntuacion.setText(String.valueOf(pelicula.getPromedioVotos()));

        String imageUrl = "https://image.tmdb.org/t/p/w500" + pelicula.getRutaPoster();

        if (pelicula.getRutaPoster() != null) {
            Picasso.get()
                    .load(imageUrl)
                    .into(imgPoster);
        }
    }
}
