package com.biller.bill;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String[] STORAGE_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String[] NET_PERMISSIONS = {
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    private static int time = 4000;
    EditText email,password;
    Button login;
    TextView creat;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
//    data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar)findViewById( R.id.prg );
        email = (EditText)findViewById( R.id.email );
        password = (EditText)findViewById( R.id.password );
        login = (Button)findViewById( R.id.login_button );
        creat = (TextView)findViewById( R.id.creat_acct );
        new Handler(  ).postDelayed( new Runnable() {
            @Override
            public void run() {

            }
        },time );

        verify();
        creat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this,Creta_acct.class );
                startActivity( intent );
            }
        } );
        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String pass = password.getText().toString();
                if(Email.isEmpty())
                {
                    email.setError( "Email is required" );
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher( Email ).matches())
                {
                    email.setError( "pls enter valid email" );
                    email.requestFocus();
                    return;
                }
                if(pass.isEmpty())
                {
                    password.setError( "password is required" );
                    password.requestFocus();
                    return;
                }
                if(pass.length()<6)
                {
                    password.setError( "must be of 6 letter" );
                    password.requestFocus();
                    return;
                }
                progressBar.setVisibility( View.VISIBLE );

                mAuth.signInWithEmailAndPassword(Email, pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility( View.GONE );
                        if(task.isSuccessful())
                        {
                            finish();
                            Intent i = new Intent(MainActivity.this,Profile_info.class  );
                            i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            startActivity( i );


                        }else
                        {
                            Toast.makeText( MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );

            }
        } );
    }

    private void verify() {
        int permission_memory = ActivityCompat.checkSelfPermission( MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE );
        int permission_net = ActivityCompat.checkSelfPermission( MainActivity.this,Manifest.permission.INTERNET );

        if(permission_memory != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions( MainActivity.this,STORAGE_PERMISSIONS,1 );
        }
        if(permission_net != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions( MainActivity.this,NET_PERMISSIONS,1 );
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        if(mAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity( new Intent( this ,Home.class) );
        }
    }
            }
