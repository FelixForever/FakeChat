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

import com.wixappsite.fakechat.fakechat.Activity.LineActivity;
import com.wixappsite.fakechat.fakechat.Obj.Message;
import com.wixappsite.fakechat.fakechat.R;
import com.wixappsite.fakechat.fakechat.Utils.MessageUtils;

import java.util.List;

public class LineAdapter extends BaseAdapter {

    public static final int VALUE_TIME_TIP = 0;
    public static final int RECV_TEXT = 1;
    public static final int RECV_IMAGE = 2;
    public static final int RECV_AUDIO = 3;
    public static final int SEND_TEXT = 4;
    public static final int SEND_IMAGE = 5;
    public static final int SEND_AUDIO = 6;
    private LayoutInflater mInflater;
    private LineActivity lineActivity;
    private List<Message> myList;
//MyListener myListener = null;

    public LineAdapter(Context context, List<Message> myList,LineActivity lineActivity) {
        this.myList = myList;
        this.lineActivity=lineActivity;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public LineAdapter(Context context, List<Message> myList) {
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
        if (convertView == null||convertView.getTag()==null) {
            //	myListener = new MyListener(position);
            switch (type) {
                case VALUE_TIME_TIP:
                    holderTime = new ViewHolderTime();
                    convertView = mInflater.inflate(R.layout.list_item_time_tip,
                            null);
                   // holderTime.tvTimeTip = (TextView) convertView
                   //         .findViewById(R.id.timestamp);
                  //  holderTime.tvTimeTip.setText(msg.getValue());
                //    convertView.setTag(holderTime);
                    break;

                case RECV_TEXT:
                    holderRecvText = new ViewHolderRecvText();
                    convertView=mInflater.inflate(R.layout.line_row_received_message,
                            null);
                    holderRecvText.ivRecvIcon = (ImageView) convertView
                            .findViewById(R.id.line_iv_recv_avatar);
                    holderRecvText.btnRecvText = (EditText) convertView
                            .findViewById(R.id.line_et_recv_msg);
                    holderRecvText.time= (TextView) convertView.findViewById(R.id.line_read_date);
                    if(msg.getTime()!=null)
                        holderRecvText.time.setText(msg.getTime());
                    else {
                        msg.setTime(MessageUtils.getCurrentTime(lineActivity));
                        holderRecvText.time.setText(MessageUtils.getCurrentTime(lineActivity));
                    }
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

                  //  convertView.setTag(holderRecvText);
                    break;

                case RECV_IMAGE:
                    holderRecvImg = new ViewHolderRecvImg();
                    convertView = mInflater.inflate(R.layout.line_row_received_picture,
                            null);
                    holderRecvImg.ivRecvIcon = (ImageView) convertView
                            .findViewById(R.id.iv_recv_avatar);
                    holderRecvImg.time= (TextView) convertView.findViewById(R.id.line_read_date);
                    if(msg.getTime()!=null)
                        holderRecvImg.time.setText(msg.getTime());
                    else {
                        msg.setTime(MessageUtils.getCurrentTime(lineActivity));
                        holderRecvImg.time.setText(MessageUtils.getCurrentTime(lineActivity));
                    }
                    holderRecvImg.ivRecvImage = (ImageView) convertView
                            .findViewById(R.id.iv_recv_pic);
                    holderRecvImg.ivRecvImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        lineActivity.changeImg(position);
                        }
                    });
                    if(Message.getRecvBitmap()!=null)
                        holderRecvImg.ivRecvIcon.setImageBitmap(Message.getRecvBitmap());
                    if(msg.getBitmap()!=null)
                        holderRecvImg.ivRecvImage.setImageBitmap(msg.getBitmap());

                   // convertView.setTag(holderRecvImg);
                    break;

                case RECV_AUDIO:
                    holderRecvAudio = new ViewHolderRecvAudio();
                    convertView = mInflater.inflate(R.layout.line_row_recv_voice,
                            null);
                    holderRecvAudio.ivRecvIcon = (ImageView) convertView
                            .findViewById(R.id.iv_recv_avatar);
                    holderRecvAudio.time= (TextView) convertView.findViewById(R.id.line_read_date);
                    holderRecvAudio.tvRecvAudioTime= (EditText) convertView.findViewById(R.id.line_voice_length);
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
                    if(msg.getTime()!=null)
                        holderRecvAudio.time.setText(msg.getTime());
                    else {
                        msg.setTime(MessageUtils.getCurrentTime(lineActivity));
                        holderRecvAudio.time.setText(MessageUtils.getCurrentTime(lineActivity));
                    }

                    if(Message.getRecvBitmap()!=null)
                        holderRecvAudio.ivRecvIcon.setImageBitmap(Message.getRecvBitmap());

                   // convertView.setTag(holderRecvAudio);
                    break;

                case SEND_TEXT:
                    holderSendText= new ViewHolderSendText();
                    convertView = mInflater.inflate(R.layout.line_row_send_message,
                            null);
//                    holderSendText.ivSendIcon = (ImageView) convertView
//                            .findViewById(R.id.iv_send_avatar);
                    holderSendText.btnSendText = (EditText) convertView
                            .findViewById(R.id.line_et_send_msg);

                    holderSendText.time= (TextView) convertView.findViewById(R.id.line_read_date);
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
                    if(msg.getTime()!=null)
                        holderSendText.time.setText(msg.getTime());
                    else {
                        msg.setTime(MessageUtils.getCurrentTime(lineActivity));
                        holderSendText.time.setText(MessageUtils.getCurrentTime(lineActivity));
                    }
                    holderSendText.btnSendText.setText(msg.getValue());
                  /*  if(Message.getSendBitmap()!=null)
                        holderSendText.ivSendIcon.setImageBitmap(Message.getSendBitmap());*/
                 //   convertView.setTag(holderSendText);
                    break;

                case SEND_IMAGE:
                    holderSendImg= new ViewHolderSendImg();
                    convertView = mInflater.inflate(R.layout.line_row_sent_picture,
                            null);
//                    holderSendImg.ivSendIcon = (ImageView) convertView
//                            .findViewById(R.id.iv_send_avatar);
                    holderSendImg.ivSendImage = (ImageView) convertView
                            .findViewById(R.id.line_iv_send_pic);
                    holderSendImg.ivSendImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lineActivity.changeImg(position);
                        }
                    });
                  holderSendImg.time= (TextView) convertView.findViewById(R.id.line_read_date);
                    if(msg.getTime()!=null)
                        holderSendImg.time.setText(msg.getTime());
                    else {
                        msg.setTime(MessageUtils.getCurrentTime(lineActivity));
                        holderSendImg.time.setText(MessageUtils.getCurrentTime(lineActivity));
                    }
                    if(msg.getBitmap()!=null)
                        holderSendImg.ivSendImage.setImageBitmap(msg.getBitmap());
                 //   convertView.setTag(holderSendImg);
                    break;

                case SEND_AUDIO:
                    holderSendAudio=new ViewHolderSendAudio();
                    convertView = mInflater.inflate(R.layout.line_row_send_voice,
                            null);
                    holderSendAudio.time= (TextView) convertView.findViewById(R.id.line_read_date);
                    holderSendAudio.tvSendAudioTime= (EditText) convertView.findViewById(R.id.line_voice_length);
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
                    if (msg.getVoicetime()!=null)
                        holderSendAudio.tvSendAudioTime.setText(msg.getVoicetime());

                    if(msg.getTime()!=null)
                        holderSendAudio.time.setText(msg.getTime());
                    else {
                        msg.setTime(MessageUtils.getCurrentTime(lineActivity));
                        holderSendAudio.time.setText(MessageUtils.getCurrentTime(lineActivity));
                    }

                    break;

                default:
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
                    holderRecvText.time.setText(msg.getTime());
					break;
				case RECV_IMAGE:
					holderRecvImg=(ViewHolderRecvImg)convertView.getTag();
                    holderRecvImg.time.setText(msg.getTime());
					break;
				case RECV_AUDIO:
					holderRecvAudio=(ViewHolderRecvAudio)convertView.getTag();
					holderRecvAudio.tvRecvAudioTime.setText(msg.getValue());
                    holderRecvAudio.time.setText((msg.getTime()));
					break;
				case SEND_TEXT:
					holderSendText=(ViewHolderSendText)convertView.getTag();
					if(holderSendText.btnSendText.getText()!=null)
