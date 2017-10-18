package com.rt.bc.eventcenter.consumer.bus;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.rt.bc.eventcenter.consumer.util.JsonUtil;
import com.rt.bc.eventcenter.itf.IEventProducer;
import com.rt.bc.eventcenter.itf.IEventProducerBus;
import com.rt.bc.eventcenter.util.EventNameUtil;
import com.rt.bc.eventcenter.util.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by shenxy on 2017-9-27.
 * 各种接收反馈消息的处理类基类. 在没有消息总线时, 先用这种简易方式处理反馈信息队列
 */
@Component
@Service
public class EventProducer implements IEventProducer{
    private final static Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Reference
    private IEventProducerBus eventProducerBus;

    // 收取上报的反馈信息
    @Override
    public void postEvent(String eventType, Serializable eventBean) throws Exception {
        if (eventBean == null && StringUtils.isEmpty(eventType)) {
            return;
        }

//        try {
            eventProducerBus.postEvent(EventNameUtil.toEventName(eventType, eventBean), JsonUtil.toJSONString(eventBean));
//        } catch (Exception e) {
//            logger.error("consumer EventProducer error:", e);
//        }
    }

    @Override
    public void postEventSync(String eventType, Serializable eventBean) throws Exception {
        if (eventBean == null && StringUtils.isEmpty(eventType)) {
            return;
        }

//        try {
            eventProducerBus.postEventSync(EventNameUtil.toEventName(eventType, eventBean), JsonUtil.toJSONString(eventBean));
//        } catch (Exception e) {
//            logger.error("consumer EventProducer error:", e);
//        }
    }
}
