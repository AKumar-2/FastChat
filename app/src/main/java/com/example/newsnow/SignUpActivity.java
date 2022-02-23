package com.example.newsnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnKeyListener {

    private FirebaseAuth mAuth;
    private Button signUpButton;
    private EditText emailText , passwordText, confirmPasswordText;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        signUpButton = findViewById(R.id.btn_sign_up);
        emailText = findViewById(R.id.text_email);
        passwordText = findViewById(R.id.text_password);
        confirmPasswordText = findViewById(R.id.text_confirm_password);

        confirmPasswordText.setOnKeyListener(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Auth", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.d("User", user.toString());
                    Intent sucessSignUp = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(sucessSignUp);
                    finish();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i==keyEvent.KEYCODE_ENTER && keyEvent.getAction()==keyEvent.ACTION_DOWN){
            signUp();
        }
        return false;
    }
}