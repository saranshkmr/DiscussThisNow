package com.sksha.discussthisnow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    public EditText etEmailId,etPassword;
    public Button btnSignUp;
    TextView tv;
    public static final String TAG="MAinActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmailId= (EditText) findViewById(R.id.et_sign_up_email);
        etPassword= (EditText) findViewById(R.id.et_sign_up_password);
        btnSignUp= (Button) findViewById(R.id.btn_sign_up);
        tv= (TextView) findViewById(R.id.tv_already_signed_up);
        firebaseAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    Log.d(TAG, "onAuthStateChanged: user is signed in" + user.getUid());
                }
                else
                {
                    Log.d(TAG, "onAuthStateChanged: user is signed out");
                }
            }
        };

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email=etEmailId.getText().toString();
                password=etPassword.getText().toString();
                if(email.equals("")||password.equals(""))
                    Toast.makeText(MainActivity.this, "Email or password can't be empty!!", Toast.LENGTH_SHORT).show();
                else
                {
                    createUser(email,password);
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,SignInActivity.class);
                startActivity(i);
            }
        });

    }

    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Registered!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Unsuccessfull try again !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null)
            firebaseAuth.removeAuthStateListener(authStateListener);
    }

}
