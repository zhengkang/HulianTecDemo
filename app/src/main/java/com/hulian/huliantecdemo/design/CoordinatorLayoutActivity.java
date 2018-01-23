package com.hulian.huliantecdemo.design;



import com.hulian.huliantecdemo.BaseActivity;
import com.hulian.huliantecdemo.R;

import butterknife.ButterKnife;

public class CoordinatorLayoutActivity extends BaseActivity {


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_coordinator_layout);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);


    }


}
