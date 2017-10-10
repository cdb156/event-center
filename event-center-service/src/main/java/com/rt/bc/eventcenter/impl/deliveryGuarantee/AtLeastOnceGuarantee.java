package com.rt.bc.eventcenter.impl.deliveryGuarantee;

import com.rt.bc.eventcenter.constant.Constant;
import com.rt.bc.eventcenter.impl.mgr.EventCenterManager;
import com.rt.bc.eventcenter.impl.mgr.ServiceContainer;
import com.rt.bc.eventcenter.vo.EventInfo;

import java.util.List;

/**
 * Created by shenxy on 9/10/17.
 *
 * 消息送达策略 —— at least once
 */
public class AtLeastOnceGuarantee implements IDeliveryGuarantee {

    @Override
    public void preSend(List<Long> eventIdList, List<String> eventJsonList) {
        // do nothing
    }

    @Override
    public void afterSend(List<Long> eventIdList, List<String> eventJsonList) throws Exception {
        if (ServiceContainer.storage == null) {
            throw new Exception(Constant.NOT_INITED);
        }

        ServiceContainer.storage.changeStatus(eventIdList, EventInfo.STATUS_SENDED);
    }
}
