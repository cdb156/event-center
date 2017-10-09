//package com.rt.bc.eventcenter.impl.oid;
//
//import com.rt.bc.eventcenter.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//
///**
// * @Title:       SnowFlakeProvider.java
// * @Description: SnowFlake算法产生id
// * @author yxq
// */
//@Component
//public class SnowFlakeProvider implements IOidProvider {
//    private final long workerId;
//    private static final long twepoch = 0x1532af4e2ddL;
//    private long sequence;
//    private static final long workerIdBits = 6L;
//    public static final long maxWorkerId = 1023L;
//    private static final long sequenceBits = 10L;
//    private static final long workerIdShift = 10L;
//    private static final long timestampLeftShift = 16L;
//    public static final long sequenceMask = 1023L;
//    public static final String OID_WORKID = "OID_WORKERID";
//    private long lastTimestamp;
//    private Logger logger;
//
//    public SnowFlakeProvider()
//    {
//        sequence = 0L;
//        lastTimestamp = -1L;
//        logger = LoggerFactory.getLogger(this.getClass());
//        String workerIdStr = System.getProperty(OID_WORKID);
//        if(StringUtils.isEmpty(workerIdStr))
//            workerIdStr = System.getenv(OID_WORKID);
//		if (StringUtils.isEmpty(workerIdStr)) {
//			String err = "get workerid form system env error!, please set env for oid!";
//			logger.error(err);
//			throw new IllegalArgumentException(err);
//		} else {
//			long wid = Long.parseLong(workerIdStr);
//			workerId = wid;
//			return;
//		}
//    }
//
//    public SnowFlakeProvider(long workerId)
//    {
//        sequence = 0L;
//        lastTimestamp = -1L;
//        logger = LoggerFactory.getLogger(this.getClass());
//        if(workerId > maxWorkerId || workerId < 0L)
//        {
//            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
//        } else
//        {
//            this.workerId = workerId;
//            return;
//        }
//    }
//
//    public synchronized long nextId()
//    {
//        long timestamp = timeGen();
//        if(lastTimestamp == timestamp)
//        {
//            sequence = sequence + 1L & 1023L;
//            if(sequence == 0L)
//            {
//                logger.info("sequenceMask value is {}.1023");
//                timestamp = tilNextMillis(lastTimestamp);
//            }
//        } else
//        {
//            sequence = 0L;
//        }
//        if(timestamp < lastTimestamp)
//            try
//            {
//                throw new Exception(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", (lastTimestamp - timestamp)));
//            }
//            catch(Exception e)
//            {
//                logger.error(e.getMessage(), e);
//            }
//        lastTimestamp = timestamp;
//        return timestamp - 0x1532af4e2ddL << 16 | workerId << 10 | sequence;
//    }
//
//    private long tilNextMillis(long lastTimestamp)
//    {
//        long timestamp;
//        for(timestamp = timeGen(); timestamp <= lastTimestamp; timestamp = timeGen());
//        return timestamp;
//    }
//
//    private long timeGen()
//    {
//        return System.currentTimeMillis();
//    }
//
//    @Override
//    public String generateIdStr(String module)
//    {
//        return String.valueOf(nextId());
//    }
//
//    @Override
//    public Long generateNextId() {
//        return nextId();
//    }
//
//    public static void main(String args[])
//    {
//        SnowFlakeProvider provider = new SnowFlakeProvider();
//        String id = provider.generateIdStr(null);
//        System.out.println(id);
//    }
//
//
//}
