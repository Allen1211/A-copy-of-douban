package com.allen.douban.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DaoFactroy {
    private Properties prop = new Properties();
    private static final DaoFactroy instance = new DaoFactroy();
    private static final Map<String,Object> daoMap = new HashMap<>();
    private DaoFactroy(){
        InputStream in = null;
        try {
            in = DaoFactroy.class.getClassLoader().getResourceAsStream("dao.properties");
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static DaoFactroy getInstance(){
        return instance;
    }

    public Object getDao(String daoName){
        Object dao = null;
        try {
            if(daoMap.get(daoName) == null){
                synchronized (this){
                    if(daoMap.get(daoName) == null){
                        String classPath = prop.getProperty(daoName);
                        if(classPath == null) return null;
                        dao = Class.forName(classPath).newInstance();
                        daoMap.put(daoName, dao);
                    }
                }
            }else{
                dao = daoMap.get(daoName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dao;
    }
}
