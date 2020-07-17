package com.biller.bill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Del extends AppCompatActivity {
    EditText ed1;
    Button  b1;
    Dbhelper dbhelper = new Dbhelper( this );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_del );
        ed1=(EditText)findViewById( R.id.del1 );
        b1= (Button)findViewById( R.id.del_butn );
        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRoes = dbhelper.deleteData( ed1.getText().toString());
                if(deletedRoes > 0)
                {
                    Toast.makeText( Del.this, "Bill is Deleted ", Toast.LENGTH_SHORT ).show();
                }
                else
                    Toast.makeText( Del.this, "bill not deleted", Toast.LENGTH_SHORT ).show();


            }
        } );
    }
}
