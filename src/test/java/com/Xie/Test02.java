package com.Xie;

import com.Xie.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Unit test for simple App.
 */
public class Test02
{
    public static void main(String[] args) {

        //获取上下文环境
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring02.xml");
        //获取指定bean
        UserService userService = (UserService) ac.getBean("userService");



        userService.test();
    }
}
