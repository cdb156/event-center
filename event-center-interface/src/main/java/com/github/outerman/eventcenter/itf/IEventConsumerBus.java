package com.github.outerman.eventcenter.itf;

import java.util.List;

/**
 * Created by shenxy on 28/9/17.
 *
 * 连接事件中心的消费服务总线, 接收通用事件, 以备分发给当前服务中的不同消费者
 */
public interface IEventConsumerBus {
    void onBusEvent(String eventName, List<String> eventJsonList);
}
