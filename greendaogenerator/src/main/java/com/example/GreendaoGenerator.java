package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreendaoGenerator {

    public static void main(String[] args) {
        Schema schema=new Schema(1,"com.hulian.huliantecdemo.entity");
        addcomponent(schema);
        schema.setDefaultJavaPackageDao("com.hulian.huliantecdemo.dao");
        try {
            new DaoGenerator().generateAll(schema,"C:\\Users\\Administrator\\Desktop\\huliandemo\\HulianTecDemo\\app\\src\\main\\java-gen");

        }catch (Exception e){
            e.printStackTrace();

        }

    }

    private static void addcomponent(Schema schema) {



        Entity classbean=schema.addEntity("UserEntity");
        classbean.addIdProperty();
        classbean.addStringProperty("user_id");
        classbean.addStringProperty("user_name");
        classbean.addStringProperty("user_bdid");
        classbean.addStringProperty("user_dw");
        classbean.addStringProperty("user_bz");
        classbean.addStringProperty("user_time");
        classbean.addStringProperty("user_location");
        classbean.addStringProperty("user_tag");
        classbean.addStringProperty("user_lat");
        classbean.addStringProperty("user_lot");

        classbean.implementsSerializable();







    }


}
