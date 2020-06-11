package com.yasser.roknaapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.yasser.roknaapp.ui.main.MainActivity;


public class Dialogs {

    Context context;
    ProgressDialog pd;

    public Dialogs(Context context) {
        this.context = context;
    }

    public void showProgressDialog(String title,String content){
          pd= ProgressDialog.show(context, title, content, true);
    }
    public void stopDialog(){
        pd.dismiss();
    }
    public void showAlertDialogWithIntent(String content, final Activity contexttoo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(content)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context,contexttoo.getClass());
                        context.startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void showAlertDialogToMain(String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(content)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context,MainActivity.class);
                        context.startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
