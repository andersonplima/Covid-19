package com.apl.covid19;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class ListActivity extends AppCompatActivity {

    private ArrayAdapter<Sintoma> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        adapter = null;
        loadSintomas();
    }

    private void loadSintomas() {
        new SintomasDao().listar(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Sintoma> sintomas = new ArrayList<>();

                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            Sintoma sintoma = new Sintoma();
                            sintoma.setId(document.getId());
                            sintoma.setTelefone(Objects.requireNonNull(document.get("telefone")).toString());
                            sintoma.setSintomas(Objects.requireNonNull(document.get("sintomas")).toString());
                            sintoma.setNome(Objects.requireNonNull(document.get("nome")).toString());

                            sintomas.add(sintoma);
                        }

                        setOrUpdateListViewAdapter(sintomas);
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro ao carregar os sintomas!",
                                    Toast.LENGTH_LONG).show();
                            Log.w(TAG, "Error loading documents", e);
                    }
                });

    }

    private void setOrUpdateListViewAdapter(ArrayList<Sintoma> sintomas) {
        if (adapter == null) {
            ListView listView = findViewById(R.id.listView);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sintomas);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Sintoma sintoma = (Sintoma) parent.getItemAtPosition(position);
                    Intent intent = new Intent(ListActivity.this, MainActivity.class);
                    intent.putExtra("sintoma", sintoma);
                    startActivity(intent);
                }
            });
        } else {
            adapter.clear();
            adapter.addAll(sintomas);
            adapter.notifyDataSetChanged();
        }
    }
}
