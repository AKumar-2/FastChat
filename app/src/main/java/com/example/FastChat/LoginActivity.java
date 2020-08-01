package com.example.FastChat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button loginButton, createAccButton;
    private EditText emailText , passwordText;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        loginButton = findViewById(R.id.btn_login);
        createAccButton = findViewById(R.id.btn_create_acc);
        emailText = findViewById(R.id.text_email_login);
        passwordText = findViewById(R.id.text_password_login);
        ConstraintLayout bcdCons = findViewById(R.id.bacgroundCons);
        LinearLayout bcdLin = findViewById(R.id.bacgroundLin);

        bcdCons.setOnClickListener(this);
        bcdLin.setOnClickListener(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        passwordText.setOnKeyListener(this);
    }



    private void login() {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Auth", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.d("User",user.toString());
                    Intent sucessLogin = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(sucessLogin);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i==keyEvent.KEYCODE_ENTER && keyEvent.getAction()==keyEvent.ACTION_DOWN){

            login();
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.bacgroundCons || view.getId()==R.id.bacgroundLin){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }

    }
}