package com.rt.bc.eventcenter.consumer.mgr;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.rt.bc.eventcenter.itf.IEventConsumer;
import com.rt.bc.eventcenter.util.EventNameUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenxy on 26/9/17.
 *
 * 收集事件中心的订阅者管理. 因为在Spring容器的初始化过程中需要使用, 这个类不通过spring管理
 */
@Component
public class EventConsumerMgr {
    private final static Logger logger = LoggerFactory.getLogger(EventConsumerMgr.class);

    private Map<String, List<IEventConsumer>> eventConsumerMap = new HashMap<>();

    //注册处理器, 及其关注的事件类型
    public void addConsumer(String eventName, IEventConsumer eventConsumer) {
        logger.warn("EventConsumerMgr, addConsumer:" + eventName);
        List<IEventConsumer> consumerList = eventConsumerMap.get(eventName);
        if (consumerList == null) {
            consumerList = new ArrayList<>();
            consumerList.add(eventConsumer);
            eventConsumerMap.put(eventName, consumerList);
        }
        else if (!consumerList.contains(eventConsumer)) {  //不重复添加
            consumerList.add(eventConsumer);
        }
    }


    public List<IEventConsumer> getConsumer(String eventName) {
        String[] eventTypeArray = EventNameUtil.parseEventNames(eventName);
        List<IEventConsumer> ret = new ArrayList<>();
        if (eventTypeArray == null) {
            return ret;
        }

        for (String type : eventTypeArray) {
            if (eventConsumerMap.get(type) != null) {
                ret.addAll(eventConsumerMap.get(type));
            }
        }
        return ret;
    }

}
