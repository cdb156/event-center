package com.rt.bc.eventcenter.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.rt.bc.eventcenter.itf.IEventConsumerBus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shenxy on 28/9/17.
 *
 * 事件服务中心, 向消费者通用服务接口发送消息的服务实现
 */
@Component
public class EventConsumer implements IEventConsumerBus {
    //广播的方式, 给所有服务都发送
    @Reference(cluster = "broadcast")
    private IEventConsumerBus eventConsumerBus;

    @Override
    public void onBusEvent(String eventName, List<String> eventJsonList) {
        eventConsumerBus.onBusEvent(eventName, eventJsonList);
    }
}
