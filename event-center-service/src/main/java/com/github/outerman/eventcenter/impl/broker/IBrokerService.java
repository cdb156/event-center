package com.github.outerman.eventcenter.impl.broker;

/**
 * Created by shenxy on 30/9/17.
 *
 * 事件处理服务的接口声明
 */
public interface IBrokerService {
    void postEvent(String eventName, String eventJson) throws Exception;
}
