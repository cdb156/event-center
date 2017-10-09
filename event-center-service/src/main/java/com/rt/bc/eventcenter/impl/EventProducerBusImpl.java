package com.rt.bc.eventcenter.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.rt.bc.eventcenter.impl.broker.IImmediateCenterService;
import com.rt.bc.eventcenter.impl.broker.IPatchCenterService;
import com.rt.bc.eventcenter.itf.IEventProducerBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shenxy on 28/9/17.
 *
 * 事件服务中心, 接收事件的通用数据
 */
@Component
@Service
public class EventProducerBusImpl implements IEventProducerBus {
    @Autowired
    private IPatchCenterService patchCenterService;

    @Autowired
    private IImmediateCenterService immediateCenterService;

    @Override
    public void postEvent(String eventName, String eventJson) {
        patchCenterService.postEvent(eventName, eventJson);
    }

    @Override
    public void postEventSync(String eventName, String eventJson) {
        immediateCenterService.postEvent(eventName, eventJson);
    }
}
