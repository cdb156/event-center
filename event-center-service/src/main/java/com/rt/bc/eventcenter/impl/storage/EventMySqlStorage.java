package com.rt.bc.eventcenter.impl.storage;

import java.util.List;

/**
 * Created by shenxy on 30/9/17.
 *
 * 使用mysql存储事件, 做持久化
 */
public class EventMySqlStorage implements IEventStorage {
    @Override
    public EventInfo save(String eventName, String eventJson) {
        return null;
    }

    @Override
    public boolean changeStatus(List<Long> eventIdList, EventInfo.EventStatus status) {
        return false;
    }
}
