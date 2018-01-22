package com.hulian.huliantecdemo.code;

import android.os.Bundle;
import android.widget.TextView;

import com.hulian.huliantecdemo.BaseActivity;
import com.hulian.huliantecdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发送验证码
 */
public class SendCodeActivity extends BaseActivity {

    @Bind(R.id.send_code)
    TextView sendCode;

    CountDownTimerUtils mCountDownTimerUtils;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_send_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mCountDownTimerUtils = new CountDownTimerUtils(sendCode, 30000, 1000);
    }


    @OnClick(R.id.send_code)
    public void onViewClicked() {
        mCountDownTimerUtils.start();
    }
}
