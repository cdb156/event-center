# 一、项目介绍
## 1.项目目标
一个基于Spring + Dubbo + Mybatis + Maven的简单的消息服务模块。
如果自己的项目整体框架也是使用上面的基础框架, 又没有太复杂的需求, 可以使用这个模块, 嵌入到自己的项目, 简化整体结构

## 2.项目特点
1、支持分布式，跨进程service的消息机制（基于Dubbo的rpc能力和广播能力）
2、使用简单，接收端@Consumer注解即可，发送端@Autowired一个Producer即可
3、多种消息存储模式：内存队列 + Mysql持久化(不丢失)
4、多种上报模式：即时上报、定时批量上报
5、多种上报策略：最多一次、最少一次
6、适应各种场景：服务进程内、跨服务进程消息、其他进程发送的不认识的消息（代码未引用）

## 3.后续待完善:
### 0) 组件本身,去掉Spring的依赖(包括task配置)

### 1) 多种线程调度方式：
1）线程池调度模式: io模式, compute模式
2）当前线程回调

### 2) 多种策略的灵活组合
1）从@Customer注解来灵活定义每一个消息的策略和模式

### 3) 各种扩展
1) 用户可自定义扩展存储\上报模式\上报策略等

## 4.使用场景——异步计算（可能跨模块）：
1）用户习惯收集（非实时，可丢失）：
2）用户数据收集（非实时，可丢失）：
3）跨模块的数据同步
4）报表数据的"预计算"

# 二、使用方法:
1) 消息接收
```
@Component
@Consumer(eventType = "A")
public class DemoEventConsumerA implements IEventConsumer<DemoEvent>, Serializable {
    @Override
    public void onEvent(List<DemoEvent> eventDtoList) {
        System.out.println("DemoEventConsumerA--onEvent!");
    }
}
```

2) 消息发送
```
@Component
@Service
public class DemoServiceImpl implements IDemoService {
    @Autowired
    private IEventProducer eventProducer;

    @Override
    public String hi() {
        eventProducer.postEvent("", new DemoEvent("aa", 11));

        return "**hello**";
    }
}

```

3) 补充相关配置
Dubbo配置, 增加配置项:
```
<!--2.作为事件组件的分发服务中心,需要增加的配置-->
<dubbo:annotation package="com.github.outerman.eventcenter.impl" service-group="${dubbo.service.group}"  reference-group="${dubbo.reference.group}" />
<context:component-scan base-package="com.github.outerman.eventcenter.impl"/>


<!--3.使用事件组件(发送和接收事件),需要增加的配置-->
<dubbo:annotation package="com.github.outerman.eventcenter.consumer" service-group="${dubbo.service.group}"  reference-group="${dubbo.reference.group}" />
<context:component-scan base-package="com.github.outerman.eventcenter.consumer"/>
<!--这个貌似是个坑, broadcast的模式, 通过注解方式貌似无效, 使用这种配置方式才有效...-->
<dubbo:service cluster="broadcast" interface="com.github.outerman.eventcenter.itf.IEventConsumerBus" ref="EventConsumerBusImpl"/>
```

Mybatis配置, 增加配置项:
```
<!-- DAO接口所在包名，Mapper会自动查找其下的类并注入默认的mapper基类 -->
<bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="com.github.outerman.eventcenter.dao" />
</bean>
```

Maven配置, 增加配置项(拷贝mapping.xml):
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <executions>
        <execution>
            <id>unpack</id>
            <phase>generate-resources</phase>
            <goals>
                <goal>unpack</goal>
            </goals>
            <configuration>
                <artifactItems>
                    <artifactItem>
                        <groupId>com.github.outerman</groupId>
                        <artifactId>event-center-service</artifactId>
                        <version>${project.version}</version>
                        <type>jar</type>
                        <overWrite>false</overWrite>
                        <includes>mapping/*.*</includes>
                        <outputDirectory>./target/classes</outputDirectory>
                    </artifactItem>
                </artifactItems>
            </configuration>
        </execution>
    </executions>
</plugin>
```

Spring的定时任务配置:
```
```

