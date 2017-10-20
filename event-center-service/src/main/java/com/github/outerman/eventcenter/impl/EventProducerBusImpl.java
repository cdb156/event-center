package com.github.outerman.eventcenter.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.outerman.eventcenter.constant.Constant;
import com.github.outerman.eventcenter.impl.broker.IImmediateBrokerService;
import com.github.outerman.eventcenter.impl.mgr.EventCenterManager;
import com.github.outerman.eventcenter.impl.mgr.ServiceContainer;
import com.github.outerman.eventcenter.itf.IEventProducerBus;
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
    private IImmediateBrokerService immediateCenterService;

    @Override
    public void postEvent(String eventName, String eventJson) throws Exception {
        if (!EventCenterManager.isInited() || ServiceContainer.defaultBrokerService == null) {
            throw new Exception(Constant.NOT_INITED);
        }

        //该方法,根据初始化的配置来发送
        ServiceContainer.defaultBrokerService.postEvent(eventName, eventJson);
    }

    @Override
    public void postEventSync(String eventName, String eventJson) throws Exception {
        //该方法,强制使用"即时上报"的方式来发送
        immediateCenterService.postEvent(eventName, eventJson);
    }
}
