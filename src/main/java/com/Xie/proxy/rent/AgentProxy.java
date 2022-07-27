package com.Xie.proxy.rent;

/**
 * @Description: 静态对象->代理角色
 *                  1.实现行为
 *                  2.增强用户行为
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/27 17:13
 */
public class AgentProxy implements RentHouse{

    //目标对象
    private RentHouse target;

    //通过带参构造器获取目标对象
    public AgentProxy(RentHouse target) {
        this.target = target;
    }

    //实现行为
    @Override
    public void toRentHouse() {

        //用户增强行为
        System.out.println("房型朝南，采光好！");

        //代理对象调用目标对象的方法
        target.toRentHouse();

        //用户增强行为
        System.out.println("价格可议！");
    }
}
