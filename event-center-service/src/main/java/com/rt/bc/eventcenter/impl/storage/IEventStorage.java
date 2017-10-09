package com.rt.bc.eventcenter.impl.storage;

import java.util.List;

/**
 * Created by shenxy on 30/9/17.
 *
 * 消息存储的服务接口声明
 */
public interface IEventStorage {
    EventInfo save(String eventName, String eventJson);

    boolean changeStatus(List<Long> eventIdList, EventInfo.EventStatus status);
}
