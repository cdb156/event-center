package com.rt.bc.eventcenter.impl.mgr;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.rt.bc.eventcenter.impl.broker.EventImmediateCenterService;
import com.rt.bc.eventcenter.impl.broker.EventPatchCenterService;
import com.rt.bc.eventcenter.impl.deliveryGuarantee.AtLeastOnceGuarantee;
import com.rt.bc.eventcenter.impl.deliveryGuarantee.AtMostOnceGuarantee;
import com.rt.bc.eventcenter.impl.storage.EventMysqlStorage;
import com.rt.bc.eventcenter.impl.storage.EventQueueStorage;

/**
 * Created by shenxy on 10/10/17.
 *
 * 各个初始化配置参数
 */
public class EventCenterConfigBuilder {
    private final static Logger logger = LoggerFactory.getLogger(EventCenterConfigBuilder.class);

    private boolean isInited;    //是否已经完成初始化

    private int guarantee = 0;
    private int storage = 0;
    private int broker = 0;

    public static final int GUARANTEE_AT_MOST_ONCE = 0;     //最多一次
    public static final int GUARANTEE_AT_LEAST_ONCE = 1;    //最少一次

    public static final int STORAGE_MYSQL = 0;              //Mysql持久化
    public static final int STORAGE_MEM = 1;                //内存(不持久化)

    public static final int BROKER_PATCH = 0;               //批量
    public static final int BROKER_IMMEDIATE = 1;           //即时上报

    public boolean init() {
        try {
            initEventCenter(this);
        }
        catch (Exception ex) {
            logger.error("初始化消息中心发生异常!", ex);
            return false;
        }

        isInited = true;

        return true;
    }

    private void initEventCenter(EventCenterConfigBuilder configBuilder) throws Exception {
        if (isInited) {
            throw new Exception("消息中心已经初始化,不能重复初始化");
        }

        if (configBuilder.getGuarantee() == GUARANTEE_AT_LEAST_ONCE) {
            ServiceContainer.guarantee = new AtLeastOnceGuarantee();
        }
        else {
            ServiceContainer.guarantee = new AtMostOnceGuarantee();
        }

        if (configBuilder.getStorage() == STORAGE_MEM) {
            ServiceContainer.storage = new EventQueueStorage();
        }
        else {
            ServiceContainer.storage = new EventMysqlStorage();
        }

        if (configBuilder.getBroker() == BROKER_IMMEDIATE) {
            ServiceContainer.defaultCenterService = new EventImmediateCenterService();
        }
        else {
            ServiceContainer.defaultCenterService = new EventPatchCenterService();
        }
    }

    public boolean isInited() {
        return isInited;
    }

    public int getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(int guarantee) {
        this.guarantee = guarantee;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getBroker() {
        return broker;
    }

    public void setBroker(int broker) {
        this.broker = broker;
    }
}
