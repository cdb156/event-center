package com.rt.bc.eventcenter.dao;

import com.rt.bc.eventcenter.impl.storage.EventInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by shenxy on 9/10/17.
 *
 * Mysql存取接口
 */
public interface EventMapper {
    int insert(@Param("eventInfo") EventInfo eventInfo);

    EventInfo queryById(@Param("id") Long id);

    int updateStatus(@Param("evetIdList") List<Long> eventIdList, @Param("status") int ordinal);

    boolean createTable();
}
