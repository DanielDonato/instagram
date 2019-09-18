package com.danieldonato.instagram.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth refenciaAutenticacao;

    public static FirebaseAuth getRefenciaAutenticacao(){
        if(refenciaAutenticacao == null){
            refenciaAutenticacao = FirebaseAuth.getInstance();
        }
        return refenciaAutenticacao;
    }

    public static DatabaseReference getReferenceFirebase() {
        if(referenceFirebase == null){
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenceFirebase;
    }
}
