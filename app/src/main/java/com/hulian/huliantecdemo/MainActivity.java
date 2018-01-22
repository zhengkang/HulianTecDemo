package com.hulian.huliantecdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hulian.huliantecdemo.code.SendCodeActivity;
import com.hulian.huliantecdemo.headimg.HeadImgActivity;
import com.hulian.huliantecdemo.imgs.SelectImgsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    //c
    static {
        System.loadLibrary("native-lib");
    }

    @Bind(R.id.code)
    TextView code;
    @Bind(R.id.imgs)
    TextView imgs;
    @Bind(R.id.headimg)
    TextView headimg;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }




    @OnClick({R.id.code, R.id.imgs, R.id.headimg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.code:
                goUI(this, SendCodeActivity.class);//跳转到验证码的模块··············
                break;
            case R.id.imgs:
                goUI(this, SelectImgsActivity.class);
                break;
            case R.id.headimg:
                goUI(this, HeadImgActivity.class);
                break;
        }
    }
}
