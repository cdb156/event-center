package com.rt.bc.eventcenter.impl.broker;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.rt.bc.eventcenter.constant.Constant;
import com.rt.bc.eventcenter.impl.BeanNames;
import com.rt.bc.eventcenter.impl.EventConsumer;
import com.rt.bc.eventcenter.impl.mgr.EventCenterManager;
import com.rt.bc.eventcenter.impl.mgr.ServiceContainer;
import com.rt.bc.eventcenter.vo.EventInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shenxy on 2017-9-28.
 * 批量处理未发送的事件, 相同对象放到List里, 批量抛出事件
 *
 */
@Component(BeanNames.BEAN_PATCH_BROKER)
public class EventPatchBrokerService implements IPatchBrokerService {
    private final static Logger logger = LoggerFactory.getLogger(EventPatchBrokerService.class);

    @Autowired
    private EventConsumer eventConsumer;

    //处理接收反馈的逻辑, 默认半分钟执行一次.
    @Scheduled(initialDelay = 30000, fixedRate = 3000)    //(cron="60/3 * * * * ?")
    public void eventProcessor() throws Exception {
        if (ServiceContainer.storage == null || ServiceContainer.guarantee == null) {
            throw new Exception(Constant.NOT_INITED);
        }

        Map<String, List<EventInfo>> eventDtoMap = new HashMap<>();

        //1. 获取当前队列里, 所有消息
        List<EventInfo> eventInfoList = ServiceContainer.storage.getNotSend();
        if (eventInfoList == null || eventInfoList.isEmpty()) {
            return;
        }

        for (EventInfo eventInfo : eventInfoList) {
            List<EventInfo> eventDtoList = eventDtoMap.get(eventInfo.getEventType());
            if (eventDtoList == null) {
                eventDtoList = new ArrayList<>();
                eventDtoList.add(eventInfo);
                eventDtoMap.put(eventInfo.getEventType(), eventDtoList);
            }
            else {
                eventDtoList.add(eventInfo);
            }
        }

        //根据订阅情况, 处理所有消息
        for (String eventType : eventDtoMap.keySet()) {
            List<EventInfo> eventDtoList = eventDtoMap.get(eventType);
            List<Long> eventIdList = eventDtoList
                    .stream()
                    .map(EventInfo::getId)
                    .collect(Collectors.toList());
            List<String> eventJsonList = eventDtoList
                    .stream()
                    .map(EventInfo::getEventJson)
                    .collect(Collectors.toList());
            //2. 记录获取消息
            ServiceContainer.guarantee.preSend(eventIdList, eventJsonList);
            //3. 发送消息
            eventConsumer.onBusEvent(eventType, eventJsonList);
            //4. 记录发送结果
            ServiceContainer.guarantee.afterSend(eventIdList, eventJsonList);

        }
    }

    @Override
    public void postEvent(String eventName, String eventJson) throws Exception {
        if (!EventCenterManager.isInited() || ServiceContainer.storage == null) {
            throw new Exception("消息中心,尚未初始化");
        }

        //保存消息到队列
        ServiceContainer.storage.save(eventName, eventJson);
    }
}
