package com.github.outerman.eventcenter.impl.deliveryGuarantee;

import java.util.List;

/**
 * Created by shenxy on 30/9/17.
 *
 * 送达策略服务, 接口声明
 */
public interface IDeliveryGuarantee {
    void preSend(List<Long> eventIdList, List<String> eventJsonList) throws Exception;

    void afterSend(List<Long> eventIdList, List<String> eventJsonList) throws Exception;
}
