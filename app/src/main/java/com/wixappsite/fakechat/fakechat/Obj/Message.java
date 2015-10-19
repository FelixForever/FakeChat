package com.wixappsite.fakechat.fakechat.Obj;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Message implements Parcelable,Serializable {

    public static String Name="FakeChat";
    private int type;
    private String value;
    private static Bitmap recvBitmap;
    private static Bitmap sendBitmap;
private   Bitmap bitmap;
    private int voice_length=1;
    private int length=1;
    private String time=null;
private String voicetime=null;
    public static Bitmap getSendBitmap() {
        return sendBitmap;
    }

    public static void setSendBitmap(Bitmap sendBitmap) {
        Message.sendBitmap = sendBitmap;
    }

    public static Bitmap getRecvBitmap() {
        return recvBitmap;
    }

    public static void setRecvBitmap(Bitmap recvBitmap) {
        Message.recvBitmap = recvBitmap;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
dest.writeInt(type);
        dest.writeString(value);
        
    }
    public static final Parcelable.Creator<Message> CREATOR = new Creator<Message>()
    {
        public Message createFromParcel(Parcel source)
        {
            Message Message = new Message();
            Message.type= source.readInt();
            Message.value= source.readString();

            return Message;
        }
        public Message[] newArray(int size)
        {
            return new Message[size];
        }
    };

    public int getVoice_length() {
        return voice_length;
    }

    public void setVoice_length(int voice_length) {
        this.voice_length = voice_length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVoicetime() {
        return voicetime;
    }

    public void setVoicetime(String voicetime) {
        this.voicetime = voicetime;
    }
}
