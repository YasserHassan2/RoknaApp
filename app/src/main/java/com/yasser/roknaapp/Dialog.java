package com.yasser.roknaapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.yasser.roknaapp.ui.main.MainActivity;


public class Dialog {

    Context context;
    ProgressDialog pd;

    public Dialog(Context context) {
        this.context = context;
    }

    public void showProgressDialog(String title,String content){
          pd= ProgressDialog.show(context, title, content, true);
    }
    public void stopDialog(){
        pd.dismiss();
    }
    public void showAlertDialog(String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(content)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
