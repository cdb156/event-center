package com.rt.bc.eventcenter.impl.mgr;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shenxy on 10/10/17.
 *
 * 初始化消息中心的配置,并启动服务
 * //TODO: 用户如何方便的使用配置:1)代码配置  2)XML配置
 */
@Component
public class EventCenterManager implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(EventCenterManager.class);

    @Autowired
    private EventCenterConfigBuilder tmp;  //各个配置项
    private static EventCenterConfigBuilder configBuilder;  //各个配置项

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
        configBuilder = tmp;
        configBuilder.init();
    }
}
