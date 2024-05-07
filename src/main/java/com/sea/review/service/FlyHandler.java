package com.sea.review.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: jiaqi.cui
 * @date: 2023/6/20
 */
public class FlyHandler implements InvocationHandler {

    private Object target;

    public FlyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke。。。");
//        throw new Exception("asd");
        Object invoke = method.invoke(target, args);
        //在真实的对象执行之后我们可以添加自己的操作
        System.out.println("after invoke。。。");
        return invoke;
    }

    public static void main(String[] args) throws Exception {

        Class birdClazz = Bird.class;
        Bird bird = new Bird();
        Flyable flyable = (Flyable) Proxy.newProxyInstance(birdClazz.getClassLoader(), birdClazz.getInterfaces(),
                new FlyHandler(bird));
        flyable.fly();

    }
}