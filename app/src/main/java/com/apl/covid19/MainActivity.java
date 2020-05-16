package com.apl.covid19;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CLOUD_FIRESTORE";
    private EditText editTextNome;
    private EditText editTextTefone;
    private EditText editTextSintomas;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextTefone = findViewById(R.id.editTextTelefone);
        editTextSintomas = findViewById(R.id.editTextSintomas);

        Button buttonSalvar = findViewById(R.id.buttonSalvar);

        Intent caller = getIntent();
        if (caller != null && caller.hasExtra("sintoma")) {
            Sintoma sintoma = (Sintoma) caller.getSerializableExtra("sintoma");

            assert sintoma != null;
            editTextNome.setText(sintoma.getNome());
            editTextTefone.setText(sintoma.getTelefone());
            editTextSintomas.setText(sintoma.getSintomas());

            id = sintoma.getId();
        } else
            id = null;

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sintoma sintoma = new Sintoma();
                sintoma.setId(id);
                sintoma.setNome(editTextNome.getText().toString());
                sintoma.setSintomas(editTextSintomas.getText().toString());
                sintoma.setTelefone(editTextTefone.getText().toString());

                if (id == null)
                    new SintomasDao().incluir(sintoma,
                            new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "Sintomas salvos com sucesso!",
                                            Toast.LENGTH_LONG).show();
                                    limparCampos();
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            }, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Erro ao salvar os sintomas!",
                                            Toast.LENGTH_LONG).show();
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                else
                    new SintomasDao().alterar(sintoma,
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Sintomas atualizados com sucesso!",
                                            Toast.LENGTH_LONG).show();
                                    limparCampos();
                                    Log.d(TAG, "DocumentSnapshot updated!");
                                }
                            },
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Erro ao atualizar os sintomas!",
                                            Toast.LENGTH_LONG).show();
                                    Log.w(TAG, "Error updating document", e);
                                }
                            }
                    );
            }
        });

        Button buttonListar = findViewById(R.id.buttonListar);
        buttonListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void limparCampos() {
        editTextNome.setText("");
        editTextTefone.setText("");
        editTextSintomas.setText("");
        id = null;
    }
}
