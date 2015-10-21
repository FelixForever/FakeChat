package com.wixappsite.fakechat.fakechat.Activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wixappsite.fakechat.fakechat.Adapter.ChatAdapter;
import com.wixappsite.fakechat.fakechat.Fx.ConfirmDialog;
import com.wixappsite.fakechat.fakechat.Obj.Message;
import com.wixappsite.fakechat.fakechat.R;
import com.wixappsite.fakechat.fakechat.Utils.BitmapCache;
import com.wixappsite.fakechat.fakechat.Utils.MessageUtils;
import com.wixappsite.fakechat.fakechat.Utils.TextListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Wix on 2015/9/14.
 */
public class ChatActivity extends BaseChatActivity implements ConfirmDialog.confirmDialogListener
{
    List<Message> msgList = new ArrayList<Message>();
    List<Message> msgListTemp = new ArrayList<Message>();
    private ListView lvChat;
    private LinearLayout fam;
    private LinearLayout fat;
    private LinearLayout more;
    private EditText chatbox;
    private InputMethodManager manager;
    private Boolean finish = true;
    private EditText name;
    private static int RESULT_CHG_THEME = 4;
    private static int RESULT_RECV_IMAGE = 1;
    private static int RESULT_TAKE_IMAGE = 6;
    private static int RESULT_SEND_IMAGE = 2;
    private static int RESULT_CHANGE_IMAGE = 3;
    private static int RESULT_LOAD_TEMPLATE = 5;
    private static int count = -1;
    private static int position = 0;
    File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
    Message msg;
    ChatAdapter chatAdapter;
    private RelativeLayout activity_chat;
    private String image_path;
    private String path;
    private ToggleButton btn_more;
    private int id;

//    private ViewPager mVPContainer;
//    private List<View>listViews;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        path = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM;
        lvChat = (ListView) findViewById(R.id.chatlist);

