package com.rt.bc.eventcenter.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by shenxy on 27/9/17.
 *
 * 事件队列管理
 */
@Component
public class EventQueueMgr {
    private final static Logger logger = LoggerFactory.getLogger(EventQueueMgr.class);

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
    private LinkedBlockingQueue<EventQueueInfo> eventQueue = new LinkedBlockingQueue<>();

    public EventQueueInfo poll() {
        return eventQueue.poll();
    }

    // 收取上报的反馈信息
    public void postEvent(String clsName, String event) {
        if (event == null) {
            return;
        }

        if (!eventQueue.offer(new EventQueueInfo(clsName, event))){
            logger.warn("failed to offer a event to eventQueue");
        }
    }

    public static class EventQueueInfo {
        String eventType;
        Serializable object;

        public EventQueueInfo(String eventType, Serializable object) {
            this.eventType = eventType;
            this.object = object;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public Serializable getObject() {
            return object;
        }

        public void setObject(Serializable object) {
            this.object = object;
        }
    }
}
