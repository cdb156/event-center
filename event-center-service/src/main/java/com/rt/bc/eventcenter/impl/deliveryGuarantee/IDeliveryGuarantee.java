package com.rt.bc.eventcenter.impl.deliveryGuarantee;

import com.rt.bc.eventcenter.impl.storage.IEventStorage;

import java.util.List;

/**
 * Created by shenxy on 30/9/17.
 *
 * 送达策略服务, 接口声明
 */
public interface IDeliveryGuarantee {
    void preSend(Long eventId, IEventStorage eventStorage);

    void afterSend(Long eventId, IEventStorage eventStorage);

    void preSend(String eventType, List<String> eventJsonList);

    void afterSend(String eventType, List<String> eventJsonList);
}
