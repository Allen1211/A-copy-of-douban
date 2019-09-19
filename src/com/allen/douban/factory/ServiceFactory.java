package com.allen.douban.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceFactory {
    private static ServiceFactory ServiceFactroy;
    private Properties prop = new Properties();
    private static final ServiceFactory instance = new ServiceFactory();
    private static final Map<String,Object> serviceMap = new HashMap<>();
    private ServiceFactory(){
        InputStream in = null;
        try {
            in = DaoFactroy.class.getClassLoader().getResourceAsStream("service.properties");
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

    public static ServiceFactory getInstance(){
        return instance;
    }

    public Object getService(String serviceName){
        Object service = null;
        try {
            if(serviceMap.get(serviceName) == null){
                synchronized (this){
                    if(serviceMap.get(serviceName) == null){
                        String classPath = prop.getProperty(serviceName);
                        if(classPath == null) return null;
                        service = Class.forName(classPath).newInstance();
                        serviceMap.put(serviceName, service);
                    }
                }
            }else{
                service = serviceMap.get(serviceName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }

}
