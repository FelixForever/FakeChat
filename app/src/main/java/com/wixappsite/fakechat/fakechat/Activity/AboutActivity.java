package com.wixappsite.fakechat.fakechat.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wixappsite.fakechat.fakechat.App;
import com.wixappsite.fakechat.fakechat.R;
import com.wixappsite.fakechat.fakechat.Utils.DoNet;

//import cn.waps.AppConnect;

/**
 * Created by Wix on 2015/10/2.
 */
public class AboutActivity extends Activity
{
    private ListView listview;
    private String[] items = new String[4];
private final static String svr="http://120.27.30.238/HomePage/Home/SuggestionAdd?";
private final  static String chkVersion="http://120.27.30.238/HomePage/Home/CheckUpdate/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        items=getResources().getStringArray(R.array.about_list);
        ListAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_view_simple,R.id.tv_maintip,items);
        listview= (ListView) findViewById(R.id.about_listview);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                        builder.setTitle("FakeChat");
                        builder.setMessage(getResources().getString(R.string.developBy));
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
                        break;
                    case 1:

                        Uri uri= Uri.parse("http://www.fakechat.cn/");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);
                        break;
                    case 2:
                        ShowDialog();
                        break;
                    case 3:

                                new DoNet(2, chkVersion,"VersionID=1", new DoNet.OkCallBack() {
                                    @Override
                                    public Void OnOk(final String result) {
                                        if(result.length()==0){

                                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.get_failed), Toast.LENGTH_SHORT).show();
                                        }
                                        else if(result.equals("F")) {
                                            showToast(1);
                                        }
                                        else
                                        {
                                            AlertDialog.Builder builder=new AlertDialog.Builder(AboutActivity.this);
                                            builder.setTitle(getResources().getString(R.string.detected_new_version));
                                            builder.setMessage(getResources().getString(R.string.download));
                                            builder.setPositiveButton(getResources().getString(R.string.dl), new DialogInterface.OnClickListener() { //设置确定按钮
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Uri uri1 = Uri.parse(result);
                                                    Intent it1 = new Intent(Intent.ACTION_VIEW, uri1);
                                                    startActivity(it1);
                                                }
                                            });
                                            builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });
                                            AlertDialog alert=builder.create();
                                            alert.show();
                                        }
                                        return null;
                                    }
                                }, new DoNet.NotOkCallBack() {
                                    @Override
                                    public Void OnNotOk() {

                                        return null;
                                    }
                                });

                        break;

                }
            }
        });
    }

    public void onClick(View view)
    {
        int id=view.getId();
        switch(id)
        {
            case R.id.about_btn_back:
                finish();
                break;
        }
    }
    public void ShowDialog()
    {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        final View Viewlayout = inflater.inflate(R.layout.feedback_dialog,
                (ViewGroup) findViewById(R.id.feedback_dialog));



        popDialog.setTitle(getResources().getString(R.string.text_feedback_dialog));
        popDialog.setView(Viewlayout);

        final EditText edit1 = (EditText) Viewlayout.findViewById(R.id.feedback_title);
        final EditText edit2 = (EditText) Viewlayout.findViewById(R.id.feedback_content);


        popDialog.setPositiveButton("发送",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new DoNet(2, svr,"Title="+edit1.getText()+""+"&Contents="+edit2.getText()+""+"&IMEI="+ App.IMEI +""+"&SuggestionFrom=official", new DoNet.OkCallBack() {
                            @Override
                            public Void OnOk(String result) {
                                if(result.length()==0){
                                    Toast.makeText(getApplicationContext(), "当前无网络连接", Toast.LENGTH_SHORT).show();
                                }
                                return null;

                            }
                        }, new DoNet.NotOkCallBack() {
                            @Override
                            public Void OnNotOk() {
                                return null;
                            }
                        });
                    showToast(0);
                        dialog.dismiss();
                    }
                });

        popDialog.create();
        popDialog.show();

    }
void showToast(int toastCode)
{
    switch (toastCode) {
        case 0:
        Toast.makeText(this, getResources().getString(R.string.send_succeed), Toast.LENGTH_SHORT).show();
            break;
        case 1:
        Toast.makeText(this, getResources().getString(R.string.new_version), Toast.LENGTH_SHORT).show();
            break;
    }
}
}
