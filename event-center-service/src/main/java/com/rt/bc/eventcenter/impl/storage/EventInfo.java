package com.rt.bc.eventcenter.impl.storage;

import java.io.Serializable;

/**
 * Created by shenxy on 9/10/17.
 *
 * 事件消息对象
 */
public class EventInfo {
    // 消息状态
    public enum EventStatus {
        notSend(0),    //未发送
        sending(1),    //发送中
        sended(2);      //已发送

        private int value;
        EventStatus(int i) {
            this.value = i;
        }

        public int getValue() {
            return value;
        }
    }

    private Long id;
    private String eventType;
    private String eventJson;
    private EventStatus status;

    public EventInfo(String eventType, String eventJson) {
        this.eventType = eventType;
        this.eventJson = eventJson;
        this.status = EventStatus.notSend;
    }

    public EventInfo(Long id, String eventType, String eventJson) {
        this.id = id;
        this.eventType = eventType;
        this.eventJson = eventJson;
        this.status = EventStatus.notSend;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventJson() {
        return eventJson;
    }

    public void setEventJson(String eventJson) {
        this.eventJson = eventJson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
