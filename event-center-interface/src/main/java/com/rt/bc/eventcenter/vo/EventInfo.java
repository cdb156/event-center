package com.rt.bc.eventcenter.vo;

import java.io.Serializable;

/**
 * Created by shenxy on 9/10/17.
 *
 * 事件消息对象
 */
public class EventInfo implements Serializable {
    public static final Integer STATUS_NOT_SEND = 0;
    public static final Integer STATUS_SENDING = 1;
    public static final Integer STATUS_SENDED = 2;
//    // 消息状态
//    public enum EventStatus {
//        notSend(0),    //未发送
//        sending(1),    //发送中
//        sended(2);      //已发送
//
//        private int value;
//        EventStatus(int i) {
//            this.value = i;
//        }
//
//        public int getValue() {
//            return value;
//        }
//    }

    private Long id;
    private String eventType;
    private String eventJson;
    private Integer status;
//    private Integer statusValue;

    public EventInfo(String eventType, String eventJson) {
        this.eventType = eventType;
        this.eventJson = eventJson;
//        setStatus(EventStatus.notSend);
        this.status = STATUS_NOT_SEND;
    }

    public EventInfo(Long id, String eventType, String eventJson) {
        this.id = id;
        this.eventType = eventType;
        this.eventJson = eventJson;
//        setStatus(EventStatus.notSend);
        this.status = STATUS_SENDED;
    }

    public EventInfo(Long id, String eventType, String eventJson, Integer statusValue) {
        this.id = id;
        this.eventType = eventType;
        this.eventJson = eventJson;
        this.status = statusValue;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
//    public EventStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(EventStatus status) {
//        this.status = status;
//        this.statusValue = status.ordinal();
//    }
//
//    public void setStatus(Integer statusValue) {
//        switch (statusValue) {
//            case 0:
//                setStatus(EventStatus.notSend);
//                break;
//            case 1:
//                setStatus(EventStatus.sending);
//                break;
//            case 2:
//                setStatus(EventStatus.sended);
//                break;
//            default:
//                setStatus(EventStatus.notSend);
//        }
//    }
//
//    public Integer getStatusValue() {
//        return statusValue;
//    }
}
