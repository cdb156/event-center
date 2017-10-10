package com.rt.bc.eventcenter.impl.storage;

import com.rt.bc.eventcenter.dao.EventMapper;
import com.rt.bc.eventcenter.vo.EventInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by shenxy on 30/9/17.
 *
 * 使用mysql存储事件, 做持久化
 */
@Component
public class EventMysqlStorage implements IEventStorage, InitializingBean {
    @Autowired
    private EventMapper eventMapper;

//    @Autowired
//    private IOidProvider oidProvider;

    @Override
    public EventInfo save(String eventName, String eventJson) {
//        Long id = oidProvider.generateNextId();
        EventInfo eventInfo = new EventInfo(eventName, eventJson);
        eventMapper.insert(eventInfo);

        return eventMapper.queryById(eventInfo.getId());
    }

    @Override
    public boolean changeStatus(List<Long> eventIdList, Integer status) {
        return eventMapper.updateStatus(eventIdList, status) > 0;
    }

    @Override
    public List<EventInfo> getNotSend() {
        return eventMapper.queryNotSend();
    }

//    @Override
//    public EventInfo poll() {
//        EventInfo eventInfo = eventMapper.queryLast();
//        if (eventInfo == null) {
//            return null;
//        }
//        if (changeStatus(Collections.singletonList(eventInfo.getId()), EventInfo.STATUS_SENDED)) {
//            return eventInfo;
//        }
//
//        try {
//            throw new Exception("获取最新一条消息并更新消息状态异常");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 每次初始化都尝试创建, 要求createTable的sql可以重复执行
        eventMapper.createTable();
    }
}
