package com.wixappsite.fakechat.fakechat.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wixappsite.fakechat.fakechat.Obj.Message;
import com.wixappsite.fakechat.fakechat.Activity.MessengerActivity;
import com.wixappsite.fakechat.fakechat.R;

import java.util.List;

public class MessengerAdapter extends BaseAdapter {

    public static final int VALUE_TIME_TIP = 0;
    public static final int RECV_TEXT = 1;
    public static final int RECV_IMAGE = 2;
    public static final int RECV_AUDIO = 3;
    public static final int SEND_TEXT = 4;
    public static final int SEND_IMAGE = 5;
    public static final int SEND_AUDIO = 6;
    private LayoutInflater mInflater;
    private MessengerActivity messengerActivity;
    private List<Message> myList;
//MyListener myListener = null;

    public MessengerAdapter(Context context, List<Message> myList,MessengerActivity messengerActivity) {
        this.myList = myList;
        this.messengerActivity=messengerActivity;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public MessengerAdapter(Context context, List<Message> myList) {
        this.myList = myList;

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return myList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {

        final Message msg = myList.get(position);
        int type = getItemViewType(position);
        ViewHolderTime holderTime = null;
        ViewHolderSendText holderSendText = null;
        ViewHolderSendImg holderSendImg = null;
        ViewHolderSendAudio holderSendAudio = null;
        ViewHolderRecvText holderRecvText = null;
        ViewHolderRecvImg holderRecvImg = null;
        ViewHolderRecvAudio holderRecvAudio = null;
        if (convertView == null || convertView.getTag()==null) {
            //	myListener = new MyListener(position);
            switch (type) {
                case VALUE_TIME_TIP:
                    holderTime = new ViewHolderTime();
                    convertView = mInflater.inflate(R.layout.list_item_time_tip,
                            null);
               //     holderTime.tvTimeTip = (TextView) convertView
                  //          .findViewById(R.id.timestamp);
                //    holderTime.tvTimeTip.setText(msg.getValue());
                //    convertView.setTag(holderTime);
                    break;

                case RECV_TEXT:
                    holderRecvText = new ViewHolderRecvText();
                    convertView=mInflater.inflate(R.layout.messenger_recv_msg,
                            null);
                    holderRecvText.ivRecvIcon = (ImageView) convertView
                            .findViewById(R.id.iv_recv_avatar);
                    holderRecvText.btnRecvText = (EditText) convertView
                            .findViewById(R.id.et_recv_msg);
                    holderRecvText.btnRecvText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            msg.setValue(s.toString());
                        }
                    });
                    holderRecvText.btnRecvText.setText(msg.getValue());
                    if(Message.getRecvBitmap()!=null)
                        holderRecvText.ivRecvIcon.setImageBitmap(Message.getRecvBitmap());

                //    convertView.setTag(holderRecvText);
                    break;

                case RECV_IMAGE:
                    holderRecvImg = new ViewHolderRecvImg();
                    convertView = mInflater.inflate(R.layout.messenger_row_recv_img,
                            null);
                    holderRecvImg.ivRecvIcon = (ImageView) convertView
                            .findViewById(R.id.iv_recv_avatar);
                    holderRecvImg.ivRecvImage = (ImageView) convertView
                            .findViewById(R.id.iv_recv_pic);
                    holderRecvImg.ivRecvImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           messengerActivity.changeImg(position);
                        }
                    });
                    if(Message.getRecvBitmap()!=null)
                        holderRecvImg.ivRecvIcon.setImageBitmap(Message.getRecvBitmap());
                    if(msg.getBitmap()!=null)
                        holderRecvImg.ivRecvImage.setImageBitmap(msg.getBitmap());
                    else
                        holderRecvImg.ivRecvImage.setImageResource(R.drawable.add_img);
               //     convertView.setTag(holderRecvImg);
                    break;

                case RECV_AUDIO:
                    holderRecvAudio = new ViewHolderRecvAudio();
                    convertView = mInflater.inflate(R.layout.messenger_row_recv_voice,
                            null);
                    holderRecvAudio.ivRecvIcon = (ImageView) convertView
                            .findViewById(R.id.iv_recv_avatar);
                /*    holderRecvAudio.btnRecvAudio = (Button) convertView
                            .findViewById(R.id.iv_recv_voice);*/
                    holderRecvAudio.tvRecvAudioTime = (EditText) convertView
                            .findViewById(R.id.et_voice_length);
                    holderRecvAudio.tvRecvAudioTime.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
