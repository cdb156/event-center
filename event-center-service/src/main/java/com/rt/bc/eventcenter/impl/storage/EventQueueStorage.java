package com.rt.bc.eventcenter.impl.storage;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.rt.bc.eventcenter.impl.BeanNames;
import com.rt.bc.eventcenter.vo.EventInfo;
import com.rt.bc.eventcenter.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by shenxy on 27/9/17.
 *
 * 事件队列管理
 */
@Component(BeanNames.BEAN_MEM_STORAGE)
public class EventQueueStorage implements IEventStorage{
    private final static Logger logger = LoggerFactory.getLogger(EventQueueStorage.class);
//    @Autowired
//    private IOidProvider iOidProvider;

    // 反馈信息的队列
    // 各个方法的含义,备忘:
    //    add       增加一个元索                如果队列已满，则抛出一个IIIegaISlabEepeplian异常
    //    remove    移除并返回队列头部的元素      如果队列为空，则抛出一个NoSuchElementException异常
    //    element   返回队列头部的元素           如果队列为空，则抛出一个NoSuchElementException异常
    //    offer     添加一个元素并返回true       如果队列已满，则返回false
    //    poll      移除并返问队列头部的元素      如果队列为空，则返回null
    //    peek      返回队列头部的元素           如果队列为空，则返回null
    //    put       添加一个元素                如果队列满，则阻塞
    //    take      移除并返回队列头部的元素      如果队列为空，则阻塞
    private LinkedBlockingQueue<EventInfo> eventQueue = new LinkedBlockingQueue<>();

//    @Override
//    public EventInfo poll() {
//        return eventQueue.poll();
//    }

    @Override
    public List<EventInfo> getNotSend() {
        List<EventInfo> ret = new ArrayList<>();
        while (true) {
            EventInfo eventInfo = eventQueue.poll();
            if (eventInfo == null) {
                break;
            }

            ret.add(eventInfo);
        }

        return ret;
    }
    // 收取上报的反馈信息
    @Override
    public EventInfo save(String eventName, String event) {
        if (event == null && StringUtils.isEmpty(eventName)) {
            return null;
        }

//        Long id = iOidProvider.generateNextId();
        EventInfo eventInfo = new EventInfo(eventName, event);
        if (!eventQueue.offer(eventInfo)){
            logger.warn("failed to offer a event to eventQueue");
            return null;
        }

        return eventInfo;
    }

    @Override
    public boolean changeStatus(List<Long> eventIdList, Integer status) {
        //在发送的时候, 已经出栈了, 所以不用做什么
        return true;
    }

}
