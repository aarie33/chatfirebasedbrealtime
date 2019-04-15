package com.example.arieahmad.chatfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arieahmad.chatfirebase.db.SharedPref;

public class InputName extends AppCompatActivity {

    private EditText edtNama;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);

        edtNama = (EditText) findViewById(R.id.edtNama);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtNama.getText())){
                    edtNama.setError("Isi nama");
                    edtNama.requestFocus();
                }else{
                    SharedPref.getInstance(getApplicationContext())
                            .saveNama(edtNama.getText().toString());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }
}
