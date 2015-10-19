package com.wixappsite.fakechat.fakechat.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.wixappsite.fakechat.fakechat.Fx.ConfirmDialog;
import com.wixappsite.fakechat.fakechat.Obj.ChatTemplate;
import com.wixappsite.fakechat.fakechat.R;
import com.wixappsite.fakechat.fakechat.Utils.SystemUtils;

import cn.waps.AppConnect;

/**
 * Created by Wix on 2015/9/26.
 */
public class OptionsActivity extends Activity {
    private static final String FIRST_TIME = "FirstTime";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        //checkNews();

        sp = getSharedPreferences("FakeChat", Context.MODE_PRIVATE);
        if(!SystemUtils.isEn(this))
        if( sp.getBoolean(FIRST_TIME, true)==true) {
            new ChatTemplate(this);
        }
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean(FIRST_TIME, false);
        e.commit();

    }
    /*void checkNews()
    {
        String value=AppConnect.getInstance(this).getConfig("Message", "null");
        if(!value.equals("null"))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(OptionsActivity.this);
            builder.setTitle("FakeChat");
            builder.setMessage(value);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //       ConfirmDialog.confirmDialogListener activity = (ConfirmDialog.confirmDialogListener) AboutActivity.this;
                    //  activity.onFinishConfirmDialog(code);
                    dialog.dismiss();
                }
            });
            AlertDialog alert=builder.create();
            alert.show();
        }
    }*/
    @Override
    protected void onDestroy()
    {
        AppConnect.getInstance(this).close();
        super.onDestroy();
    }

  public  void onClick(View view)
    {
        int id=view.getId();
        switch(id)
        {
            case R.id.iv_wechat:
                startActivity(new Intent(OptionsActivity.this, ChatActivity.class));
                break;
            case R.id.iv_line:
                startActivity(new Intent(OptionsActivity.this, LineActivity.class));
                break;
            case R.id.iv_qq:
                startActivity(new Intent(OptionsActivity.this, QQActivity.class));
                break;
            case R.id.iv_messenger:
                startActivity(new Intent(OptionsActivity.this, MessengerActivity.class));
                break;
            case R.id.iv_redpau:
                startActivity(new Intent(OptionsActivity.this, RedPauActivity.class));
                break;
            case R.id.iv_add:
                ConfirmDialog cd=new ConfirmDialog();
              cd.setInfo(getResources().getString(R.string.confirm_purchase), getResources().getString(R.string.confirm_purchase_title),3);
                cd.show(getFragmentManager(), "MoreDialog");
                break;
            case R.id.iv_about:
                startActivity(new Intent(OptionsActivity.this,AboutActivity.class));
            //    overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
                break;
           /* default:
                Toast.makeText(this,"还未开发",Toast.LENGTH_SHORT).show();
                break;*/
        }
    }
}
