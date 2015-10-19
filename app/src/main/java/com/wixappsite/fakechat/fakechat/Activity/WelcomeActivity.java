package com.wixappsite.fakechat.fakechat.Activity;

/**
 * Created by Wix on 2015/9/26.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.wixappsite.fakechat.fakechat.App;
import com.wixappsite.fakechat.fakechat.R;
import com.wixappsite.fakechat.fakechat.Utils.DoNet;

import java.util.concurrent.TimeUnit;

import cn.waps.AppConnect;

public class WelcomeActivity extends Activity {
    final static String svr="http://120.27.30.238/HomePage/Home/CountOpenTimes/";
    @Override
    protected void onDestroy() {
        findViewById(R.id.welcome_layout).setBackground(null);
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
     AppConnect.getInstance(this);
        AppConnect.getInstance(this).setCrashReport(true);
        new Thread(start).start();

    }
    Runnable start=new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub


            SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
            new DoNet(2, svr,"IMEI=\""+App.IMEI + "\"&From=\"website\"", new DoNet.OkCallBack() {
                @Override
                public Void OnOk(String result) {
                    if(result.length()==0){
                        Toast.makeText(getApplicationContext(), "提示:数据连接未开启", Toast.LENGTH_SHORT).show();
                    }
                    return null;

                }
            }, new DoNet.NotOkCallBack() {
                @Override
                public Void OnNotOk() {
                    // Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
                    return null;
                }
            });
            startActivity(new Intent(WelcomeActivity.this, OptionsActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();


        }
    };

}
