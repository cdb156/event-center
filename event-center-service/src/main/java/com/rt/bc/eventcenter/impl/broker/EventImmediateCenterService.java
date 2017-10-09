package com.rt.bc.eventcenter.impl.broker;

import com.rt.bc.eventcenter.impl.EventConsumer;
import com.rt.bc.eventcenter.impl.deliveryGuarantee.IDeliveryGuarantee;
import com.rt.bc.eventcenter.impl.storage.EventInfo;
import com.rt.bc.eventcenter.impl.storage.IEventStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by shenxy on 30/9/17.
 *
 * 用于实时处理事件的处理中心
 */
@Component
public class EventImmediateCenterService implements IImmediateCenterService {
    @Autowired
    private IEventStorage eventStorage;

    @Autowired
    private EventConsumer eventConsumer;

    @Autowired
    private IDeliveryGuarantee deliveryGuarantee;

    @Override
    public void postEvent(String eventName, String eventJson) {
        //1. 保存消息
        EventInfo eventInfo = eventStorage.save(eventName, eventJson);
        //2. 记录获取消息
        deliveryGuarantee.preSend(Collections.singletonList(eventInfo.getId()), Collections.singletonList(eventJson));
        //3. 发送消息
        eventConsumer.onBusEvent(eventName, Collections.singletonList(eventJson));
        //4. 记录发送结果
        deliveryGuarantee.afterSend(Collections.singletonList(eventInfo.getId()), Collections.singletonList(eventJson));
    }
}
