package com.sksha.discussthisnow;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by sksha on 29-07-2016.
 */
public class SignInActivity extends AppCompatActivity {
    EditText etEmailId,etPassword;
    Button btnSignIn;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    TextView tv;
    public static final String TAG="SignInActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);
        etEmailId= (EditText) findViewById(R.id.et_login_email);
        etPassword= (EditText) findViewById(R.id.et_login_password);
        btnSignIn= (Button) findViewById(R.id.btn_login);
        tv= (TextView) findViewById(R.id.tv_not_signed_up);
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

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email=etEmailId.getText().toString();
                password=etPassword.getText().toString();
                signInUser(email,password);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignInActivity.this,MainActivity.class);
                startActivity(i);
            }
        });



    }

    private void signInUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    Toast.makeText(SignInActivity.this, "User signed in", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SignInActivity.this, "Sign in failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
