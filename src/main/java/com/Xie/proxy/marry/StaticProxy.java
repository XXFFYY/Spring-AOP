package com.Xie.proxy.marry;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/27 17:06
 */
public class StaticProxy {




    public static void main(String[] args) {
        //目标对象
        You you = new You();

        //代理对象
        MarryCompanyProxy marryCompanyProxy = new MarryCompanyProxy(you);

        //通过代理对象调用目标对象中的方法
        marryCompanyProxy.toMarry();
    }
}
