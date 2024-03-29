package com.danieldonato.instagram.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danieldonato.instagram.R;
import com.danieldonato.instagram.model.Comentario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComentario extends RecyclerView.Adapter<AdapterComentario.MyViewHolder> {

    private List<Comentario> listaComentarios;
    private Context context;

    public AdapterComentario(List<Comentario> listaComentarios, Context context) {
        this.listaComentarios = listaComentarios;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_comentario, viewGroup, false);
        return new AdapterComentario.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Comentario comentario = listaComentarios.get(i);
        myViewHolder.nomeUsuario.setText(comentario.getNomeUsuario());
        myViewHolder.comentario.setText(comentario.getComentario());
        if(comentario.getCaminhoFoto() != null && !comentario.getCaminhoFoto().isEmpty()) {
            Glide.with(context)
                    .load(comentario.getCaminhoFoto())
                    .into(myViewHolder.imagemPerfil);
        }else {
            myViewHolder.imagemPerfil.setImageResource(R.drawable.avatar);
        }
    }

    @Override
    public int getItemCount() {
        return listaComentarios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imagemPerfil;
        TextView nomeUsuario, comentario;

        public MyViewHolder(View itemView){
            super(itemView);
            imagemPerfil = itemView.findViewById(R.id.imageFotoComentario);
            nomeUsuario = itemView.findViewById(R.id.textNomeComentario);
            comentario = itemView.findViewById(R.id.textComentario);

        }

    }
}
