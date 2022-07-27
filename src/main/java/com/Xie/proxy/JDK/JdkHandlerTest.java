package com.Xie.proxy.JDK;

import com.Xie.proxy.marry.Marry;
import com.Xie.proxy.marry.You;
import com.Xie.proxy.rent.Owner;
import com.Xie.proxy.rent.RentHouse;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/27 18:07
 */
public class JdkHandlerTest {
    public static void main(String[] args) {

        //目标对象
        You you = new You();
        //得到代理类
        JdkHandler jdkHandler = new JdkHandler(you);
        //得到代理对象
        Marry marry = (Marry) jdkHandler.getProxy();
        //通过代理对象调用目标对象的方法
        marry.toMarry();


        //目标对象
        Owner owner = new Owner();
        //得到代理类
        JdkHandler jdkHandler1 = new JdkHandler(owner);
        //得到代理对象
        RentHouse rentHouse = (RentHouse) jdkHandler1.getProxy();
        //通过代理对象调用目标对象的方法
        rentHouse.toRentHouse();

    }
}
