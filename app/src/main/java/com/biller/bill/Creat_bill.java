package com.biller.bill;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Creat_bill extends AppCompatActivity {
    public static final String CALCULATOR_PACKAGE ="com.android.calculator2";
    public static final String CALCULATOR_CLASS ="com.android.calculator2.Calculator";
    Dbhelper dbh;
    Button b1;
    ImageButton b2;
    EditText ed1,ed2,ed3,ed4;
    String send_cus,send_names,send_total_name,send_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_creat_bill );
        dbh = new Dbhelper( this );
        ed1 = (EditText)findViewById( R.id.cus_name );
        ed2 = (EditText)findViewById( R.id.unit_name );
        ed3 = (EditText)findViewById( R.id.items_total );
        ed4 = (EditText)findViewById( R.id.Total ) ;
        b1 = (Button)findViewById( R.id.bill );
        b2 = (ImageButton) findViewById( R.id.cals );

        AddData();
        b2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();
                i.setAction(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_APP_CALCULATOR);
                startActivity(i);
            }
        } );

    }

    private void AddData() {
        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean is = dbh.insertData( ed1.getText().toString(),ed2.getText().toString(),ed3.getText().toString(),ed4.getText().toString() );
                if(is = true)
                {
                    Toast.makeText( Creat_bill.this, "Bill saved", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    Toast.makeText( Creat_bill.this, "fail", Toast.LENGTH_SHORT ).show();
                }
                send_cus = ed1.getText().toString();
                send_names = ed2.getText().toString();
                send_total_name = ed3.getText().toString();
                send_total = ed4.getText().toString();
                Intent intent = new Intent( Creat_bill.this,Share.class );
                intent.putExtra("1",send_cus );
                intent.putExtra("2",send_names );
                intent.putExtra("3",send_total_name );
                intent.putExtra("4",send_total );
                startActivity( intent );
            }
        } );
    }


}
