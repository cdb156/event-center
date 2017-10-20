package com.github.outerman.eventcenter.itf;

import java.util.List;

/**
 * Created by shenxy on 2015-5-4.
 *
 * 事件消费者的接口
 */
public interface IEventConsumer<T> {
    void onEvent(List<T> eventDtoList);
}
