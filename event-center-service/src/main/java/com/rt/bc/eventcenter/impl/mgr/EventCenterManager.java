package com.rt.bc.eventcenter.impl.mgr;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.rt.bc.eventcenter.impl.broker.EventImmediateCenterService;
import com.rt.bc.eventcenter.impl.broker.EventPatchCenterService;
import com.rt.bc.eventcenter.impl.deliveryGuarantee.AtLeastOnceGuarantee;
import com.rt.bc.eventcenter.impl.deliveryGuarantee.AtMostOnceGuarantee;
import com.rt.bc.eventcenter.impl.storage.EventMysqlStorage;
import com.rt.bc.eventcenter.impl.storage.EventQueueStorage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by shenxy on 10/10/17.
 *
 * 初始化消息中心的配置,并启动服务
 */
@Component
public class EventCenterManager implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(EventCenterManager.class);

    private static EventCenterConfigBuilder configBuilder = new EventCenterConfigBuilder();  //各个配置项

    public static EventCenterConfigBuilder setGuaranteeModel(int guaranteeModel) {
        configBuilder.setGuarantee(guaranteeModel);

        return configBuilder;
    }

    public static EventCenterConfigBuilder setStorageModel(int storageModel) {
        configBuilder.setStorage(storageModel);

        return configBuilder;
    }

    public static EventCenterConfigBuilder setBrokerModel(int brokerModel) {
        configBuilder.setBroker(brokerModel);

        return configBuilder;
    }

    public static boolean isInited() {
        return configBuilder.isInited();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        configBuilder.init();
    }
}
