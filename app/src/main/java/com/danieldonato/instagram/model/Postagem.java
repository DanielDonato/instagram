package com.danieldonato.instagram.model;

import com.danieldonato.instagram.helper.ConfiguracaoFirebase;
import com.danieldonato.instagram.helper.UsuarioFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Postagem implements Serializable {

    private String id;
    private String idUsuario;
    private String descricao;
    private String caminhoFoto;

    public Postagem(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getReferenceFirebase();
        DatabaseReference postagemRef = firebaseRef.child("postagens");
        String idPostagem = postagemRef.push().getKey();
        setId(idPostagem);
    }

    public boolean salvar(DataSnapshot seguidoresSnapshot){ // Logica feita para não precisar gerar feed no app => Solução ter um beckend para gerar esse feed
        Map obj = new HashMap();
        Usuario usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getReferenceFirebase();

        String combinacaoId = "/" + getIdUsuario() + "/" + getId();
        obj.put("/postagens" + combinacaoId, this);
        for(DataSnapshot seguidores : seguidoresSnapshot.getChildren()){
            Map<String, Object> dadosSeguidor = new HashMap<>();
            dadosSeguidor.put("fotoPostagem", getCaminhoFoto());
            dadosSeguidor.put("descricao", getDescricao());
            dadosSeguidor.put("id", getId());
            dadosSeguidor.put("nome", usuarioLogado.getNome());
            dadosSeguidor.put("fotoUsuario" , usuarioLogado.getCaminhoFoto());

            String idSeguidor = seguidores.getKey();

            String idsAtualizacao = "/" + idSeguidor + "/" + getId();
            obj.put("/feed" + idsAtualizacao, dadosSeguidor);
        }

        firebaseRef.updateChildren(obj);
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }
}
