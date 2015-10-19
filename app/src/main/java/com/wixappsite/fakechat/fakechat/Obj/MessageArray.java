package com.wixappsite.fakechat.fakechat.Obj;

import com.wixappsite.fakechat.fakechat.Obj.Message;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Wix on 2015/9/22.
 */
public class MessageArray implements Serializable {
    private String Name;
private  String FakeChat;
    private List<Message> listMessage;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Message> getListMessage() {
        return listMessage;
    }

    public void setListMessage(List<Message> listMessage) {
        this.listMessage = listMessage;
    }

    public String getFakeChat() {
        return FakeChat;
    }

    public void setFakeChat(String fakeChat) {
        FakeChat = fakeChat;
    }
}
