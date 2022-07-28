package com.Xie.proxy.cglib;

import com.Xie.proxy.JDK.JdkHandler;
import com.Xie.proxy.marry.Marry;
import com.Xie.proxy.marry.You;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/28 11:30
 */
public class CglibTest {
    public static void main(String[] args) {

/*        //得到目标对象
        You you = new You();

        //得到拦截器
        CglibInterceptor cglibInterceptor = new CglibInterceptor(you);

        //得到代理对象
        Marry marry = (Marry) cglibInterceptor.getProxy();

        //通过代理对象调用目标对象的方法
        marry.toMarry();*/


        /*通过cglib动态代理实现：没有接口实现的类*/
        //目标对象
/*        User user = new User();
        CglibInterceptor cglibInterceptor1 = new CglibInterceptor(user);
        User u = (User) cglibInterceptor1.getProxy();
        u.test();*/


        /*通过JDK动态代理实现：没有接口实现的类*/
/*        User user = new User();
        JdkHandler jdkHandler = new JdkHandler(user);
        User u = (User) jdkHandler.getProxy();
        u.test();*/
    }
}
