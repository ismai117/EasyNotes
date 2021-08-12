package com.easynotes.mynotesapp.views.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.easynotes.mynotesapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email, password;
    private FragmentTransaction fragmentTransaction;
    Button login;
    TextView gotoRegister;


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.log_email_input);
        password = findViewById(R.id.log_password_input);
        login = findViewById(R.id.login_button);
        gotoRegister = findViewById(R.id.gotoRegister);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                if (TextUtils.isEmpty(emailValue) || TextUtils.isEmpty(passwordValue)){
                    email.setError("Don't leave field empty");
                    password.setError("Don't leave field empty");
                } else if (!emailValue.contains("@")){
                    email.setError("Dont leave out '@' symbol");
                }
                else {
                    setDetails(emailValue, passwordValue);
                }

            }
        });

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void setDetails(String emailValue, String passwordValue) {

        fAuth.signInWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    Log.d("Login", "Success");
                } else {
                    Log.d("Login", "Failed: " + task.getException());
                }
            }
        });

    }
}