package com.Xie.proxy.rent;

/**
 * @Description: 静态代理->目标对象
 *                  实现行为
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/27 17:12
 */
public class Owner implements RentHouse{

    @Override
    public void toRentHouse() {
        System.out.println("两室一厅，月租5000！");
    }
}
