package com.rt.bc.eventcenter.impl.broker;

/**
 * Created by shenxy on 30/9/17.
 *
 * 事件处理服务的接口声明
 */
public interface IPatchCenterService {
    void postEvent(String eventName, String eventJson);
}
