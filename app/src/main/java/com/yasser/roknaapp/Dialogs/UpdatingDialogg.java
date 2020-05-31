package com.yasser.roknaapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yasser.roknaapp.R;


public class UpdatingDialogg extends Dialog  {


    public static UpdatingDialogg mDialog;
    private Context mContext;
    static Dialog dialog;

    public UpdatingDialogg(Context mContext)
    {
        super(mContext);
        this.mContext=mContext;
    }

    public static UpdatingDialogg getInstance() {


        if (mDialog == null) {

        }
        return mDialog;

    }
    public void showContactUsDialog(Context pContext) {

        mContext = pContext;



        dialog = new Dialog(pContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.updating_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



    }

}
