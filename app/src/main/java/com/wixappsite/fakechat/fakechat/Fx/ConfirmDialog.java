package com.wixappsite.fakechat.fakechat.Fx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Wix on 2015/9/28.
 */
public class ConfirmDialog extends DialogFragment
{
    public interface confirmDialogListener {
        void onFinishConfirmDialog(int code);
    }
    String title;
    String message;
    int code;
  public  ConfirmDialog()
    {

    }
  public void setInfo(String title,String message,int code)
    {
        this.title=title;
        this.message=message;
        this.code=code;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(message);
        builder.setMessage(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // App.ok=true;
if(code==4)
{

}
                if (code != 3) {
                    confirmDialogListener activity = (confirmDialogListener) getActivity();
                    activity.onFinishConfirmDialog(code);
                }
                    dialog.dismiss();
            }
        });
        if (code != 3) {
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //  dismiss();
                    //  App.ok=false;
                    dialog.dismiss();
                }
            });
        }
            return builder.create();
        }

}
