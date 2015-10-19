package com.wixappsite.fakechat.fakechat.Utils;

import android.text.Editable;
import android.text.TextWatcher;

import com.wixappsite.fakechat.fakechat.Obj.Message;

/**
 * Created by Wix on 2015/10/2.
 */
public class TextListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    @Override
    public void afterTextChanged(Editable s) {
Message.Name=s.toString();
    }
}
