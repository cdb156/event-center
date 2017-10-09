package com.rt.bc.eventcenter.impl.deliveryGuarantee;

import com.rt.bc.eventcenter.impl.storage.IEventStorage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by shenxy on 9/10/17.
 *
 * 默认的消息送达策略 —— at most once
 */
public class DefaultDeliveryGuarantee implements IDeliveryGuarantee {

    @Override
    public void preSend(Long eventId, IEventStorage eventStorage) {

    }

    @Override
    public void afterSend(Long eventId, IEventStorage eventStorage) {

    }

    @Override
    public void preSend(String eventType, List<String> eventJsonList) {

    }

    @Override
    public void afterSend(String eventType, List<String> eventJsonList) {

    }
}
