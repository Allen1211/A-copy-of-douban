package com.allen.douban.factory;

import com.allen.douban.util.DbUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TransactionProxyFactory implements MethodInterceptor {

    private Object target;

    public TransactionProxyFactory(Object target){
        this.target = target;
    }

    public Object getProxyInstance(){
        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(this);
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        DbUtil.beginTransaction();
        try {
            returnValue = method.invoke(target,objects);
            DbUtil.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            DbUtil.rollbackTransaction();
        }
        return returnValue;
    }
}
