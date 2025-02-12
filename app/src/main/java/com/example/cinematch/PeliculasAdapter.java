package com.example.cinematch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinematch.models.Pelicula;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.text.DecimalFormat;
import java.util.List;

public class PeliculasAdapter extends RecyclerView.Adapter<PeliculasAdapter.PeliculaViewHolder> {

    private final Context context;
    private final List<Pelicula> peliculas;
    private final OnItemClickListener listener;

    // Constructor
    public PeliculasAdapter(Context context, List<Pelicula> peliculas, OnItemClickListener listener) {
        this.context = context;
        this.peliculas = peliculas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PeliculaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pelicula, parent, false);
        return new PeliculaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaViewHolder holder, int position) {
        Pelicula pelicula = peliculas.get(position);

        // Vincular los datos a los elementos de la tarjeta
        holder.txtTitulo.setText(pelicula.getTitulo());

        // Formatear la fecha para mostrar solo el año
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = originalFormat.parse(pelicula.getFechaEstreno());

            // Verificar si la fecha es válida
            if (date != null) {
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
                String year = yearFormat.format(date);
                holder.txtFecha.setText(year);
            } else {
                // Usar recurso de cadena en lugar de texto literal
                holder.txtFecha.setText(context.getString(R.string.fecha_desconocida));
            }
        } catch (Exception e) {
            // Si hubo un error en el parseo, usar recurso de cadena
            holder.txtFecha.setText(context.getString(R.string.fecha_desconocida));
        }

        // Formatear el promedio de votos a 1 decimal
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String puntuacion = decimalFormat.format(pelicula.getPromedioVotos());
        holder.txtPuntuacion.setText(puntuacion);

        // Cargar imagen con Picasso
        String imageUrl = "https://image.tmdb.org/t/p/w500" + pelicula.getRutaPoster();
        Picasso.get().load(imageUrl).into(holder.imgPoster);

        // Configurar clic en la tarjeta
        holder.itemView.setOnClickListener(v -> listener.onItemClick(pelicula));
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    // Clase ViewHolder debe ser public o protected si es accesible fuera del paquete
    public static class PeliculaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView txtTitulo, txtFecha, txtPuntuacion;

        public PeliculaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtPuntuacion = itemView.findViewById(R.id.txtPuntuacion);
        }
    }

    // Interfaz para manejar el clic en los elementos
    public interface OnItemClickListener {
        void onItemClick(Pelicula pelicula);
    }
}