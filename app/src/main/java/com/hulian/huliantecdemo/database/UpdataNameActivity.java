package com.hulian.huliantecdemo.database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hulian.huliantecdemo.BaseActivity;
import com.hulian.huliantecdemo.R;
import com.hulian.huliantecdemo.dao.UserEntityDao;
import com.hulian.huliantecdemo.entity.UserEntity;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdataNameActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.edittext_bz)
    EditText edittextBz;
    private UserEntity userEntity;

    private DaoManager daoManager;

    private UserEntityDao userEntityDao;

    private int tag=1;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_updata_name);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

        userEntity = (UserEntity) getIntent().getSerializableExtra("user");
        daoManager = DaoManager.getInstance();
        userEntityDao = daoManager.getDaoSession().getUserEntityDao();
        tag=getIntent().getIntExtra("tag",1);

        setData();
    }


    private void setData() {
        title.setText(userEntity.getUser_name());
        switch (tag) {
            case 1:
                edittextBz.setHint("输入用户名");
                edittextBz.setText(userEntity.getUser_name());
                break;
            case 2:
                edittextBz.setHint("输入北斗ID");
                edittextBz.setText(userEntity.getUser_bdid());
                edittextBz.setMaxHeight(9);
                break;
            case 3:
                edittextBz.setHint("输入用户单位");
                edittextBz.setText(userEntity.getUser_dw());
                break;
            case 4:
                edittextBz.setHint("输入备注");
                edittextBz.setText(userEntity.getUser_bz());
                break;
        }
    }

    @OnClick({R.id.back, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_save:
                if(edittextBz.getText().toString().trim().isEmpty()){
                    RxToast.info(this,"不能为空").show();
                    return;
                }
                showProgressDialog();
                switch (tag) {
                    case 1:
                        userEntity.setUser_name(edittextBz.getText().toString().trim());
                        break;
                    case 2:
                        if(edittextBz.getText().toString().trim().length()<7){
                            String id=edittextBz.getText().toString().trim();
                            for(int i=0;i<7-edittextBz.getText().toString().trim().length();i++){
                                id="0"+id;
                            }
                            userEntity.setUser_bdid(id);
                        }else{
                            userEntity.setUser_bdid(edittextBz.getText().toString().trim());
                        }
                        break;
                    case 3:
                        userEntity.setUser_dw(edittextBz.getText().toString().trim());
                        break;
                    case 4:
                        userEntity.setUser_bz(edittextBz.getText().toString().trim());
                        break;
                }
                userEntityDao.update(userEntity);
                EventBus.getDefault().post(new AddressBookBus("1"));
                dismissProgressDialog();
                RxToast.info(this,"修改成功").show();

                Intent intent=new Intent();
                intent.putExtra("user",userEntity);
                setResult(1000,intent);
                finish();

                break;
        }
    }
}
