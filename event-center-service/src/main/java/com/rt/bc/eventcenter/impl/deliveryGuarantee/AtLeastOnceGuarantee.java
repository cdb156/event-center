package com.rt.bc.eventcenter.impl.deliveryGuarantee;

import com.rt.bc.eventcenter.vo.EventInfo;
import com.rt.bc.eventcenter.impl.storage.IEventStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shenxy on 9/10/17.
 *
 * 默认的消息送达策略 —— at most once
 */
//@Component //TODO:
public class AtLeastOnceGuarantee implements IDeliveryGuarantee {
    @Autowired
    private IEventStorage eventStorage;

    @Override
    public void preSend(List<Long> eventIdList, List<String> eventJsonList) {
        // do nothing
    }

    @Override
    public void afterSend(List<Long> eventIdList, List<String> eventJsonList) {
        eventStorage.changeStatus(eventIdList, EventInfo.STATUS_SENDED);
    }
}
