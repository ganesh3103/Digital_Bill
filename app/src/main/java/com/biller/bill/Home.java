package com.biller.bill;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Home extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    StorageReference store;
    DatabaseReference database;
    TextView set_name,set_mobile,set_email;
    ImageView img;
    ProgressBar pr;
    Button bill ,list;
    InterstitialAd interstitialAd,interstitialAd1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        MobileAds.initialize( this,"ca-app-pub-8081521936908560~8402331524");
         interstitialAd = new InterstitialAd( this );
        interstitialAd1 = new InterstitialAd( this );
         interstitialAd.setAdUnitId( "ca-app-pub-8081521936908560/7553952588" );
        interstitialAd1.setAdUnitId( "ca-app-pub-8081521936908560/6058041079" );
         interstitialAd.setAdListener( new AdListener()
                                       {
                                           @Override
                                           public void onAdClosed() {
                                               Intent i_creat = new Intent( Home.this,Creat_bill.class );
                                               startActivity( i_creat );
                                               interstitialAd.loadAd( new AdRequest.Builder().build() );
                                           }
                                       }
         );
         interstitialAd.loadAd( new AdRequest.Builder().build() );
         interstitialAd1.setAdListener( new AdListener()
                                        {
                                            @Override
                                            public void onAdClosed() {
                                                Intent intent_list = new Intent( Home.this,BIl_list.class);
                                                startActivity( intent_list );
                                                interstitialAd1.loadAd( new AdRequest.Builder().build() );
                                            }
                                        }
         );
        interstitialAd1.loadAd( new AdRequest.Builder().build() );
        bill = (Button)findViewById( R.id.button );
        list = (Button)findViewById( R.id.button1 );
        pr =(ProgressBar)findViewById( R.id.prg3 );
        firebaseAuth = FirebaseAuth.getInstance();
        store = FirebaseStorage.getInstance().getReference(firebaseAuth.getUid());
        database = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
        img = (ImageView)findViewById( R.id.round_img);
        set_name = (TextView)findViewById( R.id.set_name );
        set_mobile = (TextView)findViewById( R.id.set_mobile );
        set_email = (TextView)findViewById( R.id.set_email );
        bill.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_creat = new Intent( Home.this,Creat_bill.class );
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else {
                    startActivity( i_creat );
                }
            }
        } );
        list.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_list = new Intent( Home.this,BIl_list.class);
                if(interstitialAd1.isLoaded())
                {
                    interstitialAd1.show();
                }
                else {
                    startActivity( intent_list );
                }
            }
        } );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu, menu );
        return super.onCreateOptionsMenu( menu );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity( new Intent(Home.this,MainActivity.class) );
            }
        }
        return super.onOptionsItemSelected( item );
    }


    @Override
    protected void onStart() {
        super.onStart();
        pr.setVisibility( View.VISIBLE );
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final File finalLocalFile = localFile;
        store.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        img.setImageURI( Uri.fromFile( finalLocalFile ) );
                        pr.setVisibility( View.INVISIBLE );
                        Toast.makeText( Home.this, "Profile loaded succesfully", Toast.LENGTH_SHORT ).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
        database.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String N= dataSnapshot.child("_Name").getValue(String.class);
                String T= dataSnapshot.child("_Mobile").getValue(String.class);
                String A= dataSnapshot.child("_Email").getValue(String.class);

                set_name.setText(" " +N);
                set_mobile.setText(" " +T);
                set_email.setText( " " +A);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }


    }
