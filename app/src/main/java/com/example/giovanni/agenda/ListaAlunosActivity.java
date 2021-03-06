package com.example.giovanni.agenda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class ListaAlunosActivity extends AppCompatActivity {

    FirebaseAuth auth;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Button b = findViewById(R.id.btnLogin);
        Button b2 = findViewById(R.id.btn_forgot_password);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        final ProgressBar NewprogressBar = (ProgressBar)findViewById(R.id.spin_kit);
        DoubleBounce doubleBounce = new DoubleBounce();
        NewprogressBar.setIndeterminateDrawable(doubleBounce);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NewprogressBar.setVisibility(view.VISIBLE);
                        Intent in = new Intent(ListaAlunosActivity.this, TelaPrincipalActivity.class);
                        startActivity(in);
                    }
                }, 4000);

            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListaAlunosActivity.this, EsqueceSenha.class); // Intent it = new Intent(this, TelaPrincipalActivity.class); tive que coloacr a classe no primeiro this pq ela ta dentro do oncompletelistener
                startActivity(it);
            }
        });

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Intent it = new Intent(ListaAlunosActivity.this, TelaPrincipalActivity.class);
            startActivity(it);
            finish();
        }


    }

    private void login() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtSenha = findViewById(R.id.edtSenha);

        if (!edtEmail.getText().toString().isEmpty() && !edtSenha.getText().toString().isEmpty()) {
            Task<AuthResult> processo = auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString());
            processo.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent it = new Intent(ListaAlunosActivity.this, TelaPrincipalActivity.class); // Intent it = new Intent(this, TelaPrincipalActivity.class); tive que coloacr a classe no primeiro this pq ela ta dentro do oncompletelistener
                        startActivity(it);
                    } else {
                        Toast.makeText(getApplicationContext(), "E-mail ou senha invalidos", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Favor preencher os campos de LOGIN", Toast.LENGTH_LONG).show();
        }

    }
}
