package com.rt.bc.eventcenter.impl.storage;

/**
 * Created by shenxy on 30/9/17.
 *
 * 消息存储的服务接口声明
 */
public interface IEventStorage {
    EventInfo save(String eventName, String eventJson);

    boolean saveEventStatus(EventInfo.EventStatus status);
}
