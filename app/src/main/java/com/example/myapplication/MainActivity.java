package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    GridLayout grid ;
    ArrayAdapter<String> adaptador;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        grid = findViewById(R.id.grid);
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,getResources().getStringArray(R.array.bombas));
        fragmentManager = getSupportFragmentManager();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.instrucciones:

                Dialogs ds = new Dialogs(1);
                ds.setString(R.string.CuerpoInstrucciones);
                ds.show(getSupportFragmentManager(),"this");
                return true;
            case R.id.comenzar:
                grid.removeAllViews();
                //genera el tablero
                ds = new Dialogs(2,this,grid);
                ds.show(getSupportFragmentManager(),"this");

                return true;
            case R.id.configuracion:
                ds = new Dialogs(3,adaptador);
                ds.show(getSupportFragmentManager(),"");
                return true;
            default:
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error",Toast.LENGTH_LONG).show();
                return true;
        }
    }
}