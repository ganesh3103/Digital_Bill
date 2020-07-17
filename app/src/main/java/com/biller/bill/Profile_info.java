package com.biller.bill;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Profile_info extends AppCompatActivity {
   ImageView img;
   TextView t1;
   EditText name,mobile,email;
   Button save;
    private static final int PICK_IMAGE = 1;
    FirebaseAuth mauth;
    Uri Imageuri;
    DatabaseReference database;
    StorageReference store;
    get ga = new get();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile_info );
        t1 = (TextView)findViewById( R.id.text );
        mauth = FirebaseAuth.getInstance();
        store = FirebaseStorage.getInstance().getReference(mauth.getUid());
        database = FirebaseDatabase.getInstance().getReference( mauth.getUid() );
        img = (ImageView)findViewById( R.id.round_img_profile );
        name = (EditText)findViewById( R.id.person_name );
        mobile = (EditText)findViewById( R.id.mobile);
        email = (EditText)findViewById( R.id.email );
        save = (Button)findViewById( R.id.save );
        img.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType( "image/*" );
                intent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( intent, PICK_IMAGE );

            }
        } );
        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String Mobile = mobile.getText().toString();
                String Email = email.getText().toString();
                if(Name.isEmpty())
                {
                    name.setError( "name required" );
                    name.requestFocus();
                    return;
                }
                if(Mobile.isEmpty())
                {
                    mobile.setError( "mobile number required" );
                    mobile.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher( Email ).matches())
                {
                    email.setError( "enter valid email" );
                    email.requestFocus();
                    return;
                }
                if(Imageuri == null)
                {
                   t1.setError( "select image" );
                   t1.requestFocus();
                   t1.setText( "select image or logo of your bussiness" );
                   return;
                }

                ga.setname( Name );
                ga.setMobile( Mobile );
                ga.setEmail( Email );
                database.setValue( ga );

                store.putFile( Imageuri )
                        .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText( Profile_info.this, "saving", Toast.LENGTH_SHORT ).show();
                            }
                        } )
                        .addOnFailureListener( new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        } );

                    Intent home = new Intent( Profile_info.this,Home.class );
                    startActivity( home );

            }
        } );


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Imageuri = data.getData();

            img.setImageURI( Imageuri );
            t1.setVisibility( View.INVISIBLE);

        }
    }
}
