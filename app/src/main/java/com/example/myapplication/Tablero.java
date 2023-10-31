package com.example.myapplication;

import android.content.Context;
import android.widget.GridLayout;

public class Tablero{
    Context context;
    GridLayout gridLayout;
    int NumeroFilasColumnas ,totalCasillas , numeroBombas  , i, j;
    int[][] MatrizReferencia;
    Casilla[][] listaCasillas;

    public Tablero() {
    }

    public Tablero(Context context , GridLayout gridLayout,int NumeroFilasColumnas,int numeroBombas){
        this.context = context;
        this.gridLayout = gridLayout;
        this.numeroBombas = numeroBombas;
        this.NumeroFilasColumnas = NumeroFilasColumnas;
        totalCasillas = NumeroFilasColumnas * NumeroFilasColumnas;
        MatrizReferencia = new int[NumeroFilasColumnas][NumeroFilasColumnas];
        listaCasillas = new Casilla[NumeroFilasColumnas][NumeroFilasColumnas];
        //crea las casillas y las añade a una lista
        //muestra la matriz por consola
        crearMatrizReferencia();

    }
    public void generarCasillas(){
        for (i = 0; i <= NumeroFilasColumnas-1 ; i++) {
            for (j = 0; j <= NumeroFilasColumnas-1 ; j++) {
                System.out.print( "\t" +MatrizReferencia[i][j] +"\t");
                Casilla casilla = new Casilla(MatrizReferencia[i][j],context,gridLayout,i,j,NumeroFilasColumnas,numeroBombas);
                listaCasillas[i][j] = casilla;
            }
            System.out.println();
        }
        //le añade la lista completa de casillas a cada casilla
        for (i = 0; i <= NumeroFilasColumnas-1 ; i++) {
            for (j = 0; j <= NumeroFilasColumnas-1 ; j++) {
                listaCasillas[i][j].setListaCasillas(listaCasillas);
            }
        }
    }
    private void crearMatrizReferencia(){
        ponerBombas();
        ComprobarBombas();
    }

    private void ComprobarBombas(){
        //recorre la matriz comprobando las casillas de alrededor de cada casilla comprobando si es una bomba
        // y sumando la cantidad de bombas que tiene una casilla alrededor
        for (i = 0; i <= NumeroFilasColumnas-1 ; i++) {
            for (j = 0; j <= NumeroFilasColumnas-1 ; j++) {
                int valor=0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int m = j - 1; m <= j + 1; m++) {
                        try {
                            if (MatrizReferencia[k][m] == -1) {
                                valor++;
                            }
                        } catch (ArrayIndexOutOfBoundsException | NullPointerException ignored) {
                        }
                    }
                }
                assert MatrizReferencia != null;
                if (MatrizReferencia[i][j] == 0) {
                    MatrizReferencia[i][j] = valor;
                }
            }
        }
    }

    private void ponerBombas(){
        //recorre la matriz metiendo bombas en posiciones aleatorias
        int contador = 0;
        while (contador < numeroBombas) {
            for (i = 0; i <= NumeroFilasColumnas-1 ; i++) {
                for (j = 0; j <= NumeroFilasColumnas-1 ; j++) {
                    int probabilidadDeBomba = (int) Math.floor(Math.random() * totalCasillas + 1);
                    if (probabilidadDeBomba == 1 && contador < numeroBombas && MatrizReferencia[i][j]!=-1) {
                        MatrizReferencia[i][j] = -1 ;
                        contador++;
                    }
                }
            }
        }
    }
}
