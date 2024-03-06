package com.vam.whitecoats.imageEditing;

import android.os.Handler;

import com.vam.whitecoats.ui.activities.CaseRoomEditAttachmentActivity;

public class UpdateImage implements Runnable {

    private CustomRelativeLayout _rl;

    private int _state;

    private int _button;

    private long _currentTime;

    private Handler _handler;

    public UpdateImage(CustomRelativeLayout rl, Handler handler) {
        _rl = rl;
        _handler = handler;
    }

    public void setState(int state, int button) {
        _state = state;
        _currentTime = System.currentTimeMillis();
        _button = button;
    }

    @Override
    public void run() {

        int amount = 2;
        int rotate =1;
        if (_button == CaseRoomEditAttachmentActivity.MINUS) {
            amount = -amount;
            rotate = -rotate;
        }
        _rl.handleEvent(_state, amount * 5,rotate   * 45);
        _handler.postDelayed(this, 100);
    }
}
