package com.biller.bill;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BIl_list extends AppCompatActivity {
    Dbhelper dbh;
    Button b1;
    ListView listView;
    ArrayList<Student> arrayList;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_bil_list );
        dbh = new Dbhelper( this );
       listView = (ListView)findViewById( R.id.list_item );
       loaddata();
       listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

           }
       } );

    }

    private void loaddata() {

        arrayList=dbh.getAllData();
        myAdapter = new MyAdapter( this,arrayList );
        listView.setAdapter( myAdapter );
        myAdapter.notifyDataSetChanged();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu1, menu );
        return super.onCreateOptionsMenu( menu );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Delete:{
                 Intent ok = new Intent( this,Del.class );
                 startActivity( ok );
            }
        }
        return super.onOptionsItemSelected( item );
    }

}
