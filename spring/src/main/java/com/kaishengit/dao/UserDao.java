package com.kaishengit.dao;

public class UserDao {

    public UserDao(){
        System.out.println("我是构造方法...");
    }

    public void  init(){
        System.out.println("init...");
    }

    public void  save(){
        System.out.println("Hello,spring...");
    }

   public void  destory(){
        System.out.println("容器销毁"+"destory...");
    }

}
