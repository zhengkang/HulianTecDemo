package com.hulian.huliantecdemo.database;

import android.content.Context;


import com.hulian.huliantecdemo.dao.DaoMaster;
import com.hulian.huliantecdemo.dao.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 创建数据库，创建数据库表，包含对数据库的增删改查，升级数据库版本
 * Created by wiky_zhang on 2017/7/18.
 */

public class DaoManager {
    private static String TAG=DaoManager.class.getName();
    private static final String DB_NAME="mydb.sqlite";
    private volatile static DaoManager daoManager;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static DaoMaster.DevOpenHelper helper;
    private Context context;

    /**
     * 单例获取模式
     * @return
     */
    public static DaoManager getInstance(){
        DaoManager instance=null;
        if (daoManager==null){
            synchronized (DaoManager.class){
                if (instance==null){
                    instance=new DaoManager();
                    daoManager=instance;
                }
            }
        }
        return daoManager;
    }

    /**
     * 判断是否存在数据库，如果没有就创建数据库
     * @return
     */
    public DaoMaster getDaoMaster(){
        if (daoMaster==null){
            DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(context,DB_NAME,null);
            daoMaster=new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public void init(Context context){
        this.context=context;
    }

    /**
     * 完成对数据库的增删改查工作的一个接口
     * @return
     */
    public DaoSession getDaoSession(){
        if (daoSession==null){
            if (daoMaster==null){
                daoMaster=getDaoMaster();
            }
            daoSession=daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 打开输出日志的操作，默认是关闭的
     */
    public void setDebug(){
        QueryBuilder.LOG_SQL=true;
        QueryBuilder.LOG_VALUES=true;
    }

    /**
     * 关闭所有的操作，数据库使用完毕必须关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper(){
        if (helper!=null){
            helper.close();
            helper=null;
        }
    }
    public void closeDaoSession(){
        if (daoSession!=null){
            daoSession.clear();
            daoSession=null;
        }
    }

public void upversion(int news){
    if (helper!=null){
//        helper.onUpgrade();
    }
}

}
