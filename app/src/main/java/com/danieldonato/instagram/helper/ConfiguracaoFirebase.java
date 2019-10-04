package com.danieldonato.instagram.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth refenciaAutenticacao;
    private static StorageReference storage;

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

    public static StorageReference getFirebaseStorage(){
        if(storage == null){
            storage = FirebaseStorage.getInstance().getReference();
        }
        return storage;
    }
}
