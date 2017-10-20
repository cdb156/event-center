package com.github.outerman.eventcenter.impl.broker;

import com.github.outerman.eventcenter.constant.Constant;
import com.github.outerman.eventcenter.impl.BeanNames;
import com.github.outerman.eventcenter.impl.EventConsumer;
import com.github.outerman.eventcenter.impl.mgr.EventCenterManager;
import com.github.outerman.eventcenter.impl.mgr.ServiceContainer;
import com.github.outerman.eventcenter.vo.EventInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by shenxy on 30/9/17.
 *
 * 用于实时处理事件的处理中心
 */
@Component(BeanNames.BEAN_IMMEDIATE_BROKER)
public class EventImmediateBrokerService implements IImmediateBrokerService {

    @Autowired
    private EventConsumer eventConsumer;

    @Override
    public void postEvent(String eventName, String eventJson) throws Exception {
        if (!EventCenterManager.isInited() || ServiceContainer.storage == null || ServiceContainer.guarantee == null) {
            throw new Exception(Constant.NOT_INITED);
        }

        //1. 保存消息
        EventInfo eventInfo = ServiceContainer.storage.save(eventName, eventJson);
        //2. 记录获取消息
        ServiceContainer.guarantee.preSend(Collections.singletonList(eventInfo.getId()), Collections.singletonList(eventJson));
        //3. 发送消息
        eventConsumer.onBusEvent(eventName, Collections.singletonList(eventJson));
        //4. 记录发送结果
        ServiceContainer.guarantee.afterSend(Collections.singletonList(eventInfo.getId()), Collections.singletonList(eventJson));
    }
}
