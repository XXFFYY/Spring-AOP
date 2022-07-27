package com.Xie.proxy.rent;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/27 17:17
 */
public class StaticProxy {
    public static void main(String[] args) {
        //目标对象
        Owner owner = new Owner();

        //代理对象
        AgentProxy agentProxy = new AgentProxy(owner);

        //通过代理对象调用目标对象中的方法

        agentProxy.toRentHouse();
    }
}
