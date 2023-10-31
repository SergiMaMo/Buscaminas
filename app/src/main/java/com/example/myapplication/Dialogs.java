package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class Dialogs extends DialogFragment {
    /* Este método es llamado al hacer el show() de la clase DialogFragment() */
    int string , opcion;
    String [] dificultades = {"facil","medio","dificil","muy dificil","mode esquere"};
    int numeroDeCasillas,numeroDeMinas;
    Context contexto;
    GridLayout grid;
    ArrayAdapter<String> adaptador;

    public void setString(int string) {
        this.string = string;
    }
    public Dialogs(int opcion){
        this.opcion=opcion;
    }
    public Dialogs(int opcion,ArrayAdapter<String> adaptador){
        this.opcion=opcion;
        this.adaptador=adaptador;
    }
    public Dialogs(int opcion, Context contexto, GridLayout grid) {
        this.opcion=opcion;
        this.contexto = contexto;
        this.grid = grid;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Usamos la clase Builder para construir el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (opcion) {
            case 1 :
                builder.setTitle(R.string.TituloInstrucciones);
                builder.setMessage(string);
                return builder.create();
            case 2:
            builder.setSingleChoiceItems(dificultades, -1, (dialog, which) -> {
                grid.removeAllViews();
                switch (which){
                    case 0:
                        numeroDeCasillas = 8;
                        numeroDeMinas = 10;
                        grid.setRowCount(numeroDeCasillas);
                        grid.setColumnCount(numeroDeCasillas);
                        break;
                    case 1:
                        numeroDeCasillas = 10;
                        numeroDeMinas = 20;
                        grid.setRowCount(numeroDeCasillas);
                        grid.setColumnCount(numeroDeCasillas);
                        break;
                    case 2:
                        numeroDeCasillas = 12;
                        numeroDeMinas = 30;
                        grid.setRowCount(numeroDeCasillas);
                        grid.setColumnCount(numeroDeCasillas);
                        break;
                    case 3:
                        numeroDeCasillas = 16;
                        numeroDeMinas = 60;
                        grid.setRowCount(numeroDeCasillas);
                        grid.setColumnCount(numeroDeCasillas);
                        break;
                    case 4:
                        numeroDeCasillas = 17;
                        numeroDeMinas = 80;
                        grid.setRowCount(numeroDeCasillas);
                        grid.setColumnCount(numeroDeCasillas);
                        break;
                    default:

                        break;
                }
                Tablero tablero = new Tablero(contexto,grid,numeroDeCasillas,numeroDeMinas);
                tablero.generarCasillas();
            });
            return builder.create();
            case 3:
                builder.setAdapter(adaptador,(dialog, which) -> {

                });
                return builder.create();
            default:
                return builder.create();
        }

    }
    //Se invoca cuando el fragmento se añade a la actividad
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}