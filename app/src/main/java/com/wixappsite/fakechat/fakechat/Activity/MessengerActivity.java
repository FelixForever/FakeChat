package com.wixappsite.fakechat.fakechat.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.wixappsite.fakechat.fakechat.Adapter.MessengerAdapter;
import com.wixappsite.fakechat.fakechat.Fx.ConfirmDialog;
import com.wixappsite.fakechat.fakechat.Obj.Message;
import com.wixappsite.fakechat.fakechat.R;
import com.wixappsite.fakechat.fakechat.Utils.MessageUtils;
import com.wixappsite.fakechat.fakechat.Utils.TextListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wix on 2015/9/27.
 */
public class MessengerActivity extends BaseChatActivity implements ConfirmDialog.confirmDialogListener{
    List<Message> msgList = new ArrayList<Message>();
    private ListView lvChat;
    private EditText chatbox;
    private LinearLayout more;
    private ToggleButton btn_more;
    private EditText name;
    Message msg;
    private static int RESULT_CHG_THEME=4;
    private static int RESULT_RECV_IMAGE = 1;
    private static int RESULT_CHANGE_IMAGE=3;
    private static int RESULT_LOAD_TEMPLATE=5;
    private static int count=-1;
    private static int position=0;
    private String image_path;
    private String path;
    MessengerAdapter facebookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        path= Environment.getExternalStorageDirectory()+ File.separator+Environment.DIRECTORY_DCIM;
        lvChat = (ListView)findViewById(R.id.facebook_chatlist);
        more= (LinearLayout) findViewById(R.id.facebook_more);
        btn_more= (ToggleButton) findViewById(R.id.facebook_btn_more);
        chatbox= (EditText) findViewById(R.id.et_chat_box);
        name=(EditText)findViewById(R.id.name);
        name.addTextChangedListener(new TextListener());
        facebookAdapter=new MessengerAdapter(this, msgList,this);
        lvChat.setAdapter(facebookAdapter);
        lvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                chatbox.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                more.setVisibility(View.GONE);
                btn_more.setChecked(false);
                return false;
            }
        });
        lvChat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                showDelDialog(position);
                return false;
            }
        });
        addTimeStamp();
    }

    public void showDelDialog(final int position) {
        final CharSequence[] items = {getResources().getString(R.string.delete)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                msgList.remove(position);
                lvChat.setAdapter(getAdapter());
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            takeScreen();
        }
    };
    void takeScreen()
    {
        MessageUtils.shareScreenshot(this);
    }
    public void backPressed(View view)
    {
        finish();
    }
    public void onClick(View view)
    {
        int id = view.getId();

        switch(id) {
            case R.id.btn_recv_msg:

                addRecvMessage("Message.");
                count++;
                lvChat.setSelection(count);
                break;
            case R.id.btn_send_msg:
                addSendMessage("Message.");
                count++;
                lvChat.setSelection(count);
                break;
            case R.id.btn_recv_img:
                addRecvPic();
                count++;
                lvChat.setSelection(count);
                break;
            case R.id.btn_send_img:
                addSendPic();
                count++;
                lvChat.setSelection(count);
                break;
            case R.id.btn_recv_voice:
                addRecvVoice();
                count++;
                lvChat.setSelection(count);
                break;
            case R.id.btn_send_voice:
                addSendVoice();
                count++;
                lvChat.setSelection(count);
                break;
            case R.id.btn_clearlist:
                ConfirmDialog cd=new ConfirmDialog();
                cd.setInfo(getResources().getString(R.string.confirm_clear), getResources().getString(R.string.confirm_clear_title),0);
                cd.show(getFragmentManager(), "ClearDialog");

                break;
            case R.id.facebook_btn_more:

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                if(more.getVisibility()==View.VISIBLE) {
                    more.setVisibility(View.GONE);
                    btn_more.setChecked(false);
                }
                else {

                    more.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.btn_screenshot:

                more.setVisibility(View.GONE);
                btn_more.setChecked(false);
                chatbox.requestFocus();
                ConfirmDialog cs=new ConfirmDialog();
                cs.setInfo(getResources().getString(R.string.confirm_screenshot), getResources().getString(R.string.confirm_screenshot_title), 1);
                cs.show(getFragmentManager(), "ScreenShotDialog");
                break;
            case R.id.btn_share:

                more.setVisibility(View.GONE);
                btn_more.setChecked(false);
                chatbox.requestFocus();
                new Handler().postDelayed(r, 1);

                break;
            case R.id.btn_save:
                Intent i=new Intent(MessengerActivity.this, SaveTemplateActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                Bundle b=new Bundle();
                b.putParcelableArrayList("msgList", (ArrayList<? extends Parcelable>) msgList);
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.btn_template:
                Intent ILT=new Intent(MessengerActivity.this,LoadTemplateActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(ILT,RESULT_LOAD_TEMPLATE);

                break;
            case R.id.btn_theme:
                Toast toast;
                toast = Toast.makeText(getApplicationContext(),
                        R.string.not_support, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                break;
        }

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
            if (more.getVisibility() == View.VISIBLE) {
                more.setVisibility(View.GONE);
                btn_more.setChecked(false);
                return false;
            }

        return  super.dispatchKeyEvent(event);
    }
    public void showImageDialog(final int requestcode)
    {
        final CharSequence[]items={getResources().getString(R.string.camera),getResources().getString(R.string.gallery)};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        startActivityForResult(takeImage(), requestcode);
                        break;
                    case 1:
                        startActivityForResult(pickImage(), requestcode);
                        break;
                }
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
    public Intent pickImage()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }
    public Intent takeImage()
    {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String temName=new DateFormat().format("yyMMdd_hhmmss",System.currentTimeMillis())+"_"+(Math.random()*100)+".jpg";
        image_path=path+File.separator+temName;
        File file = new File(image_path);
        if (file.exists()) {
            file.delete();
        }
        Uri u = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
        return intent;
    }
    public  void selectRecvAvatar(View view)
    {
        showImageDialog(RESULT_RECV_IMAGE);
    }
    public  void selectTheme()
    {
        showImageDialog(RESULT_CHG_THEME);
    }

    public void changeImg(int position)
    {
        this.position=position;
        showImageDialog(RESULT_CHANGE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if ((requestCode == RESULT_RECV_IMAGE  || requestCode==RESULT_CHANGE_IMAGE||requestCode==RESULT_CHG_THEME ||requestCode==RESULT_LOAD_TEMPLATE )&& (resultCode == RESULT_OK
        )) {
            Bitmap bitmap=null;


            try {
                if(image_path!=null)
                {
                    File file = new File(image_path);
                    try {
                        Uri uri = Uri.fromFile(file);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                        options.inSampleSize = 4;
                        options.inJustDecodeBounds = false;
                        bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                        android.provider.MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        image_path=null;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else if(null != data) {
                    Uri uri = data.getData();
                    ContentResolver cr = this.getContentResolver();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 3;
                    bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }




            switch (requestCode) {
                case 1:
                    Message.setRecvBitmap(bitmap);
                    break;
                case 2:
                    Message.setSendBitmap(bitmap);
                    break;
                case 3:
                    msg=msgList.get(position);
                    msg.setBitmap( bitmap);

                    break;
                case 4:
                    BitmapDrawable DrawableBitmap = new BitmapDrawable(bitmap);
                    findViewById(R.id.root_layout).setBackground(DrawableBitmap);
                    break;

            }

            lvChat.setAdapter(facebookAdapter);

        }
        else if (resultCode==6)
        {
            msgList=data.getParcelableArrayListExtra("msgList");
            facebookAdapter=new MessengerAdapter(this, msgList,this);
            name.setText(Message.Name);
            lvChat.setAdapter(facebookAdapter);
        }
    }
    private BaseAdapter getAdapter(){
        return facebookAdapter;
    }

    public void addRecvMessage(String str)
    {
        msg = new Message();
        msg.setType(MessengerAdapter.RECV_TEXT);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }
    public void addSendMessage(String str)
    {
        msg = new Message();
        msg.setType(MessengerAdapter.SEND_TEXT);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }
    public void addRecvPic()
    {
        msg = new Message();
        msg.setType(MessengerAdapter.RECV_IMAGE);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }
    public void addSendPic()
    {
        msg = new Message();
        msg.setType(MessengerAdapter.SEND_IMAGE);

        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }
    public void addRecvVoice()
    {
        msg = new Message();
        msg.setType(MessengerAdapter.RECV_AUDIO);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }
    public void addSendVoice()
    {
        msg = new Message();
        msg.setType(MessengerAdapter.SEND_AUDIO);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }
    public void addTimeStamp()
    {
        msg = new Message();
        msg.setType(MessengerAdapter.VALUE_TIME_TIP);
      //  msg.setValue(str);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }
    @Override
    public void onFinishConfirmDialog(int code) {
        if(code==0) {
            msgList.clear();
            findViewById(R.id.root_layout).setBackgroundColor(Color.parseColor("#ebebeb"));
            count = -1;
            lvChat.setAdapter(getAdapter());
            addTimeStamp();
        }
        else if(code==1)
        {
            MessageUtils.takeScreenshot(this);
        }
    }
}
