package com.danieldonato.instagram.activity;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danieldonato.instagram.R;
import com.danieldonato.instagram.helper.ConfiguracaoFirebase;
import com.danieldonato.instagram.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilAmigoActivity extends AppCompatActivity {

    private Usuario usuarioSelecionado;
    private Button buttonAcaoPerfil;
    private CircleImageView imagePerfil;

    private DatabaseReference usuariosRef;
    private DatabaseReference usuarioAmigoRef;
    private ValueEventListener valueEventListenerPerfilAmigo;

    private TextView textPublicacoes, textSeguidores, textSeguindo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_amigo);

        usuariosRef = ConfiguracaoFirebase.getReferenceFirebase().child("usuarios");

        inicializarComponentes();

        buttonAcaoPerfil.setText("Seguir");

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_fechar_preto_24dp);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            usuarioSelecionado = (Usuario) bundle.getSerializable("usuarioSelecionado");
            getSupportActionBar().setTitle(usuarioSelecionado.getNome());
            String caminhoFoto = usuarioSelecionado.getCaminhoFoto();
            if(caminhoFoto != null){
                Uri url = Uri.parse(caminhoFoto);
                Glide.with(PerfilAmigoActivity.this)
                        .load(url)
                        .into(imagePerfil);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        recuperarDadosPerfilAmigo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioAmigoRef.removeEventListener(valueEventListenerPerfilAmigo);
    }

    private void recuperarDadosPerfilAmigo(){
        usuarioAmigoRef = usuariosRef.child(usuarioSelecionado.getId());
        valueEventListenerPerfilAmigo = usuarioAmigoRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        String postagens = String.valueOf(usuario.getPostagens());
                        String seguindo = String.valueOf(usuario.getSeguindo());
                        String seguidores = String.valueOf(usuario.getSeguidores());
                        textPublicacoes.setText(postagens);
                        textSeguindo.setText(seguindo);
                        textSeguidores.setText(seguidores);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    private void inicializarComponentes(){
        imagePerfil = findViewById(R.id.imagePerfil);
        buttonAcaoPerfil = findViewById(R.id.buttonAcaoPerfil);
        textPublicacoes = findViewById(R.id.textPublicacoes);
        textSeguidores = findViewById(R.id.textSeguidores);
        textSeguindo = findViewById(R.id.textSeguindo);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
