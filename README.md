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
