package com.rt.bc.eventcenter.annotation;

import java.lang.annotation.*;

/**
 * Created by shenxy on 26/9/17.
 *
 * 事件消费者的注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Consumer {

//    //事件回调的模式
//    enum ConsumMode {
//        patch,          //延迟 + 批量 + 线程池
//        immediate       //在当前线程立即回调(同步)
//    }

//    ConsumMode consumMode() default ConsumMode.immediate;

    //关注的事件类型, 为空表示关注所有类型
    String eventType() default "";
}
