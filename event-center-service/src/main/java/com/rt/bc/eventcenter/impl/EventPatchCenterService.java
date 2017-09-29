package com.rt.bc.eventcenter.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenxy on 2017-9-28.
 * 批量处理未发送的事件, 相同对象放到List里, 批量抛出事件
 *
 * //TODO: 是否需要考虑:相同数据对象,不同事件类型?
 */
@Component
public class EventPatchCenterService {
    private final static Logger logger = LoggerFactory.getLogger(EventPatchCenterService.class);

    @Autowired
    EventQueueMgr eventQueueMgr;

    @Autowired
    private EventConsumer eventConsumer;


    //处理接收反馈的逻辑, 默认半分钟执行一次.
    // TODO: 为了减少对当前系统的影响,也为了方便批量处理,暂时使用这种"定时批量"的方式; 后续可以使用LinkedBlockingQueue的阻塞机制, 每个事件实时处理
    @Scheduled(initialDelay = 30000, fixedRate = 3000)    //(cron="60/3 * * * * ?")
    public void eventProcessor() {
        Map<String, List<Object>> eventDtoMap = new HashMap<>();

        //获取当前队列里, 所有消息
        while (true) {
            EventQueueMgr.EventQueueInfo eventInfo = eventQueueMgr.poll();
            if (eventInfo == null) {
                break;
            }

            List<Object> eventObjList = eventDtoMap.get(eventInfo.eventType);
            if (eventObjList == null) {
                eventObjList = new ArrayList<>();
                eventObjList.add(eventInfo.getObject());
                eventDtoMap.put(eventInfo.eventType, eventObjList);
            }
            else {
                eventObjList.add(eventInfo.getObject());
            }
        }

        //根据订阅情况, 处理所有消息
        for (String cls : eventDtoMap.keySet()) {
            eventConsumer.onBusEvent(cls, eventDtoMap.get(cls).toString());
        }
    }
}
