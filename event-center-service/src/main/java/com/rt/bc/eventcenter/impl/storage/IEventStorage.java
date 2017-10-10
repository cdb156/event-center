package com.rt.bc.eventcenter.impl.storage;

import com.rt.bc.eventcenter.vo.EventInfo;

import java.util.List;

/**
 * Created by shenxy on 30/9/17.
 *
 * 消息存储的服务接口声明
 */
public interface IEventStorage {
    EventInfo save(String eventName, String eventJson);

    boolean changeStatus(List<Long> eventIdList, Integer status);

//    EventInfo poll();

    List<EventInfo> getNotSend();
}
