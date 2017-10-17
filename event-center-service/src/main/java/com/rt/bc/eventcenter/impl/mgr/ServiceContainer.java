package com.rt.bc.eventcenter.impl.mgr;

import com.rt.bc.eventcenter.impl.broker.IBrokerService;
import com.rt.bc.eventcenter.impl.deliveryGuarantee.IDeliveryGuarantee;
import com.rt.bc.eventcenter.impl.storage.IEventStorage;

/**
 * Created by shenxy on 10/10/17.
 *
 * 根据初始化参数,初始化出来的各个服务类
 */
public class ServiceContainer {
    public static IDeliveryGuarantee guarantee = null;

    public static IEventStorage storage = null;

    public static IBrokerService defaultBrokerService = null;
}
