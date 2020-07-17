package com.biller.bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter  extends BaseAdapter {
    Context context;
    ArrayList<Student> arrayList;
    public MyAdapter(Context context, ArrayList<Student> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        view= inflater.inflate( R.layout.customlist_view,null );
        TextView t1 = (TextView)view.findViewById( R.id.id_txt );
        TextView t2 = (TextView)view.findViewById( R.id.id_txt1 );
        TextView t3 = (TextView)view.findViewById( R.id.id_txt2 );
        Student student = arrayList.get( i );
        t1.setText( ""+ String.valueOf( student.getId()) );
        t2.setText( ""+student.getName() );
        t3.setText( ""+student.getSurname() );
        return view;
    }
}
