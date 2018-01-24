package com.hulian.huliantecdemo.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.hulian.huliantecdemo.BaseActivity;
import com.hulian.huliantecdemo.R;
import com.hulian.huliantecdemo.dao.UserEntityDao;
import com.hulian.huliantecdemo.entity.UserEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchUserActivity extends BaseActivity implements TextWatcher {
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.listview)
    RecyclerView listview;

    private DaoManager daoManager;

    private UserEntityDao userEntityDao;


    private AddressBookAdapter addressBookAdapter;
    private List<UserEntity> userEntityList=new ArrayList<UserEntity>();

    private String content="";


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_search_user);

    }

    @Override
    protected void initData() {

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        editText.addTextChangedListener(this);


        daoManager=DaoManager.getInstance();
        userEntityDao=daoManager.getDaoSession().getUserEntityDao();
        addressBookAdapter=new AddressBookAdapter(this,userEntityList);
        LinearLayoutManager ms= new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(ms);
        listview.setAdapter(addressBookAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBusInformBus

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        content= String.valueOf(charSequence);
        //查询
        userEntityList.clear();
        userEntityList.addAll(userEntityDao.queryBuilder().whereOr(UserEntityDao.Properties.User_bdid.like("%" + content + "%"),UserEntityDao.Properties.User_name.like("%" + content + "%"),UserEntityDao.Properties.User_dw.like("%" + content + "%"),UserEntityDao.Properties.User_bz.like("%" + content + "%")).list());
        addressBookAdapter.refresh(userEntityList);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(AddressBookBus event) {
        //查询
        userEntityList.clear();
        userEntityList.addAll(userEntityDao.queryBuilder().whereOr(UserEntityDao.Properties.User_bdid.like("%" + content + "%"),UserEntityDao.Properties.User_name.like("%" + content + "%"),UserEntityDao.Properties.User_dw.like("%" + content + "%"),UserEntityDao.Properties.User_bz.like("%" + content + "%")).list());
        addressBookAdapter.refresh(userEntityList);
    }
}
