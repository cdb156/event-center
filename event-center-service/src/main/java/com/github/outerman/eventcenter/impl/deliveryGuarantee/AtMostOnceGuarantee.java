package com.github.outerman.eventcenter.impl.deliveryGuarantee;

import com.github.outerman.eventcenter.constant.Constant;
import com.github.outerman.eventcenter.impl.BeanNames;
import com.github.outerman.eventcenter.impl.mgr.ServiceContainer;
import com.github.outerman.eventcenter.vo.EventInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shenxy on 9/10/17.
 *
 * 默认的消息送达策略 —— at most once
 */
@Component(BeanNames.BEAN_MOST_GUARANTEE)
public class AtMostOnceGuarantee implements IDeliveryGuarantee {

    @Override
    public void preSend(List<Long> eventIdList, List<String> eventJsonList) throws Exception {
        if (ServiceContainer.storage == null) {
            throw new Exception(Constant.NOT_INITED);
        }

        ServiceContainer.storage.changeStatus(eventIdList, EventInfo.STATUS_SENDED);
    }

    @Override
    public void afterSend(List<Long> eventIdList, List<String> eventJsonList) {
        // do nothing
    }
}