msg.setVoicetime(s.toString());
                        }
                    });
                    if(msg.getVoicetime()!=null)
                        holderRecvAudio.tvRecvAudioTime.setText(msg.getVoicetime());

                    if(Message.getRecvBitmap()!=null)
                        holderRecvAudio.ivRecvIcon.setImageBitmap(Message.getRecvBitmap());
                    //    holderRecvAudio.tvRecvAudioTime.setText(msg.getValue());
                   // convertView.setTag(holderRecvAudio);
                    break;

                case SEND_TEXT:
                    holderSendText= new ViewHolderSendText();
                    convertView = mInflater.inflate(R.layout.messenger_send_msg,
                            null);
             /*       holderSendText.ivSendIcon = (ImageView) convertView
                            .findViewById(R.id.iv_send_avatar);*/
                    holderSendText.btnSendText = (EditText) convertView
                            .findViewById(R.id.et_send_msg);

                    holderSendText.btnSendText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            msg.setValue(s.toString());

                        }
                    });

                    holderSendText.btnSendText.setText(msg.getValue());
                /*    if(Message.getSendBitmap()!=null)
                        holderSendText.ivSendIcon.setImageBitmap(Message.getSendBitmap());*/
                  //  convertView.setTag(holderSendText);
                    break;

                case SEND_IMAGE:
                    holderSendImg= new ViewHolderSendImg();
                    convertView = mInflater.inflate(R.layout.messenger_row_send_img,
                            null);
                /*    holderSendImg.ivSendIcon = (ImageView) convertView
                            .findViewById(R.id.iv_send_avatar);*/
                    holderSendImg.ivSendImage = (ImageView) convertView
                            .findViewById(R.id.iv_send_pic);
                    holderSendImg.ivSendImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {messengerActivity.changeImg(position);
                        }
                    });
               /*     if(Message.getSendBitmap()!=null)
                        holderSendImg.ivSendIcon.setImageBitmap(Message.getSendBitmap());*/
                    if(msg.getBitmap()!=null)
                        holderSendImg.ivSendImage.setImageBitmap(msg.getBitmap());
                    else
                        holderSendImg.ivSendImage.setImageResource(R.drawable.add_img);


                 //   convertView.setTag(holderSendImg);
                    break;

                case SEND_AUDIO:
                    holderSendAudio=new ViewHolderSendAudio();
                    convertView = mInflater.inflate(R.layout.messenger_row_send_voice,
                            null);
        /*            holderSendAudio.ivSendIcon = (ImageView) convertView
                            .findViewById(R.id.iv_send_avatar);*/
          /*          holderSendAudio.btnSendAudio = (Button) convertView
                            .findViewById(R.id.iv_send_voice);*/
                    holderSendAudio.tvSendAudioTime = (EditText) convertView
                            .findViewById(R.id.et_voice_length);
                    holderSendAudio.tvSendAudioTime.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
msg.setVoicetime(s.toString());
                        }
                    });
                    if(msg.getVoicetime()!=null)
                    {
                    holderSendAudio.tvSendAudioTime.setText(msg.getVoicetime());
                    }
      /*              if(Message.getSendBitmap()!=null)
                        holderSendAudio.ivSendIcon.setImageBitmap(Message.getSendBitmap());*/
                  //  convertView.setTag(holderSendAudio);
                    break;
            }

        }
        else {


			switch (type) {
				case VALUE_TIME_TIP:
					holderTime=(ViewHolderTime)convertView.getTag();
					//holderTime.tvTimeTip.setText(msg.getValue());
					break;
				case RECV_TEXT:

					holderRecvText=(ViewHolderRecvText)convertView.getTag();
					if(holderRecvText.btnRecvText.getText()!=null)
					holderRecvText.btnRecvText.setText(msg.getValue());
					break;
				case RECV_IMAGE:
					holderRecvImg=(ViewHolderRecvImg)convertView.getTag();
					break;
				case RECV_AUDIO:
					holderRecvAudio=(ViewHolderRecvAudio)convertView.getTag();
					holderRecvAudio.tvRecvAudioTime.setText(msg.getValue());
					break;
				case SEND_TEXT:
					holderSendText=(ViewHolderSendText)convertView.getTag();
					if(holderSendText.btnSendText.getText()!=null)
holderSendText.btnSendText.setText(msg.getValue());
					break;
				case SEND_IMAGE:
					holderSendImg=(ViewHolderSendImg)convertView.getTag();
				//	holderSendImg.ivSendImage.setImageResource(R.drawable.test);
					break;
				case SEND_AUDIO:
					holderSendAudio=(ViewHolderSendAudio)convertView.getTag();
					//holderSendAudio.tvSendAudioTime.setText(msg.getValue());
					break;

				default:
					break;
			}
            convertView.setTag(holderRecvAudio);
            convertView.setTag(holderRecvImg);
            convertView.setTag(holderRecvText);
            convertView.setTag(holderSendAudio);
            convertView.setTag(holderSendImg);
            convertView.setTag(holderSendText);
            convertView.setTag(holderTime);
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {

        Message msg = myList.get(position);
        int type = msg.getType();

        return type;
    }


    @Override
    public int getViewTypeCount() {
        return 7;
    }

    class ViewHolderTime {
        private TextView tvTimeTip;
    }

    class ViewHolderSendText {
        private ImageView ivSendIcon;
        private EditText btnSendText;
    }

    class ViewHolderSendImg {
        private ImageView ivSendIcon;
        private ImageView ivSendImage;
    }

    class ViewHolderSendAudio {
        private ImageView ivSendIcon;
        private Button btnSendAudio;
        private EditText tvSendAudioTime;
    }

    class ViewHolderRecvText {
        private ImageView ivRecvIcon;
        private EditText btnRecvText;
    }

    class ViewHolderRecvImg {
        private ImageView ivRecvIcon;
        private ImageView ivRecvImage;
    }

    class ViewHolderRecvAudio {
        private ImageView ivRecvIcon;
        private Button btnRecvAudio;
        private EditText tvRecvAudioTime;
    }

}
