package com.Xie.proxy.marry;

/**
 * @Description: 静态代理->代理角色
 *                  1.实现行为
 *                  2.增强用户行为
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/27 16:54
 */
public class MarryCompanyProxy implements Marry{

    //目标对象
    private Marry target;

    //通过带参构造器传递目标对象
    public MarryCompanyProxy(Marry target) {
        this.target = target;
    }

    //实现行为
    @Override
    public void toMarry() {

        //用户行为增强
        before();

        //调用目标对象中的方法
        target.toMarry();

        //用户行为增强
        after();
    }

    /**
     * 用户行为增强
     */
    private void after() {
        System.out.println("新婚快乐，百年好合");
    }

    /**
     * 用户行为增强
     */
    private void before() {
        System.out.println("婚礼现场正在布置中...");
    }
}
