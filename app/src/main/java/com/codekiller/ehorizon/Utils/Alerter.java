package com.codekiller.ehorizon.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.codekiller.ehorizon.R;

public class Alerter {
    AlertDialog.Builder builder;
    Context context;
    Alerter(Context context){
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }
    public void showAlert(String message){
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setMessage(message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

}
