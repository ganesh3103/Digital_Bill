package com.biller.bill;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class Share extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    RelativeLayout r1;
    RelativeLayout r2;
    StorageReference store;
    DatabaseReference database;
    TextView cus,tel_mob,tel_email;
    ImageView show_image;
    ScrollView l1,l2,l3,l4,l5,l6;
   TextView get_cus,get_names,get_totalnames,get_total,c,n1,n2,n3;
   Button share;
   ImageView img;
   String t1,t2,t3,t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_share );
       l1 = (ScrollView)findViewById( R.id.line_up );
        l2 = (ScrollView)findViewById( R.id.line_2 );
        l3 = (ScrollView)findViewById( R.id.line_3 );
        l4 = (ScrollView)findViewById( R.id.line_4 );
        l5 = (ScrollView)findViewById( R.id.line_5 );
        l6 = (ScrollView)findViewById( R.id.line_6 );
        c= (TextView)findViewById( R.id.contd );
        n1= (TextView)findViewById( R.id.naam );
        n2= (TextView)findViewById( R.id.naam2 );
        n3= (TextView)findViewById( R.id.naam3 );
        img = (ImageView)findViewById( R.id.view_screenshot );
        firebaseAuth = FirebaseAuth.getInstance();
        store = FirebaseStorage.getInstance().getReference(firebaseAuth.getUid());
        database = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
        cus = (TextView)findViewById( R.id.Bus_name );
        tel_mob = (TextView)findViewById( R.id.tel_num );
        tel_email = (TextView)findViewById( R.id.tel_email );
        show_image = (ImageView)findViewById( R.id.show_image );
        get_cus = (TextView) findViewById( R.id.display_cusname );
        get_names = (TextView)findViewById( R.id.display_name_of_items );
        get_totalnames= (TextView) findViewById( R.id.display_total_numof_items);
        get_total = (TextView)findViewById( R.id.display_total_acc );
        t1 = get_cus.getText().toString();
        t2 = get_names.getText().toString();
        t3 = get_totalnames.getText().toString();
        t4 = get_total.getText().toString();
        share = (Button)findViewById( R.id.share_scren );
        final Intent intent = getIntent();
        t1 = intent.getStringExtra( "1" );
        t2 = intent.getStringExtra( "2" );
        t3 = intent.getStringExtra( "3" );
        t4 = intent.getStringExtra( "4" );
        get_cus.setText("Bill to: " +t1);
        get_names.setText( ""+t2 );
        get_totalnames.setText( ""+t3 );
        get_total.setText( ""+t4 );

        share.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bitmap b = Screenshot.takescreenshotsOfRootView( img );


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(  );
                b.compress( Bitmap.CompressFormat.PNG,100,byteArrayOutputStream );
                String path = MediaStore.Images.Media.insertImage( getContentResolver(),b,"h",null );
                Uri t = Uri.parse( path );
                img.setImageURI( t);
                l1.setVisibility( View.INVISIBLE );
                l2.setVisibility( View.INVISIBLE );
                l3.setVisibility( View.INVISIBLE );
                l4.setVisibility( View.INVISIBLE );
                l5.setVisibility( View.INVISIBLE );
                l6.setVisibility( View.INVISIBLE );
                n1.setVisibility( View.INVISIBLE );
                n2.setVisibility( View.INVISIBLE );
                n3.setVisibility( View.INVISIBLE );
                c.setVisibility( View.INVISIBLE );
                cus.setVisibility( View.INVISIBLE );
                tel_email.setVisibility( View.INVISIBLE );
                tel_mob.setVisibility( View.INVISIBLE );
                share.setVisibility( View.INVISIBLE );
                show_image.setVisibility( View.INVISIBLE );
                get_cus.setVisibility( View.INVISIBLE );
                get_names.setVisibility( View.INVISIBLE );
                get_totalnames.setVisibility( View.INVISIBLE );
                get_total.setVisibility( View.INVISIBLE );
                Toast.makeText( Share.this, "Bill saved to gallery", Toast.LENGTH_SHORT ).show();


                Intent sahe = new Intent( Intent.ACTION_SEND );
                sahe.setType( "image/*" );
                sahe.putExtra(Intent.EXTRA_STREAM,t);
                startActivity( Intent.createChooser( sahe,"share with" ) );
            }







        } );
}


    private void BG() {
        View vi = this.getWindow().getDecorView();
         int color = Color.BLACK;
        vi.setBackgroundColor( color);
    }


    @Override
    protected void onStart() {
        super.onStart();
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
                        show_image.setImageURI( Uri.fromFile( finalLocalFile ) );



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

                cus.setText(" " +N);
                tel_mob.setText("Mobile:" +T);
                tel_email.setText( "Email:" +A);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }
}
