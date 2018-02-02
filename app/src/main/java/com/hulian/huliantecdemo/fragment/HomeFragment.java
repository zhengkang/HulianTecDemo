package com.hulian.huliantecdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hulian.huliantecdemo.R;
import com.hulian.huliantecdemo.code.SendCodeActivity;
import com.hulian.huliantecdemo.database.AddressBookActivity;
import com.hulian.huliantecdemo.design.CoordinatorLayoutActivity;
import com.hulian.huliantecdemo.headimg.HeadImgActivity;
import com.hulian.huliantecdemo.imgs.SelectImgsActivity;
import com.hulian.huliantecdemo.refresh.RefeshActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/28.
 */

public class HomeFragment extends Fragment {

    @Bind(R.id.code)
    TextView code;
    @Bind(R.id.imgs)
    TextView imgs;
    @Bind(R.id.headimg)
    TextView headimg;
    @Bind(R.id.database)
    TextView database;
    @Bind(R.id.refresh)
    TextView refresh;
    @Bind(R.id.Coordina)
    TextView Coordina;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(this, view);


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.code, R.id.imgs, R.id.headimg, R.id.database,R.id.refresh, R.id.Coordina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.code:
                goUI(getActivity(), SendCodeActivity.class);//跳转到验证码的模块··············
                break;
            case R.id.imgs:
                goUI(getActivity(), SelectImgsActivity.class);
                break;
            case R.id.headimg:
                goUI(getActivity(), HeadImgActivity.class);
                break;
            case R.id.database:
                goUI(getActivity(), AddressBookActivity.class);
            case R.id.refresh:
                goUI(getActivity(), RefeshActivity.class);
                break;
            case R.id.Coordina:
                goUI(getActivity(), CoordinatorLayoutActivity.class);
                break;
        }
    }

    public static void goUI(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);

    }
}
