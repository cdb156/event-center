package com.rt.bc.eventcenter.itf;

import java.io.Serializable;

/**
 * Created by shenxy on 26/9/17.
 *
 * 连接事件服务中心的事件生产者总线, 接收不同事件, 转化成通用事件数据结构
 */
public interface IEventProducerBus {
    void postEvent(String eventName, String eventJson);
}
