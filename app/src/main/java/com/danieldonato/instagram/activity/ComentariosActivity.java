package com.danieldonato.instagram.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.danieldonato.instagram.R;
import com.danieldonato.instagram.adapter.AdapterComentario;
import com.danieldonato.instagram.helper.ConfiguracaoFirebase;
import com.danieldonato.instagram.helper.UsuarioFirebase;
import com.danieldonato.instagram.model.Comentario;
import com.danieldonato.instagram.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComentariosActivity extends AppCompatActivity {

    private EditText editComentario;
    private RecyclerView recyclerComentarios;

    private String idPostagem;
    private Usuario usuario;
    private AdapterComentario adapterComentario;
    private List<Comentario> listaComentarios = new ArrayList<>();

    private DatabaseReference firebaseRef;
    private DatabaseReference comentariosRef;
    private ValueEventListener valueEventListenerComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        editComentario = findViewById(R.id.editComentario);
        recyclerComentarios = findViewById(R.id.recyclerComentarios);

        usuario = UsuarioFirebase.getDadosUsuarioLogado();
        firebaseRef = ConfiguracaoFirebase.getReferenceFirebase();

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Comentarios");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_fechar_preto_24dp);

        recyclerComentarios.setHasFixedSize(true);
        recyclerComentarios.setLayoutManager(
                new LinearLayoutManager(this)
        );
        adapterComentario = new AdapterComentario(listaComentarios, getApplicationContext());
        recyclerComentarios.setAdapter(adapterComentario);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            idPostagem = bundle.getString("idPostagem");
        }
    }

    private void recuperarComentarios(){
        comentariosRef = firebaseRef.child("comentarios")
                .child(idPostagem);
        valueEventListenerComentarios = comentariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaComentarios.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    listaComentarios.add(ds.getValue(Comentario.class));
                }
                adapterComentario.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void salvarComentario(View view){
        String textComentario = editComentario.getText().toString();
        if(textComentario != null && !textComentario.isEmpty()){

            Comentario comentario = new Comentario();
            comentario.setIdPostagem(idPostagem);
            comentario.setIdUsuario(usuario.getId());
            comentario.setNomeUsuario(usuario.getNome());
            comentario.setCaminhoFoto(usuario.getCaminhoFoto());
            comentario.setComentario(textComentario);
            if(comentario.salvar()){
                Toast.makeText(this,
                        "Comentario salvo com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this,
                    "Insira um comentario antes de salvar",
                    Toast.LENGTH_SHORT).show();
        }
        editComentario.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarComentarios();
    }

    @Override
    protected void onStop() {
        super.onStop();
        comentariosRef.removeEventListener(valueEventListenerComentarios);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
