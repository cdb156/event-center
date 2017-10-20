package com.github.outerman.eventcenter.util;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import java.io.Serializable;

/**
 * Created by shenxy on 29/9/17.
 *
 * 组装和解析eventName的工具类
 */
public class EventNameUtil {
    private final static Logger logger = LoggerFactory.getLogger(EventNameUtil.class);

    private static final String spliter = "__";

    private static final String nullName = " "; //空格

    //根据事件类型和事件对象, 组装事件名称
    public static String toEventName(String eventType, Serializable eventObj) {
        Class cls;
        if (eventObj == null) {
            cls = null;
        }
        else {
            cls = eventObj.getClass();
        }

        return toEventName(eventType, cls);
    }

    public static String toEventName(String eventType, Class cls) {
        return (cls == null ? nullName : cls.getName())
                + spliter
                + (StringUtils.isEmpty(eventType) ? nullName : eventType);
    }

    //根据事件名称, 解析出事件对象类型
    public static Class parseEventObjClass(String eventName) {
        if (StringUtils.isEmpty(eventName) || !eventName.contains(spliter)) {
            return null;
        }

        String className = eventName.split(spliter)[0];

        if (StringUtils.isEmpty(className) || nullName.equals(className)) {
            return null;
        }

        Class cls;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.warn("EventNameUtil: The cls of this event is not founded. className:" + className);
            return null;
        }

        return cls;
    }

    //根据事件名称, 解析出事件类型名称
    public static String parseEventType(String eventName) {
        if (StringUtils.isEmpty(eventName) || !eventName.contains(spliter)) {
            return null;
        }
        String[] eventParams = eventName.split(spliter);

        if (eventParams.length < 2) {
            return null;
        }
        if (StringUtils.isEmpty(eventParams[1]) || nullName.equals(eventParams[1])) {
            return "";
        }
        return eventName.split(spliter)[1];
    }

    //根据事件名称, 解析出可能匹配的事件名称(考虑eventType为空的通配的情况)
    public static String[] parseEventNames(String eventName) {
        if (StringUtils.isEmpty(eventName) || !eventName.contains(spliter)) {
            return null;
        }

        String[] eventParams = eventName.split(spliter);

        if (eventParams.length < 2) {
            return null;
        }

        if (!StringUtils.isEmpty(eventParams[1]) && !nullName.equals(eventParams[1])) {
            return new String[]{eventParams[0] + spliter + nullName, eventName};
        }
        else {
            return new String[]{eventName};
        }
    }
}
