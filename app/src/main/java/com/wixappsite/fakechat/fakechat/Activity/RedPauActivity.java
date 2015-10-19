package com.wixappsite.fakechat.fakechat.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wixappsite.fakechat.fakechat.Fx.CircleImageView;
import com.wixappsite.fakechat.fakechat.Fx.ConfirmDialog;
import com.wixappsite.fakechat.fakechat.R;
import com.wixappsite.fakechat.fakechat.Utils.MessageUtils;
import com.wixappsite.fakechat.fakechat.Utils.SystemUtils;

import java.io.FileNotFoundException;

/**
 * Created by Wix on 2015/9/29.
 */
public class RedPauActivity extends Activity implements ConfirmDialog.confirmDialogListener
{
    private RelativeLayout redpau;
    private LinearLayout tool_bar;
    private CircleImageView profilePic;
    private TextView divider;
    private TextView rp_tv1;
    private TextView rp_tv2;
    private TextView s_redpau;
    private  EditText redpauname;
    private String image_path;
    private String path;
    int  n;
  /*  String []items={"收到的钱可直接消费" ,
            "收到的钱可直接用来发红包" ,
            "可于“钱包-微信红包-我的红包”查看记录" ,
            "收到的钱可直接提现" ,
            "收到的钱可用来转账"};*/
  String []items=new String[5];
    private final static int RESULT_CHANGE_AVATAR=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redpau);
        items=getResources().getStringArray(R.array.redpau_text);
        s_redpau= (TextView) findViewById(R.id.s_redpau);
        redpauname= (EditText) findViewById(R.id.redpau_name);
        if(SystemUtils.isEn(this))
        {
            s_redpau.setVisibility(View.GONE);

            redpauname.setText("Lucky Money from FakeChat");

        }

        profilePic= (CircleImageView) findViewById(R.id.iv_profile_pic);
        divider= (EditText) findViewById(R.id.divider);
tool_bar= (LinearLayout) findViewById(R.id.tool_bar);
        rp_tv1= (TextView) findViewById(R.id.text_redpau1);

        rp_tv2= (TextView) findViewById(R.id.text_redpau2);
rp_tv2.setText(items[getRandText()].toString());
        redpau= (RelativeLayout) findViewById(R.id.redpau_layout);
        redpau.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
         divider.requestFocus();
                if(tool_bar.getVisibility()==View.VISIBLE)
                    tool_bar.setVisibility(View.INVISIBLE);
                else
                tool_bar.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

    }
   int getRandText()
   {
      int s=(int)(Math.random() * 5);
       while(s==n)
       {
          s=(int)(Math.random() * 5);
       }
       n=s;
return n;
   }
    public Intent pickImage()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }
    public  void changeAvatar(View view)
    {
       startActivityForResult(pickImage(),RESULT_CHANGE_AVATAR);
    }


    public void backPressed(View view)
    {
        finish();
    }
public  void onClick(View view)
{
    int id=view.getId();
    switch (id)
    {
        case R.id.btn_screenshot:
            divider.requestFocus();
            tool_bar.setVisibility(View.INVISIBLE);
            ConfirmDialog cs=new ConfirmDialog();
            cs.setInfo(getResources().getString(R.string.confirm_screenshot), getResources().getString(R.string.confirm_screenshot_title),1);
            cs.show(getFragmentManager(), "ScreenShotDialog");
            break;
        case R.id.btn_share:
            divider.requestFocus();
            tool_bar.setVisibility(View.INVISIBLE);
            MessageUtils.shareScreenshot(this);
            break;
        case R.id.text_redpau1:
            if(rp_tv1.getText().equals(getResources().getString(R.string.redpau_text_bot2)))
                rp_tv1.setText(getResources().getString(R.string.redpau_text_bot1));
            else
                rp_tv1.setText(getResources().getString(R.string.redpau_text_bot2));
            break;
        case R.id.text_redpau2:
            rp_tv2.setText(items[getRandText()]);
            break;
    }
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode ==RESULT_CHANGE_AVATAR && (resultCode == RESULT_OK
        )&& null != data) )
        {

            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inSampleSize = 3;
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,options);
                switch (requestCode) {
                    case 0:
                        profilePic.setImageBitmap(bitmap);
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }



        }

    }

    @Override
    public void onFinishConfirmDialog(int code) {

        MessageUtils.takeScreenshot(this);
    }

}
