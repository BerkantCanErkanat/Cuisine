package com.berkantcanerkanat.cuisine;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

public class LoadingDialog {
    Activity activity;
    AlertDialog dialog;
    TextView loadingScreenText;
    LoadingDialog(Activity activity){
        this.activity = activity;
        loadingScreenText = this.activity.findViewById(R.id.loadingScreenTextView);
    }
    void startLoadingDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        alertDialog.setView(layoutInflater.inflate(R.layout.custom_loading_dialog,null));
        alertDialog.setCancelable(false);
        dialog = alertDialog.create();
        dialog.show();
    }
    void dismissDialog(){
        dialog.dismiss();
    }
}
