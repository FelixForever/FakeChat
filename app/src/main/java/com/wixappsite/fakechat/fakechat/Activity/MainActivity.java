package com.wixappsite.fakechat.fakechat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wixappsite.fakechat.fakechat.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      startActivity(new Intent(MainActivity.this,ChatActivity.class));

        finish();
    }
}
