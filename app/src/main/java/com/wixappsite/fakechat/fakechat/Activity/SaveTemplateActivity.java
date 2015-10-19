package com.wixappsite.fakechat.fakechat.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wixappsite.fakechat.fakechat.Obj.Message;
import com.wixappsite.fakechat.fakechat.Obj.MessageArray;
import com.wixappsite.fakechat.fakechat.R;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wix on 2015/9/21.
 */
public class SaveTemplateActivity extends Activity {
    private static final String FIRST_TIME = "FirstTime";
 private    List<Message> msgList;
    private    List<MessageArray> msgListArray;
    private EditText et;
   private MessageArray mA;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_template);
        sp = getSharedPreferences("FakeChat", Context.MODE_PRIVATE);
        et= (EditText) findViewById(R.id.ac_tag_et_name);
        msgList = this.getIntent().getExtras()
                .getParcelableArrayList("msgList");
loadArray();
        String strData = "";
        for (Message p : msgList) {
            strData = strData + p.getType() + " " + p.getValue() + "\n";
        }

    }
    public void createMsgListArray()
    {
        mA = new MessageArray();
        mA.setListMessage(msgList);
        mA.setName(et.getText().toString());
        mA.setFakeChat(Message.Name);

    }
    public void addMsgListArray()
    {
       msgListArray.add(mA);
    }
  public   void onClick(View view)
    {
        int id=view.getId();
        switch (id) {
            case R.id.btn_save_template_back:
                finish();
                break;
            case  R.id.btn_save_template:
                if(TextUtils.isEmpty(et.getText())!=true) {
                    createMsgListArray();
                  addMsgListArray();
                   saveArray();
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),
                            R.string.tempalte_saved, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    finish();
                }
                else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),
                            R.string.template_warning, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
        }
    }

    void saveArray()
    {
        FileOutputStream stream = null;
        try {
            stream = this.openFileOutput("data.s", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(msgListArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    void loadArray()
    {
        FileInputStream stream = null;
        try {
            stream = this.openFileInput("data.s");
            ObjectInputStream ois = new ObjectInputStream(stream);
          if( sp.getBoolean(FIRST_TIME, true)==true) {
              msgListArray= new ArrayList<>();
              SharedPreferences.Editor e = sp.edit();
              e.putBoolean(FIRST_TIME, false);
              e.commit();
          }
            else
              msgListArray = (List<MessageArray>) ois.readObject();

        } catch (FileNotFoundException e) {
            msgListArray= new ArrayList<>();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            msgListArray= new ArrayList<>();
            e.printStackTrace();
        } catch (OptionalDataException e) {
            msgListArray= new ArrayList<>();
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            msgListArray= new ArrayList<>();
            e.printStackTrace();
        } catch (EOFException e)
        {
            msgListArray= new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            msgListArray= new ArrayList<>();
            e.printStackTrace();
        }
finally {

        }

    }

}
