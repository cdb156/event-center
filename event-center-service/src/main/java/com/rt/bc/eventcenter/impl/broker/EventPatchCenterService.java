package com.rt.bc.eventcenter.impl.broker;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.rt.bc.eventcenter.impl.EventConsumer;
import com.rt.bc.eventcenter.impl.deliveryGuarantee.IDeliveryGuarantee;
import com.rt.bc.eventcenter.impl.storage.EventInfo;
import com.rt.bc.eventcenter.impl.storage.EventQueueStorage;
import com.rt.bc.eventcenter.util.EventNameUtil;
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
 */
@Component
public class EventPatchCenterService implements IPatchCenterService {
    private final static Logger logger = LoggerFactory.getLogger(EventPatchCenterService.class);

    @Autowired
    private EventQueueStorage eventQueueStorage;

    @Autowired
    private EventConsumer eventConsumer;

    @Autowired
    private IDeliveryGuarantee deliveryGuarantee;

    //处理接收反馈的逻辑, 默认半分钟执行一次.
    // TODO: 为了减少对当前系统的影响,也为了方便批量处理,暂时使用这种"定时批量"的方式; 后续可以使用LinkedBlockingQueue的阻塞机制, 每个事件实时处理
    @Scheduled(initialDelay = 30000, fixedRate = 3000)    //(cron="60/3 * * * * ?")
    public void eventProcessor() {
        Map<String, List<String>> eventDtoMap = new HashMap<>();

        //1. 获取当前队列里, 所有消息
        while (true) {
            EventInfo eventInfo = eventQueueStorage.poll();
            if (eventInfo == null) {
                break;
            }

            List<String> eventJsonList = eventDtoMap.get(eventInfo.getEventType());
            if (eventJsonList == null) {
                eventJsonList = new ArrayList<>();
                eventJsonList.add(eventInfo.getEventJson());
                eventDtoMap.put(eventInfo.getEventType(), eventJsonList);
            }
            else {
                eventJsonList.add(eventInfo.getEventJson());
            }
        }

        //根据订阅情况, 处理所有消息
        for (String eventType : eventDtoMap.keySet()) {
            List<String> eventJsonList = eventDtoMap.get(eventType);
            //2. 记录获取消息
            deliveryGuarantee.preSend(eventType, eventJsonList);
            //3. 发送消息
            eventConsumer.onBusEvent(eventType, eventJsonList);
            //4. 记录发送结果
            deliveryGuarantee.afterSend(eventType, eventJsonList);

        }
    }

    @Override
    public void postEvent(String eventName, String eventJson) {
        //保存消息到队列
        eventQueueStorage.save(eventName, eventJson);
    }
}