       // fam = (LinearLayout) findViewById(R.id.msg_bar);
        fat = (LinearLayout) findViewById(R.id.tool_bar);
        chatbox = (EditText) findViewById(R.id.et_chat_box);
        name = (EditText) findViewById(R.id.name);
        name.addTextChangedListener(new TextListener());
        btn_more = (ToggleButton) findViewById(R.id.btn_more);
        more = (LinearLayout) findViewById(R.id.more);
        chatAdapter = new ChatAdapter(this, msgList, this);
        lvChat.setAdapter(chatAdapter);
        lvChat.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                chatbox.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                more.setVisibility(View.GONE);
                btn_more.setChecked(false);
                return false;
            }
        });
        lvChat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {

                showDelDialog(position);
                return true;
            }
        });
        addTimeStamp();

       /* mVPContainer=(ViewPager)findViewById(R.id.vp_container);
        listViews = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        listViews.add(mInflater.inflate(R.layout.viewpager_1st, null));
        listViews.add(mInflater.inflate(R.layout.viewpager_2nd, null));
        mVPContainer.setAdapter(new MyPagerAdapter(listViews));
        mVPContainer.setCurrentItem(0);*/
    }

    public void showDelDialog(final int position)
    {
        final CharSequence[] items = {getResources().getString(R.string.delete)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                msgList.remove(position);
                lvChat.setAdapter(getAdapter());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    Runnable r = new Runnable()
    {
        @Override
        public void run()
        {
            takeScreen();
        }
    };

    void takeScreen()
    {
        MessageUtils.shareScreenshot(this);
    }


    public void onClick(View view)
    {
        int id = view.getId();

        switch (id)
        {
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
                ConfirmDialog cd = new ConfirmDialog();
                cd.setInfo(getResources().getString(R.string.confirm_clear), getResources().getString(R.string.confirm_clear_title), 0);
                cd.show(getFragmentManager(), "ClearDialog");

                break;
            case R.id.btn_more:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                if (more.getVisibility() == View.VISIBLE)
                {

                    more.setVisibility(View.GONE);
                    btn_more.setChecked(false);
                } else
                {

                    more.setVisibility(View.VISIBLE);

                }

                break;

            case R.id.btn_screenshot:

                more.setVisibility(View.GONE);
                btn_more.setChecked(false);
                chatbox.requestFocus();
                ConfirmDialog cs = new ConfirmDialog();
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
                Intent i = new Intent(ChatActivity.this, SaveTemplateActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                Bundle b = new Bundle();
                b.putParcelableArrayList("msgList", (ArrayList<? extends Parcelable>) msgList);
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.btn_template:
                Intent ILT = new Intent(ChatActivity.this, LoadTemplateActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(ILT, RESULT_LOAD_TEMPLATE);

                break;
            case R.id.btn_theme:
                selectTheme();
                break;

        }

    }

    private String getPhotoFileName()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public void showImageDialog(final int requestcode)
    {
        final CharSequence[] items = {getResources().getString(R.string.camera), getResources().getString(R.string.gallery)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //    builder.setTitle(R.string.confirm_del);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                switch (which)
                {
                    case 0:
                        startActivityForResult(takeImage(), requestcode);
                        break;
                    case 1:
                        startActivityForResult(pickImage(), requestcode);
                        break;
                }
            }
        });
        AlertDialog alert = builder.create();
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

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String temName = new DateFormat().format("yyMMdd_hhmmss", System.currentTimeMillis()) + "_" + (Math.random() * 100) + ".jpg";
        image_path = path + File.separator + temName;
        File file = new File(image_path);
        if (file.exists())
        {
            file.delete();
        }
        Uri u = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);

        return intent;
    }

    public void selectRecvAvatar(View view)
    {
        showImageDialog(RESULT_RECV_IMAGE);
    }

    public void selectTheme()
    {
        showImageDialog(RESULT_CHG_THEME);
    }

    public void selectSendAvatar(View view)
    {
        showImageDialog(RESULT_SEND_IMAGE);
    }

    public void changeImg(int position)
    {
        this.position = position;
        showImageDialog(RESULT_CHANGE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        BitmapCache bitmapCache = new BitmapCache();
        if ((requestCode == RESULT_RECV_IMAGE || requestCode == RESULT_SEND_IMAGE || requestCode == RESULT_CHANGE_IMAGE || requestCode == RESULT_CHG_THEME || requestCode == RESULT_LOAD_TEMPLATE) && (resultCode == RESULT_OK
        ))
        {
            Bitmap bitmap = null;

            try
            {
                if (image_path != null)
                {
                    File file = new File(image_path);
                    try
                    {
                        Uri uri = Uri.fromFile(file);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                        options.inSampleSize = 4;
                        options.inJustDecodeBounds = false;
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                        android.provider.MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        image_path = null;
                    } catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                } else if (null != data)
                {
                    Uri uri = data.getData();
                    ContentResolver cr = this.getContentResolver();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 3;
                    bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
                }
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

            Drawable d = new BitmapDrawable(getResources(), bitmap);

            switch (requestCode)
            {
                case 1:
                    Message.setRecvBitmap(bitmap);
                    break;
                case 2:
                    Message.setSendBitmap(bitmap);
                    break;
                case 3:
                    msg = msgList.get(position);
                    msg.setBitmap(bitmap);

                    break;
                case 4:
                    BitmapDrawable DrawableBitmap = new BitmapDrawable(bitmap);
                    findViewById(R.id.root_layout).setBackgroundDrawable(DrawableBitmap);
                    break;
            }
            lvChat.setAdapter(chatAdapter);
        } else if (resultCode == 6)
        {
            msgList = data.getParcelableArrayListExtra("msgList");
            chatAdapter = new ChatAdapter(this, msgList, this);
            name.setText(Message.Name);
            lvChat.setAdapter(chatAdapter);
        }
    }

    public void backPressed(View view)
    {
        finish();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
            if (more.getVisibility() == View.VISIBLE)
            {
                more.setVisibility(View.GONE);
                btn_more.setChecked(false);
                return false;
            }

        return super.dispatchKeyEvent(event);
    }

    public void ShowDialog(final int position)
    {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        final View Viewlayout = inflater.inflate(R.layout.voice_dialog,
                (ViewGroup) findViewById(R.id.layout_dialog));

        final TextView item1 = (TextView) Viewlayout.findViewById(R.id.tv_voice_length);

        popDialog.setTitle(getResources().getString(R.string.get_voice_length));
        popDialog.setView(Viewlayout);

        final SeekBar seek1 = (SeekBar) Viewlayout.findViewById(R.id.voice_seek_bar);
        seek1.setMax(60);
        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {

                item1.setText(getResources().getString(R.string.voice_length) + ":" + progress + getResources().getString(R.string.second));
            }

            public void onStartTrackingTouch(SeekBar arg0)
            {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar)
            {
                // TODO Auto-generated method stub

            }
        });

        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        msg = msgList.get(position);

                        msg.setVoice_length(seek1.getProgress() * 2 + 1);
                        msg.setLength(seek1.getProgress());
                        lvChat.setAdapter(getAdapter());
                        dialog.dismiss();
                    }
                });

        popDialog.create();
        popDialog.show();

    }

    @Override
    protected void onDestroy()
    {
        System.gc();
        super.onDestroy();
    }

    private BaseAdapter getAdapter()
    {
        return chatAdapter;
    }

    public void addRecvMessage(String str)
    {
        msg = new Message();
        msg.setType(ChatAdapter.RECV_TEXT);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }

    public void addSendMessage(String str)
    {
        msg = new Message();
        msg.setType(ChatAdapter.SEND_TEXT);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }

    public void addRecvPic()
    {
        msg = new Message();
        msg.setType(ChatAdapter.RECV_IMAGE);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }

    public void addSendPic()
    {
        msg = new Message();
        msg.setType(ChatAdapter.SEND_IMAGE);

        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }

    public void addRecvVoice()
    {
        msg = new Message();
        msg.setType(ChatAdapter.RECV_AUDIO);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }

    public void addSendVoice()
    {
        msg = new Message();
        msg.setType(ChatAdapter.SEND_AUDIO);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }

    public void addTimeStamp()
    {
        msg = new Message();
        msg.setType(ChatAdapter.VALUE_TIME_TIP);
        //    msg.setValue(str);
        msgList.add(msg);
        lvChat.setAdapter(getAdapter());
    }

    @Override
    public void onFinishConfirmDialog(int code)
    {
        if (code == 0)
        {
            msgList.clear();
            findViewById(R.id.root_layout).setBackgroundColor(Color.parseColor("#ebebeb"));
            count = -1;
            addTimeStamp();
            //    lvChat.setAdapter(getAdapter());
        } else if (code == 1)
        {
            MessageUtils.takeScreenshot(this);
        }
    }




}

