package com.easynotes.mynotesapp.views.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText email, password, confirm_password;
    Button register;
    TextView gotoLogin;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        email = findViewById(R.id.reg_email_input);
        password = findViewById(R.id.reg_password_input);
        confirm_password = findViewById(R.id.confirm_password_input);
        register = findViewById(R.id.register_button);
        gotoLogin = findViewById(R.id.gotoLogin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                String confirmpasswordValue = confirm_password.getText().toString();

                if (TextUtils.isEmpty(emailValue) || TextUtils.isEmpty(passwordValue) || TextUtils.isEmpty(confirmpasswordValue)){
                    email.setError("Don't leave field empty");
                    password.setError("Don't leave field empty");
                    confirm_password.setError("Don't leave field empty");
                } else if (!passwordValue.equals(confirmpasswordValue)){
                    password.setError("Password don't match");
                    confirm_password.setError("Password don't match");
                } else if (!emailValue.contains("@")){
                    email.setError("Dont leave out '@' symbol");
                } else {
                    create(emailValue, passwordValue);
                }

            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });



    }

    private void create(String emailValue, String passwordValue) {

        fAuth.createUserWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    Log.d("Register", "Success");
                } else {
                    Log.d("Register", "Failed: " + task.getException());
                }
            }
        });

    }
}