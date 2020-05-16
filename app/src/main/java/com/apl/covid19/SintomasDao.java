package com.apl.covid19;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SintomasDao {
    FirebaseFirestore db;

    public SintomasDao() {
        db = FirebaseFirestore.getInstance();
    }

    public void alterar(Sintoma sintoma, OnSuccessListener<? super Void> onSuccess,
                        OnFailureListener onFailure) {
        Map<String, Object> sintomaMap = new HashMap<>();
        sintomaMap.put("nome", sintoma.getNome());
        sintomaMap.put("telefone", sintoma.getTelefone());
        sintomaMap.put("sintomas", sintoma.getSintomas());

        db.collection("sintomas")
                .document(sintoma.getId())
                .set(sintomaMap)
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure);
    }

    public void incluir(Sintoma sintoma, OnSuccessListener<DocumentReference> onSuccess,
                          OnFailureListener onFailure) {
        Map<String, Object> sintomaMap = new HashMap<>();
        sintomaMap.put("nome", sintoma.getNome());
        sintomaMap.put("telefone", sintoma.getTelefone());
        sintomaMap.put("sintomas", sintoma.getSintomas());

        db.collection("sintomas")
                .add(sintomaMap)
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure);
    }

    public void listar(OnSuccessListener<? super QuerySnapshot> onSuccess, OnFailureListener onFailure) {
        db.collection("sintomas")
                .get()
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure);
    }
}
