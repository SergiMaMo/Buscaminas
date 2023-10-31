package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class Casilla extends Tablero{
    int valor,numeroBombas;
    Context context;
    GridLayout gridLayout;
    Button casilla;
    ImageButton bomba;
    int alto,ancho ,i,j;
    int numeroFilasYColumnas;
    int contador = 0;
    Casilla[][] listaCasillas;
    boolean descubierta = false,marcada = false,primeraPulsada;

    public void setListaCasillas(Casilla[][] listaCasillas) {
        this.listaCasillas = listaCasillas;
    }

    public Casilla(int valor, Context context, GridLayout gridLayout,int i , int j,int numeroFilasYColumnas,int numeroDeBombas){
        this.valor = valor;
        this.context = context;
        this.gridLayout = gridLayout;
        this.i = i;
        this.j = j;
        this.numeroFilasYColumnas =  numeroFilasYColumnas;
        this.numeroBombas = numeroDeBombas;
        listaCasillas = new Casilla[numeroFilasYColumnas][numeroFilasYColumnas];
        Display display = null;
        //recogemos el alto y ancho de la pantalla para poder generar los botones con un tamaño adecuado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display = context.getDisplay();
        }
        Point size = new Point();
        if (display != null) {
            display.getRealSize(size);
        }
        alto = size.y-350;
        ancho = size.x;
        primeraPulsada=false;
        generarBotones();

    }

    @SuppressLint("SetTextI18n")
    private void generarBotones(){
        //generamos un button o un imageButton dependiendo de si es una bomba o una casilla
        Bordes bordes = new Bordes();
        if(valor==-1){

            bomba = new ImageButton(context);
            // bomba.setImageResource(R.drawable.bomb6); //descomentar para pruebas
            bomba.setMinimumHeight(alto/numeroFilasYColumnas);
            bomba.setMinimumWidth(ancho/numeroFilasYColumnas);
            bomba.setScaleType(ImageView.ScaleType.CENTER);
            bomba.setOnClickListener(view -> {
                if(!primeraPulsada){
                    reiniciar();
                }else {
                    bomba.setImageResource((R.drawable.bomb7));
                    perder();
                }
            });
            bomba.setOnLongClickListener(view ->{

                bomba.setImageResource((R.drawable.bandera2));
                marcada = true;
                for(int k = 0 ; k<numeroFilasYColumnas;k++){
                    for(int l = 0;l<numeroFilasYColumnas;l++){
                        if(listaCasillas[k][l].marcada){
                            contador++;
                            if(contador==numeroBombas){
                                ganar();
                            }
                        }
                    }
                }
                bomba.setOnLongClickListener(view1 ->{
                    return true;
                });
                return true;
            });
            bordes.bordeBoton(bomba,Color.rgb(128,128,128));
            gridLayout.addView(bomba);
        }else{
            casilla = new Button(context);

            casilla.setMinimumHeight(alto/numeroFilasYColumnas);
            casilla.setMinimumWidth(ancho/numeroFilasYColumnas);
            casilla.setHeight(alto/numeroFilasYColumnas);
            casilla.setWidth(ancho/numeroFilasYColumnas);
            casilla.setMaxHeight(alto/numeroFilasYColumnas);
            casilla.setMaxWidth(ancho/numeroFilasYColumnas);

            casilla.setOnClickListener(view -> {
                descubierta = true;
                ponerNumero(casilla);
                if(valor == 0) {
                    for(int k = 0 ; k<numeroFilasYColumnas;k++){
                        for(int l = 0;l<numeroFilasYColumnas;l++){
                            listaCasillas[k][l].primeraPulsada=true;
                        }
                    }
                    descubrirACholon(i,j);
                }
                if(!primeraPulsada&&valor!=0){
                    reiniciar();
                }
                casilla.setOnLongClickListener(view1 ->{
                    return true;
                });
            });
            casilla.setOnLongClickListener(view ->{
                casilla.setText(""+valor);
                perder();

                return true;
            });
            bordes.bordeBoton(casilla,Color.rgb(128,128,128));
            gridLayout.addView(casilla);
        }
    }

    public void descubrirACholon(int i , int j){
        //al dar click a una casilla se descubren las casillas de alrededor
        // si una casilla es 0 se descubren las casillas de alrededor de esa casilla
        for (int k = i - 1; k <= i + 1; k++) {
            for (int m = j - 1; m <= j + 1; m++) {
                try {
                    if (listaCasillas[k][m].valor != -1 && !listaCasillas[k][m].descubierta) {
                        listaCasillas[k][m].ponerNumero(listaCasillas[k][m].getCasilla());
                        listaCasillas[k][m].getCasilla().setOnLongClickListener(view1 ->{
                            return true;
                        });
                        listaCasillas[k][m].descubierta = true;
                        if(listaCasillas[k][m].valor == 0){
                            descubrirACholon(k,m);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException | NullPointerException ignored) {
                }
            }
        }

    }
    public void reiniciar(){
        gridLayout.removeAllViews();
        Tablero tablero = new Tablero(context,gridLayout,numeroFilasYColumnas,numeroBombas);
        if(tablero.MatrizReferencia[i][j] == 0){
            tablero.generarCasillas();
            tablero.listaCasillas[i][j].casilla.callOnClick();
        }else{
            reiniciar();
        }
    }

    public void perder(){

        gridLayout.removeAllViews();
        Toast.makeText(context,"has perdido",Toast.LENGTH_LONG).show();
    }

    public void ganar(){
        gridLayout.removeAllViews();
        Toast.makeText(context,"has ganado",Toast.LENGTH_LONG).show();
    }

    public Button getCasilla() {
        return casilla;
    }
    private void ponerNumero(Button btn){
        btn.setBackgroundColor(Color.rgb(220,220,220));
        int red = 0,green=10,blue=0;
        red = red + 90 * valor;
        if(red>254)red=255;
        blue = blue + 45 * valor;
        if(blue>254)blue=255;
        btn.setTextColor(Color.rgb(red,green,blue));
        btn.setText(""+valor);
    }
}
