package com.demo.prototype;

import java.io.*;
import java.util.ArrayList;

/**
 * 1、实现Cloneable接口
 */
public class User implements Cloneable, Serializable {
    private String name;
    private String password;
    private ArrayList<String> phones;

    @Override
    public User clone() {
        try {
            User user = (User) super.clone();
            // 设置深复制
            // 重点，如果要连带引用类型一起复制，需要添加底下一条代码，如果不加就对于是复制了引用地址
            user.phones = (ArrayList<String>) this.phones.clone();
            return user;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User serialDeepClone() {
        try {
            ByteArrayOutputStream byteArrayOs = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOs);
            objectOutputStream.writeObject(this);

            ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(byteArrayOs.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayIs);
            return (User) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭资源
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }
}