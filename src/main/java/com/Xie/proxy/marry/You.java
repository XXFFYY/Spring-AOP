package com.Xie.proxy.marry;

/**
 * @Description: 静态代理->目标角色(真实角色) 实现行为
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/27 16:52
 */
public class You implements Marry{

    @Override
    public void toMarry() {
        System.out.println("Marry...");
    }
}
