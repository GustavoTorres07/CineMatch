package com.example.cinematch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BienvenidaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        // Asegurarse de que el ActionBar está disponible
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name)); // Usar el recurso de cadena
        }
    }

    public void verRecomendaciones(View view) {
        Intent intent = new Intent(BienvenidaActivity.this, MainActivity.class);
        startActivity(intent); // Lanza la actividad principal
        finish(); // Opcional, si quieres cerrar la pantalla de bienvenida
    }
}
