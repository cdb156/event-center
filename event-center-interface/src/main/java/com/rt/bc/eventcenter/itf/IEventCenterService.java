package com.rt.bc.eventcenter.itf;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shenxy on 26/9/17.
 *
 * 事件中心的服务接口, 用于注册服务
 */
public interface IEventCenterService {
    void registerConsumer(Class cls , IEventConsumer eventProcessor);

    void registerConsumer(Class cls , List<IEventConsumer> eventProcessor);
}
