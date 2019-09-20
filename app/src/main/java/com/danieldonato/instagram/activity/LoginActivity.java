package com.danieldonato.instagram.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.danieldonato.instagram.R;
import com.danieldonato.instagram.helper.ConfiguracaoFirebase;
import com.danieldonato.instagram.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private ProgressBar progressBar;
    private Usuario usuario;
    private FirebaseAuth auteticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verificarUsuarioLogado();
        inicializarComponentes();

        progressBar.setVisibility(View.GONE);
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = campoEmail.getText().toString();
                String textSenha = campoSenha.getText().toString();
                    if(!textEmail.isEmpty()){
                        if(!textSenha.isEmpty()){
                            usuario = new Usuario();
                            usuario.setEmail(textEmail);
                            usuario.setSenha(textSenha);
                            validarLogin(usuario);
                        }else{
                            Toast.makeText(LoginActivity.this, "Preencha a senha", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Preencha o email", Toast.LENGTH_LONG).show();
                    }
                }
        });
    }

    public void verificarUsuarioLogado(){
        auteticacao = ConfiguracaoFirebase.getRefenciaAutenticacao();
        if(auteticacao.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void abrirCadastro(View view){
        Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(i);
    }

    public void validarLogin(Usuario usuario) {
        progressBar.setVisibility(View.VISIBLE);
        auteticacao = ConfiguracaoFirebase.getRefenciaAutenticacao();
        auteticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Erro ao fazer login", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void inicializarComponentes(){
        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);
        botaoEntrar = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressLogin);
        campoEmail.requestFocus();
    }


}
