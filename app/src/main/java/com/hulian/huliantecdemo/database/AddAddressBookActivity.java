package com.hulian.huliantecdemo.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hulian.huliantecdemo.BaseActivity;
import com.hulian.huliantecdemo.R;
import com.hulian.huliantecdemo.dao.UserEntityDao;
import com.hulian.huliantecdemo.entity.UserEntity;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAddressBookActivity extends BaseActivity {

    @Bind(R.id.edittext_yhm)
    EditText edittextYhm;
    @Bind(R.id.edittext_dbid)
    EditText edittextDbid;
    @Bind(R.id.edittext_dw)
    EditText edittextDw;
    @Bind(R.id.edittext_bz)
    EditText edittextBz;

    private DaoManager daoManager;

    private UserEntityDao userEntityDao;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_add_address_book);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

        daoManager=DaoManager.getInstance();
    }


    @OnClick({R.id.back, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_save:
                if(edittextYhm.getText().toString().trim().isEmpty()){
                    RxToast.info(this,"用户名为必填项").show();
                    return;
                }
                if(edittextDbid.getText().toString().trim().isEmpty()){
                    RxToast.info(this,"北斗ID为必填项").show();
                    return;
                }

                addData();
                break;
        }
    }

    /**
     * 添加用户
     */
    public void addData(){
        if(userEntityDao==null){
            userEntityDao=daoManager.getDaoSession().getUserEntityDao();
        }
        UserEntity userEntity=new UserEntity();
        if(edittextDbid.getText().toString().trim().length()<7){
            String id=edittextDbid.getText().toString().trim();
            for(int i=0;i<7-edittextDbid.getText().toString().trim().length();i++){
                id="0"+id;
            }
            userEntity.setUser_bdid(id);
        }else{
            userEntity.setUser_bdid(edittextDbid.getText().toString().trim());
        }
        userEntity.setUser_bz(edittextBz.getText().toString().trim());
        userEntity.setUser_dw(edittextDw.getText().toString().trim());
        userEntity.setUser_name(edittextYhm.getText().toString().trim());

        userEntityDao.insert(userEntity);
        EventBus.getDefault().post(new AddressBookBus("1"));
        RxToast.info(this,"添加成功").show();
        finish();

    }
}
