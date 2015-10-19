package com.wixappsite.fakechat.fakechat.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.wixappsite.fakechat.fakechat.Adapter.ChatAdapter;
import com.wixappsite.fakechat.fakechat.Obj.MessageArray;
import com.wixappsite.fakechat.fakechat.Obj.Message;
import com.wixappsite.fakechat.fakechat.R;

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
 * Created by Wix on 2015/9/22.
 */
public class LoadTemplateActivity extends Activity {
    private ListView listview;
    private    List<Message> msgList;
    private List<MessageArray> mA;
    private  String[] templateList = new String[]{};
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_template);


        listview = (ListView) findViewById(R.id.list_template);

        loadArray();
        adapter = new ArrayAdapter<String>(this, R.layout.list_view_simple, R.id.tv_maintip, templateList);
        listview.setAdapter(adapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDelDialog(position);
                return true;
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msgList = mA.get(position).getListMessage();
                Message.Name=mA.get(position).getFakeChat();
                Intent i = new Intent();
                i.putParcelableArrayListExtra("msgList", (ArrayList<? extends Parcelable>) msgList);
                setResult(6, i);
                finish();
            }
        });

    }
    ListAdapter newAdapter()
{
    adapter = new ArrayAdapter<String>(this, R.layout.list_view_simple, R.id.tv_maintip, templateList);
    return adapter;
}
    public void showDelDialog(final int position)
    {
        final CharSequence[]items={getResources().getString(R.string.delete)};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mA.remove(position);
                saveArray();
            loadArray();
                listview.setAdapter(newAdapter());
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
    public void onClick(View view)
    {
        finish();
    }

    void saveArray()
    {
        FileOutputStream stream = null;
        try {
            stream = this.openFileOutput("data.s", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(mA);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    void loadTemplate()
    {
        FileInputStream stream = null;
        try {
            stream = this.openFileInput("data.s");
            ObjectInputStream ois = new ObjectInputStream(stream);
            msgList= (List<Message>) ois.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      ChatAdapter chatAdapter=new ChatAdapter(this, msgList);

listview.setAdapter(chatAdapter);
    }
    void loadArray()
    {
        FileInputStream stream = null;
        try {
            stream = this.openFileInput("data.s");
            ObjectInputStream ois = new ObjectInputStream(stream);
           mA= (List<MessageArray>) ois.readObject();
templateList=new String[mA.size()];
            for (int i=0; i <  mA.size(); i++)
            {
             templateList[i]=(mA.get(i).getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
