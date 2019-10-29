package com.danieldonato.instagram.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danieldonato.instagram.R;
import com.danieldonato.instagram.model.Postagem;
import com.danieldonato.instagram.model.Usuario;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisualizarPostagemActivity extends AppCompatActivity {

    private TextView textPerfilPostagem, textQteCurtidasPostagem,
        textDescricaoPostagem, textVisualizarComentariosPostagem;
    private ImageView imagePostagemSelecionada;
    private CircleImageView imagePerfilPostagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_postagem);
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);

        toolbar.setTitle("Visualizar Postagem");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_fechar_preto_24dp);

        inicializarComponentes();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Postagem postagem = (Postagem) bundle.getSerializable("postagem");
            Usuario usuario = (Usuario) bundle.getSerializable("usuario");

            if(usuario.getCaminhoFoto() != null){
                Uri uri = Uri.parse(usuario.getCaminhoFoto());
                Glide.with(VisualizarPostagemActivity.this)
                        .load(uri)
                        .into(imagePerfilPostagem);
            }else {
                imagePerfilPostagem.setImageResource(R.drawable.avatar);
            }
            textPerfilPostagem.setText(usuario.getNome());
            Uri uriPostagem = Uri.parse(postagem.getCaminhoFoto());
            Glide.with(VisualizarPostagemActivity.this)
                    .load(uriPostagem)
                    .into(imagePostagemSelecionada);
            textDescricaoPostagem.setText(postagem.getDescricao());
        }
    }

    private void inicializarComponentes(){
        textPerfilPostagem = findViewById(R.id.textPerfilPostagem);
        textQteCurtidasPostagem = findViewById(R.id.textQuantidadeCurtidas);
        textDescricaoPostagem = findViewById(R.id.textDescricaoPostagem);
        textVisualizarComentariosPostagem = findViewById(R.id.textVisualizarComentariosPostagem);
        imagePostagemSelecionada = findViewById(R.id.imagePostagemSelecionada);
        imagePerfilPostagem = findViewById(R.id.imagePerfilPostagem);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
