package com.wixappsite.fakechat.fakechat.Obj;

import android.content.Context;
import android.content.SharedPreferences;

import com.wixappsite.fakechat.fakechat.Adapter.ChatAdapter;

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
 * Created by Wix on 2015/10/2.
 */
public class ChatTemplate {
    MessageArray mA;
    private Context context;
    private List<MessageArray> msgListArray;
    Message msg;
    private SharedPreferences sp;
    List<Message> msgList;
public ChatTemplate(Context context)
{
    this.context=context;
    initTemplate();
    loadArray();

    joke1();
    joke2();
    joke3();
    joke4();
    joke5();
    saveArray();
}
void initTemplate()
{
    msgListArray = new ArrayList<>();
    saveArray();
}
    void joke1() {
          msgList= new ArrayList<Message>();
        addTimeStamp();
        addRecvMessage("一直好多车，我尿急~");
        addSendMessage("找加油站");
        addSendMessage("别憋坏了");
        addRecvMessage("塞住了");
        addSendMessage("啥?");
        addSendMessage("拿什么塞住了");
        addRecvMessage("我说车塞住。。。");
        saveMessage("模板1", "FakeChat");

    }

    void joke2() {
        msgList= new ArrayList<Message>();
        addTimeStamp();
        addSendMessage("女神，我最近老是梦到和你在一起");
        addRecvMessage("呵呵，和我在一起干嘛吗？");
        addSendMessage("没有干，就是单纯的在一起。");
        addSendMessage("吃饭了吗");
        addRecvMessage("TA开启了好友验证，您还是不是TA好友。请先发送好友验证要求，对方验证通过后，才能对话");
        saveMessage("模板2", "FakeChat");
    }

    void joke3() {
        msgList= new ArrayList<Message>();
        addTimeStamp();
        addSendMessage("手好了？");
        addRecvMessage("没呢..还有一个月！不过现在已经不影响了");
        addSendMessage("那你是用右手擦屁股还是左手擦屁股？");
        addRecvMessage("晕...左手");
        addSendMessage("你好恶心，我都是用手指");
        addRecvMessage("→_→");
        addSendMessage("手纸...");
        saveMessage("模板3", "FakeChat");
    }

    void joke4() {
        msgList= new ArrayList<Message>();
        addTimeStamp();
        addRecvMessage("先生，您好，这里是中国人寿保险，请问您平时外出都用什么交通工具");
        addSendMessage("轮椅");
        addRecvMessage("不好意思，打扰了");
        saveMessage("模板4", "FakeChat");
    }

    void joke5() {
        msgList= new ArrayList<Message>();
        addTimeStamp();
        addSendMessage("爸，我到底是啥血型？");
        addRecvMessage("B型，别信你妈说的，你的畜牲证明上有");
        addSendMessage("啥？？");
        saveMessage("模板5", "FakeChat");
    }
void saveMessage(String name,String FakeChat)
{
    mA = new MessageArray();
    mA.setListMessage(msgList);
    mA.setName(name);
    mA.setFakeChat(Message.Name);
    msgListArray.add(mA);
//msgList.clear();
}
    public void addRecvMessage(String str) {
        msg = new Message();
        msg.setType(ChatAdapter.RECV_TEXT);
  msg.setValue(str);
        msgList.add(msg);


    }

    public void addSendMessage(String str) {
        msg = new Message();
        msg.setType(ChatAdapter.SEND_TEXT);
        msg.setValue(str);
        msgList.add(msg);

    }

    public void addRecvPic() {
        msg = new Message();
        msg.setType(ChatAdapter.RECV_IMAGE);
        msgList.add(msg);
    }

    public void addSendPic() {
        msg = new Message();
        msg.setType(ChatAdapter.SEND_IMAGE);

        msgList.add(msg);

    }

    public void addRecvVoice() {
        msg = new Message();
        msg.setType(ChatAdapter.RECV_AUDIO);
        msgList.add(msg);

    }

    public void addSendVoice() {
        msg = new Message();
        msg.setType(ChatAdapter.SEND_AUDIO);
        msgList.add(msg);

    }
    public void addTimeStamp()
    {
        msg = new Message();
        msg.setType(ChatAdapter.VALUE_TIME_TIP);
        //    msg.setValue(str);
        msgList.add(msg);

    }

    void loadArray() {
        FileInputStream stream = null;
        try {
            stream = context.openFileInput("data.s");
            ObjectInputStream ois = new ObjectInputStream(stream);


                msgListArray = (List<MessageArray>) ois.readObject();

        } catch (FileNotFoundException e) {
            msgListArray = new ArrayList<>();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            msgListArray = new ArrayList<>();
            e.printStackTrace();
        } catch (OptionalDataException e) {
            msgListArray = new ArrayList<>();
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            msgListArray = new ArrayList<>();
            e.printStackTrace();
        } catch (EOFException e) {
            msgListArray = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            msgListArray = new ArrayList<>();
            e.printStackTrace();
        } finally {

        }
    }
    void saveArray()
    {
        FileOutputStream stream = null;
        try {
            stream =  context.openFileOutput("data.s", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(msgListArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