holderSendText.btnSendText.setText(msg.getValue());
                    holderSendText.time.setText(msg.getTime());
					break;
				case SEND_IMAGE:
					holderSendImg=(ViewHolderSendImg)convertView.getTag();
                    holderSendImg.time.setText(msg.getTime());
				//	holderSendImg.ivSendImage.setImageResource(R.drawable.test);
					break;
				case SEND_AUDIO:
					holderSendAudio=(ViewHolderSendAudio)convertView.getTag();
                    holderSendAudio.time.setText((msg.getTime()));
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
        private TextView time;
    }

    class ViewHolderSendImg {
        private ImageView ivSendIcon;
        private ImageView ivSendImage;
        private TextView time;
    }

    class ViewHolderSendAudio {
        private ImageView ivSendIcon;
        private Button btnSendAudio;
        private EditText tvSendAudioTime;
        private TextView time;
    }

    class ViewHolderRecvText {
        private ImageView ivRecvIcon;
        private EditText btnRecvText;
        private TextView time;
    }

    class ViewHolderRecvImg {
        private ImageView ivRecvIcon;
        private ImageView ivRecvImage;
        private TextView time;
    }

    class ViewHolderRecvAudio {
        private ImageView ivRecvIcon;
        private Button btnRecvAudio;
        private EditText tvRecvAudioTime;
        private TextView time;
    }

}


