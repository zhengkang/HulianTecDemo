package com.hulian.huliantecdemo.database;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

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

public class AddressBookActivity extends BaseActivity {


    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.listview)
    RecyclerView listview;

    private DaoManager daoManager;

    private UserEntityDao userEntityDao;


    private AddressBookAdapter addressBookAdapter;
    private List<UserEntity> userEntityList=new ArrayList<UserEntity>();

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_address_book);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        daoManager=DaoManager.getInstance();
        daoManager.init(this);
        addressBookAdapter=new AddressBookAdapter(this,userEntityList);
        LinearLayoutManager ms= new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(ms);
        listview.setAdapter(addressBookAdapter);

        getData();

    }


    /**
     * 获取数据
     */
    public void getData(){
        userEntityDao=daoManager.getDaoSession().getUserEntityDao();
        userEntityList.addAll(userEntityDao.queryBuilder().orderDesc(UserEntityDao.Properties.Id).list());
        addressBookAdapter.refresh(userEntityList);
    }





    @OnClick({R.id.back, R.id.search, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search:
                goUI(this, SearchUserActivity.class);
                break;
            case R.id.add:
                goUI(this,AddAddressBookActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(AddressBookBus event) {
        userEntityList.clear();
        userEntityList.addAll(userEntityDao.queryBuilder().orderDesc(UserEntityDao.Properties.Id).list());
        addressBookAdapter.refresh(userEntityList);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBusInformBus
    }

}
