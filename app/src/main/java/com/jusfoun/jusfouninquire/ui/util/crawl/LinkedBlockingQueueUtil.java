package com.jusfoun.jusfouninquire.ui.util.crawl;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhaoyapeng
 * @version create time:17/7/3111:15
 * @Email zyp@jusfoun.com
 * @Description ${队列工具类}
 */
public class LinkedBlockingQueueUtil {

    /**
     * 此处使用LinkedBlockingQueue
     *
     * LinkedBlockingQueue 可指定具体容量，未指定容量默认为Integer.MAX_VALUE
     * 先进先出（FIFO）
     *
     *PriorityBlockingQueue 优先级队列  无边界
     *
     * DelayQueue延迟队列 无边界
     *
     *ArrayBlcokingQueue 有界（必须有界）
     *
     * */

    private LinkedBlockingQueue<TaskModel> linkedBlockingQueue;

    private static LinkedBlockingQueueUtil linkedBlockingQueueUtil;

    private LinkedBlockingQueueUtil() {
        linkedBlockingQueue = new LinkedBlockingQueue<>();
    }

    public static LinkedBlockingQueueUtil getInstance() {
        if (linkedBlockingQueueUtil == null) {
            linkedBlockingQueueUtil = new LinkedBlockingQueueUtil();
        }
        return linkedBlockingQueueUtil;
    }


    public  void add(TaskModel taskModell){
        linkedBlockingQueue.add(taskModell);
    }


}
