package com.example.myapplication;

import android.graphics.drawable.GradientDrawable;
import android.widget.Button;
import android.widget.ImageButton;

public class Bordes {

    public static void bordeBoton(Button b, int color){
        if (b!=null){
            GradientDrawable gd = new GradientDrawable();
            if (color!=0){
                gd.setColor(color);
            }
            gd.setStroke(4, 0xFFAAAAAA);
            b.setBackground(gd);
        }
    }
    public static void bordeBoton(ImageButton b, int color){
        if (b!=null){
            GradientDrawable gd = new GradientDrawable();
            if (color!=0){
                gd.setColor(color);
            }
            gd.setStroke(4, 0xFFAAAAAA);
            b.setBackground(gd);
        }
    }
}
