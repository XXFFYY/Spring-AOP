# Spring-AOP
Spring AOP第一次学习

## 1.静态代理

​	某个对象提供一个代理，代理角色固定，以控制对这个对象的访问。 代理类和委托类有共同的父类或

父接口，这样在任何使用委托类对象的地方都可以用代理对象替代。代理类负责请求的预处理、过滤、

将请求分派给委托类处理、以及委托类执行完请求后的后续处理。

### 1.1 代理的三要素

1. 有共同的行为（结婚）-定义接口

2. 目标角色/真实角色（新人）-实现接口

3. 代理角色（婚庆公司）-实现接口 增强用户行为

### 1.2 静态代理的特点

1. 目标角色固定

2. 在应用程序之前就得知目标角色

3. 代理对象会增强目标对象的行为

4. 有可能存在多个代理，产生“类爆炸”（缺点）

​	

## 2.动态代理

+ 可以根据需要，通过反射机制在程序运行期，动态的为目标对象创建代理对象。

+ 动态代理的两种实现方式：
  1. JDK动态代理
  2. CGLIB动态代理

### 2.1动态代理的特点

1. 目标对象不固定
2. 在程序运行时动态创建目标对象
3. 代理对象会增强目标对象的行为

### 2.2 JDK动态代理

​	**注：JDK动态代理的目标对象必须有接口实现**

#### 2.2.1 newProxyInstance

+ 动态代理类：

```java
public class JdkHandler implements InvocationHandler {

    //目标对象
    private Object target;//目标对象的类型不固定，创建时动态生成

    //通过带参构造器传递目标对象

    public JdkHandler(Object target) {
        this.target = target;
    }

    /**
     * 1.调用目标对象的方法（返回Object）
     * 2.增强目标对象的行为
     * @param proxy 调用该方法的代理实例
     * @param method 目标对象的方法
     * @param args 目标对象的方法所需要的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //用户的增强行为
        System.out.println("============方法执行前");

        //调用目标对象中的方法（返回Object)
        Object object = method.invoke(target, args);

        //用户的增强行为
        System.out.println("方法执行后============");
        return object;
    }

    /**
     * 获取代理对象
     *  public static Object newProxyInstance(ClassLoader loader,
     *
     *                     Class<?>[] interfaces,
     *
     *                     InvocationHandler h)
     * loader:类加载器
     * interfaces: 接口数组
     *      target.getClass().getInterfaces():目标对象的接口数组
     * h:InvocationHandler接口（传入InvocationHandler接口的实现类）
     * @return
     */
    public Object getProxy(){

        Object object = Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);

        return object;
    }
}
```

+ 测试方法：

```java
//目标对象
You you = new You();
//得到代理类
JdkHandler jdkHandler = new JdkHandler(you);
//得到代理对象
Marry marry = (Marry) jdkHandler.getProxy();
//通过代理对象调用目标对象的方法
marry.toMarry();
```

----------------------------------

### 2.3 CGLIB动态代理

​		 JDK的动态代理机制只能代理实现了接口的类，而不能实现接口的类就不能使用JDK的动态代理，cglib是针对类来实现代理的，它的原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，但因为采用的是继承，所以不能对final修饰的类进行代理。

​		 实现原理：**继承思想**

​		 代理类继承目标类，重写目标类中的方法。

-------

#### 2.3.1 添加依赖

```xml
<!-- https://mvnrepository.com/artifact/cglib/cglib -->
<dependency>
  <groupId>cglib</groupId>
  <artifactId>cglib</artifactId>
  <version>3.3.0</version>
</dependency>
```

------------

#### 2.3.2 定义类

​	实现MethodInterceptor接口

```java
public class CglibInterceptor implements MethodInterceptor {
//目标对象
private Object target;

//通过构造器传入目标对象
public CglibInterceptor(Object target) {
    this.target = target;
}

/**
 * 获取代理对象
 * @return
 */
public Object getProxy(){

    //通过Enhancer对象中的create()方法生成一个类，用于生成代理对象
    Enhancer enhancer = new Enhancer();

    //设置父类（将目标类作为代理类的父类）
    enhancer.setSuperclass(target.getClass());

    //设置拦截器 回调对象为本身对象
    enhancer.setCallback(this);

    //生成代理类对象并返回给调用者
    return enhancer.create();

}

/**
 * 拦截器
 *  1.目标对象的方法调用
 *  2.行为增强
 * @param o cglib动态生成的代理类的实例
 * @param method    实体类所调用的被代理的方法的引用
 * @param objects   参数列表
 * @param methodProxy   生成的代理类对方法的代理引用
 * @return
 * @throws Throwable
 */
@Override
public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

    //增强行为
    System.out.println("=================方法前执行");

    //调用目标类中的方法
    Object object = methodProxy.invoke(target, objects);

    //增强行为
    System.out.println("方法后执行=================");

    return object;
}
}
```
---------

#### 2.3.3 测试方法

```java
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
        User user = new User();
        CglibInterceptor cglibInterceptor1 = new CglibInterceptor(user);
        User u = (User) cglibInterceptor1.getProxy();
        u.test();
```

----------

#### 2.3.4 JDK代理与CGLIB代理的区别

+ JDK动态代理实现接口，Cglib动态代理继承思想

+ JDK动态代理（目标对象存在接口时）执行效率高于Ciglib

+ 如果目标对象有接口实现，选择JDK代理，如果没有接口实现选择Cglib代理

---------

## 3.Spring AOP
