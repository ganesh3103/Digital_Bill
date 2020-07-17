package com.biller.bill;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Creta_acct extends AppCompatActivity {
    EditText get_email,get_password;
    Button creat_account;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_creta_acct );
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar)findViewById( R.id.prg );
        get_email = (EditText)findViewById( R.id.email_creat );
        get_password = (EditText)findViewById( R.id.password_creat );
        creat_account = (Button)findViewById( R.id.creat_button );
        creat_account.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_go = get_email.getText().toString();
                String password_go = get_password.getText().toString();
                if(email_go.isEmpty())
                {
                    get_email.setError( "Email is required" );
                    get_email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher( email_go ).matches())
                {
                    get_email.setError( "pls enter valid email" );
                    get_email.requestFocus();
                    return;
                }
                if(password_go.isEmpty())
                {
                    get_password.setError( "password is required" );
                    get_password.requestFocus();
                    return;
                }
                if(password_go.length()<6)
                {
                    get_password.setError( "must be of 6 letters" );
                    get_password.requestFocus();
                    return;
                }
                progressBar.setVisibility( View.VISIBLE );
                mAuth.createUserWithEmailAndPassword( email_go, password_go ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility( View.GONE );
                        if(task.isSuccessful())
                        {
                            startActivity( new Intent( Creta_acct.this,Profile_info.class ) );
                            Toast.makeText( Creta_acct.this, "Account created successfully", Toast.LENGTH_SHORT ).show();
                        } else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText( Creta_acct.this, "Already register", Toast.LENGTH_SHORT ).show();
                            }else{
                                Toast.makeText( Creta_acct.this, task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                            }
                        }

                    }
                } );

            }
        } );
            }

    }

