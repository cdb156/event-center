package com.github.outerman.eventcenter.consumer.mgr;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.github.outerman.eventcenter.annotation.Consumer;
import com.github.outerman.eventcenter.itf.IEventConsumer;
import com.github.outerman.eventcenter.util.EventNameUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by shenxy on 26/9/17.
 *
 * 在spring的初始化过程里, 通过注解, 收集事件中心的订阅者
 */
@Component
public class EventConsumerCollector implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {
    private final static Logger logger = LoggerFactory.getLogger(EventConsumerCollector.class);

    @Autowired
    private EventConsumerMgr eventConsumerMgr;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Consumer consumer = AnnotationUtils.findAnnotation(bean.getClass(), Consumer.class);
//        logger.warn("BeanPostProcessor:" + beanName);
        if (bean instanceof IEventConsumer && consumer != null) {
            Type[] types = bean.getClass().getGenericInterfaces();

            if (types == null || types.length == 0) {
                return bean;
            }

            //获取泛型类型((ParameterizedType)bean.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
            for (Type type : types) {
                if (type.getTypeName().contains(IEventConsumer.class.getName())) {
                    Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
                    if (genericTypes == null || genericTypes.length == 0) {
                        continue;
                    }

                    eventConsumerMgr.addConsumer(EventNameUtil.toEventName(consumer.eventType(), (Class) genericTypes[0]), (IEventConsumer) bean);

                    break;
                }
            }
        }

        return bean;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
//
//            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
//                logger.warn("onApplicationEvent root:" + eventCenterService);
//                //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
//                Map<Class, List<IEventConsumer>> eventConsumerMap = getEventConsumerMap();
//
//                if (eventConsumerMap == null || eventConsumerMap.size() == 0) {
//                    return;
//                }
//
//                eventConsumerMap.forEach((clz, consumerList) -> eventCenterService.registerConsumer(clz, consumerList));
//            }, 1000, TimeUnit.MILLISECONDS);
//
//        }
    }

}
