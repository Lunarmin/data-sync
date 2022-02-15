package com.data.sync.common.constant;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataSyncConstant {

    // MySQL的任务队列的最大数量
    private final static int MAX_QUEUE_MySQL_CAPACITY = 20;

    // MySQL的任务队列 需要增量更新的 数据表
    public final static BlockingQueue<String> taskMySQLQueue = new LinkedBlockingQueue<>(MAX_QUEUE_MySQL_CAPACITY);

}
