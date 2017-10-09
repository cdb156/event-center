package com.rt.bc.eventcenter.impl.oid;

/**
 * Created by shenxy on 9/10/17.
 *
 * id生成接口
 */
public interface IOidProvider
{
    String generateIdStr(String s);

    Long generateNextId();
}