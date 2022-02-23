package com.example.FastChat;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnKeyListener {

    private FirebaseAuth mAuth;
    private EditText emailText;
    private EditText passwordText, confirmPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        //noinspection unused will see to it later
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Button signUpButton = findViewById(R.id.btn_sign_up);
        emailText = findViewById(R.id.text_email);
        passwordText = findViewById(R.id.text_password);
        confirmPasswordText = findViewById(R.id.text_confirm_password);
        EditText confirmPasswordText = findViewById(R.id.text_confirm_password);
        confirmPasswordText.setOnKeyListener(this);
        signUpButton.setOnClickListener(view -> signUp());
    }

    private void signUp() {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if(emailText.getText().length() == 0){
            emailText.setError("Enter Email");
        }else if(passwordText.getText().length() == 0){
            passwordText.setError("Enter Password");
        }if (passwordText.getText().length() < 6){
            passwordText.setError("Password length should be more than 5");
            Toast.makeText(SignUpActivity.this, "Password length should be more than 5", Toast.LENGTH_LONG).show();
        }else if (!confirmPasswordText.getText().toString().equals(password)){
            confirmPasswordText.setError("Password don't match");
        }else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Auth", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    assert user != null;
                    Log.d("User", user.toString());
                    Intent successSignUp = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(successSignUp);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth", "createUserWithEmail:failure", task.getException());
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(SignUpActivity.this, "User already exist Login please", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            signUp();
        }
        return false;
    }
}