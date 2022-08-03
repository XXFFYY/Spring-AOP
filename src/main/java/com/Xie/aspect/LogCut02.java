package com.Xie.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/28 17:24
 */

/**
 * 切面
 *      切入点和通知的抽象
 *      定义 切入点 和 通知
 *          切入点：定义要拦截哪些类的哪些方法
 *          通知：定义拦截之后方法要做什么
 */
@Component  //将对象交给IOC容器进行实现
public class LogCut02 {

    public void cut(){

    }

    /**
     * 声明前置通知，并将通知应用到指定的切入点上
     *      目标类的方法执行前，执行该通知
     */

    public void before(){
        System.out.println("前置通知...");
    }

    /**
     * 声明返回通知，并将通知应用到指定的切入点上
     *      目标类的方法在无异常执行后，执行该通知
     */

    public void afterReturn(){
        System.out.println("返回通知...");
    }

    /**
     * 声明最终通知，并将通知应用到指定的切入点上
     *      目标类的方法执行后，执行该通知（有无异常都会执行）
     */

    public void after(){
        System.out.println("最终通知...");
    }

    /**
     * 声明异常通知，并将通知应用到指定的切入点上
     *      目标类的方法执行异常时，执行该通知
     */

    public void afterThrow(){
        System.out.println("异常通知...");
    }

    /**
     * 声明环绕通知，并将通知应用到指定的切入点上
     *      目标类的方法执行前后，都可通过环绕通知定义相应的处理
     *          需要通过显式调用的方法，否则无法访问指定方法 pjp.proceed();
     */

    public Object around(ProceedingJoinPoint pjp){
        System.out.println("环绕通知-前置通知...");

        Object object = null;

        try {
            //显式调用对应的方法
            object = pjp.proceed();
            System.out.println(pjp.getTarget());
            System.out.println("环绕通知-返回通知...");
        }catch (Throwable throwable){
            throwable.printStackTrace();
            System.out.println("环绕通知-异常通知...");
        }
        System.out.println("环绕通知-最终通知...");
        return object;
    }
}
