package com.hulian.huliantecdemo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.hulian.huliantecdemo.fragment.HomeFragment;
import com.hulian.huliantecdemo.view.tablayout.FragmentTabHost;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.realcontent)
    FrameLayout realcontent;
    @Bind(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @Bind(R.id.tabhost)
    FragmentTabHost tabhost;


    /**
     * 布局填充器
     */
    private LayoutInflater mLayoutInflater;

    /**
     * Fragment数组界面
     */
    private Class mFragmentArray[] = {
            HomeFragment.class,
            HomeFragment.class,
            HomeFragment.class,
            HomeFragment.class
    };
    /**
     * 存放图片数组
     */
    private int mImageArray[] = {
            R.drawable.first,
            R.drawable.first,
            R.drawable.first,
            R.drawable.first
    };

    /**
     * 选修卡文字
     */
    private int mTextArray[] = {
            R.string.shouye,
            R.string.shouye,
            R.string.shouye,
            R.string.shouye
    };

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        initview();

        setListen();
    }

    private void setListen() {

        tabhost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabhost.setCurrentTab(2);
            }
        });
        tabhost.getTabWidget().getChildTabViewAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    tabhost.setCurrentTab(3);
            }
        });
        tabhost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    tabhost.setCurrentTab(1);
            }
        });
    }


    private void initview() {
        mLayoutInflater = LayoutInflater.from(this);
        tabhost.setup(this, getSupportFragmentManager(), R.id.realcontent);
        // 得到fragment的个数
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(this.getResources().getString(mTextArray[i]))
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            tabhost.addTab(tabSpec, mFragmentArray[i], null);
            // 设置Tab按钮的背景
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            tabhost.getTabWidget().setDividerDrawable(null);

        }
    }

    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tabhost_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_img);
        imageView.setImageResource(mImageArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.item_text);
        textView.setText(mTextArray[index]);
        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }



}
