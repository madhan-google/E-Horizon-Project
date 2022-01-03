package com.codekiller.ehorizon.Utils;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    Context context;
    public Toaster(Context context){
        this.context = context;
    }
    public void toast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
