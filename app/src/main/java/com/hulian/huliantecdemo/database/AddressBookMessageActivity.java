package com.hulian.huliantecdemo.database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hulian.huliantecdemo.BaseActivity;
import com.hulian.huliantecdemo.R;
import com.hulian.huliantecdemo.dao.UserEntityDao;
import com.hulian.huliantecdemo.entity.UserEntity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressBookMessageActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.text_yhm)
    TextView textYhm;
    @Bind(R.id.text_bdid)
    TextView textBdid;
    @Bind(R.id.text_dw)
    TextView textDw;
    @Bind(R.id.text_bz)
    TextView textBz;

    private Intent intent;

    private UserEntity userEntity;

    private DaoManager daoManager;

    private UserEntityDao userEntityDao;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_address_book_message);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

        userEntity = (UserEntity) getIntent().getSerializableExtra("user");
        daoManager = DaoManager.getInstance();
        userEntityDao = daoManager.getDaoSession().getUserEntityDao();

        setData();
    }


    public void setData() {
        title.setText(userEntity.getUser_name());
        textYhm.setText(userEntity.getUser_name());
        textBdid.setText(userEntity.getUser_bdid());
        textBz.setText(userEntity.getUser_bz());
        textDw.setText(userEntity.getUser_dw());

    }

    @OnClick({R.id.back, R.id.delete, R.id.yhm, R.id.bdid, R.id.dw, R.id.bz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.delete:
                PopupWindowUtils.showDelete(title,this);
                break;
            case R.id.yhm:
                if(intent==null){
                    intent=new Intent(this,UpdataNameActivity.class);
                }
                intent.putExtra("user",userEntity);
                intent.putExtra("tag",1);
                startActivityForResult(intent,1000);
                break;
            case R.id.bdid:
                if(intent==null){
                    intent=new Intent(this,UpdataNameActivity.class);
                }
                intent.putExtra("user",userEntity);
                intent.putExtra("tag",2);
                startActivityForResult(intent,2000);
                break;
            case R.id.dw:
                if(intent==null){
                    intent=new Intent(this,UpdataNameActivity.class);
                }
                intent.putExtra("user",userEntity);
                intent.putExtra("tag",3);
                startActivityForResult(intent,3000);
                break;
            case R.id.bz:
                if(intent==null){
                    intent=new Intent(this,UpdataNameActivity.class);
                }
                intent.putExtra("user",userEntity);
                intent.putExtra("tag",4);
                startActivityForResult(intent,4000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            userEntity= (UserEntity) data.getSerializableExtra("user");
            setData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void deleteUser(){
        userEntityDao.deleteByKey(userEntity.getId());
        EventBus.getDefault().post(new AddressBookBus("1"));
        finish();
    }

}
