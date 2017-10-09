package com.rt.bc.eventcenter.impl.deliveryGuarantee;

import com.rt.bc.eventcenter.impl.storage.EventInfo;
import com.rt.bc.eventcenter.impl.storage.IEventStorage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Created by shenxy on 9/10/17.
 *
 * 默认的消息送达策略 —— at most once
 */
public class AtMostOnceGuarantee implements IDeliveryGuarantee {
    @Autowired
    private IEventStorage eventStorage;

    @Override
    public void preSend(List<Long> eventIdList, List<String> eventJsonList) {
        eventStorage.saveEventStatus(eventIdList, EventInfo.EventStatus.sended);
    }

    @Override
    public void afterSend(List<Long> eventIdList, List<String> eventJsonList) {
        // do nothing
    }
}
