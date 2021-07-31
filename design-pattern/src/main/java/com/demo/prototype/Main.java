package com.demo.prototype;

import java.util.ArrayList;

/**
 * 原型模式
 * 1.什么是原型模式
 *
 *     原型设计模式简单来说就是克隆
 *     原型表明了有一个样板实例，这个原型是可定制的。原型模式多用于创建复杂的或者构造耗时的实例，因为这种情况下，复制一个已经存在的实例可使程序运行更高效。
 *
 * 2.原型模式的应用场景
 *
 *     类初始化需要消化非常多的资源，这个资源包括数据、硬件资源等。这时我们就可以通过原型拷贝避免这些消耗。
 *     通过new产生的一个对象需要非常繁琐的数据准备或者权限，这时可以使用原型模式。
 *     一个对象需要提供给其他对象访问，而且各个调用者可能都需要修改其值时，可以考虑使用原型模式拷贝多个对象供调用者使用，即保护性拷贝。
 *
 * 我们Spring框架中的多例就是使用原型。
 *
 * 3.原型模式的使用方式
 *
 *     实现Cloneable接口。在java语言有一个Cloneable接口，它的作用只有一个，就是在运行时通知虚拟机可以安全地在实现了此接口的类上使用clone方法。在java虚拟机中，只有实现了这个接口的类才可以被拷贝，否则在运行时会抛出CloneNotSupportedException异常。
 *     重写Object类中的clone方法。Java中，所有类的父类都是Object类，Object类中有一个clone方法，作用是返回对象的一个拷贝，但是其作用域protected类型的，一般的类无法调用，因此Prototype类需要将clone方法的作用域修改为public类型。
 *
 * 3.1原型模式分为浅复制和深复制
 *
 *     （浅复制）只是拷贝了基本类型的数据，而引用类型数据，只是拷贝了一份引用地址。
 *     （深复制）在计算机中开辟了一块新的内存地址用于存放复制的对象。
 */
public class Main {
      public static void main(String[] args) {
          //创建User原型对象
          User user = new User();
          user.setName("李三");
          user.setPassword("123456");
          ArrayList<String> phones = new ArrayList<>();
          phones.add("17674553302");
          user.setPhones(phones);
  
          //copy一个user对象,并且对象的属性
          User user2 = user.clone();
          user2.setPassword("654321");
  
          //查看俩个对象是否是一个
          System.out.println(user == user2);
  
          //查看属性内容
          System.out.println(user.getName() + " | " + user2.getName());
          System.out.println(user.getPassword() + " | " + user2.getPassword());
          //查看对于引用类型拷贝
          System.out.println(user.getPhones() == user2.getPhones());
      }
  }