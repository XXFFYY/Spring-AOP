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

### 3.1 什么是AOP?

​		 Aspect Oriented Programing 面向切面编程，相比较 oop 面向对象编程来说，Aop关注的不再是程序代码中某个类，某些方法，而aop考虑的更多的是一种面到面的切入，即层与层之间的一种切入，所以称之为切面。

------------

### 3.2 AOP能做什么？

​		 AOP主要应用于日志记录，性能统计，安全控制,事务处理等方面，实现公共功能性的重复使用。

-------------

### 3.3 AOP的特点

1. 降低模块与模块之间的耦合度，提高业务代码的聚合度。（高内聚低耦合）

2. 提高了代码的复用性。

3. 提高系统的扩展性。（高版本兼容低版本）

4. 可以在不影响原有的功能基础上添加新的功能

#### 3.3.1 AOP的底层实现

​		　动态代理（JDK + CGLIB）

-------------------------

### 3.4 AOP基本概念

#### 3.4.1 Joinpoint（连接点）

​		 被拦截到的每个点，spring中指被拦截到的每一个方法，spring aop一个连接点即代表一个方法的执行。

#### 3.4.2 Pointcut（切入点）

​		 对连接点进行拦截的定义（匹配规则定义 规定拦截哪些方法，对哪些方法进行处理），spring 有专门的表达式语言定义。

#### 3.4.3 Advice（通知）

​		 拦截到每一个连接点即（每一个方法）后所要做的操作

#### 3.4.4  Aspect（切面）

​		 切入点与通知的结合，决定了切面的定义，切入点定义了要拦截哪些类的哪些方法，通知则定义了拦截过方法后要做什么，切面则是横切关注点的抽象，与类相似，类是对物体特征的抽象，切面则是横切关注点抽象。

#### 3.4.5 Target（目标对象）

​		 被代理的目标对象

#### 3.4.6 Weave（织入）

​		 将切面应用到目标对象并生成代理对象的这个过程即为织入

#### 3.4.7 Introduction（引入）

​		 在不修改原有应用程序代码的情况下，在程序运行期为类动态添加方法或者字段的过程称为引入

--------------

## 4. Spring AOP的实现

### 4.1 Spring AOP环境搭建

#### 4.1.1坐标依赖引入

```xml
<!--Spring AOP-->
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjweaver</artifactId>
  <version>1.9.9.1</version>
  <scope>runtime</scope>
</dependency>
```

#### 4.1.2 添加Spring.xml配置

添加命名空间

```xml
xmlns:aop="http://www.springframework.org/schema/aop"
```

```xml
http://www.springframework.org/schema/aop
http://www.springframework.org/schema/aop/spring-aop.xsd
```

-------------

### 4.2 注解实现

#### 4.2.1 定义切面

```java
/**
 * 切面
 *      切入点和通知的抽象
 *      定义 切入点 和 通知
 *          切入点：定义要拦截哪些类的哪些方法
 *          通知：定义拦截之后方法要做什么
 */
@Component  //将对象交给IOC容器进行实现
@Aspect //声明当前类是一个切面
public class LogCut {

    /**
     * 切入点
     *      定义要拦截哪些类的哪些方法
     *      匹配规则，拦截什么方法
     *
     *      定义切入点
     *          @Pointcut("匹配规则")
     *      AOP切入点表达式
     *          1.执行所有的公共方法
     *              execution(public *(..))
     *          2.执行任意的set方法
     *              execution(* set*.(..))
     *          3.设置指定包下的任意类的任意方法 (指定包：com.Xie.service)
     *              execution(* com.Xie.service.*.*(..))
     *          4.设置指定包以及子包下任意类的任意方法(指定包：com.Xie.service)
     *              execution(* com.Xie.service..*.*(..))
     *          表达式中的第一个*
     *              代表的是方法的修饰范围 （public、private、protected）
     *              如果取值是*，则表示所有范围
     */
    @Pointcut("execution(* com.Xie.service..*.*(..))")
    public void cut(){

    }

    /**
     * 声明前置通知，并将通知应用到指定的切入点上
     *      目标类的方法执行前，执行该通知
     */
    @Before(value = "cut()")
    public void before(){
        System.out.println("前置通知...");
    }

    /**
     * 声明返回通知，并将通知应用到指定的切入点上
     *      目标类的方法在无异常执行后，执行该通知
     */
    @AfterReturning(value = "cut()")
    public void afterReturn(){
        System.out.println("返回通知...");
    }

    /**
     * 声明最终通知，并将通知应用到指定的切入点上
     *      目标类的方法执行后，执行该通知（有无异常都会执行）
     */
    @After(value = "cut()")
    public void after(){
        System.out.println("最终通知...");
    }

    /**
     * 声明异常通知，并将通知应用到指定的切入点上
     *      目标类的方法执行异常时，执行该通知
     */
    @AfterThrowing(value = "cut()")
    public void afterThrow(){
        System.out.println("异常通知...");
    }

    /**
     * 声明环绕通知，并将通知应用到指定的切入点上
     *      目标类的方法执行前后，都可通过环绕通知定义相应的处理
     *          需要通过显式调用的方法，否则无法访问指定方法 pjp.proceed();
     */
    @Around(value = "cut()")
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
```

#### 4.2.2 配置文件(Spring.xml)

```xml
<!--配置AOP代理-->
<aop:aspectj-autoproxy/>
```

----------

### 4.3 XML实现

#### 4.3.1 定义切面

```java
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
```

#### 4.3.2 配置文件(spring.xml)

```xml
<!-- AOP相关配置-->
<aop:config>
    <!-- AOP切面-->
    <aop:aspect ref="logCut02">
        <!-- 定义aop切入点 -->
        <aop:pointcut id="cut" expression="execution(* com.Xie.service.*.*(..))"/>
        <!-- 配置前置通知:设置前置通知对应的方法名及切入点 -->
        <aop:before method="before" pointcut-ref="cut"/>
        <!-- 配置返回通知:设置返回通知对应的方法名及切入点 -->
        <aop:after-returning method="afterReturn" pointcut-ref="cut"/>
        <!-- 配置最终通知:设置最终通知对应的方法名及切入点 -->
        <aop:after method="after" pointcut-ref="cut"/>
        <!-- 配置异常通知:设置异常通知对应的方法名及切入点 -->
        <aop:after-throwing method="afterThrow" pointcut-ref="cut"/>
        <!-- 配置环绕通知:设置环绕通知对应的方法名及切入点 -->
        <aop:around method="around" pointcut-ref="cut"/>
    </aop:aspect>
</aop:config>
```
