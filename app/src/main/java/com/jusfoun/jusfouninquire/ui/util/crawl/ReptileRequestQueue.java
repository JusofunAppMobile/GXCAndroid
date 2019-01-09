package com.jusfoun.jusfouninquire.ui.util.crawl;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhaoyapeng
 * @version create time:17/7/3116:11
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class ReptileRequestQueue {
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
    private LinkedBlockingQueue<BaseReptileRequest> linkedBlockingQueue;

    private ReptileDispatcher reptileDispatcher;
    public ReptileRequestQueue(){
        linkedBlockingQueue = new LinkedBlockingQueue<>();
        reptileDispatcher = new ReptileDispatcher(linkedBlockingQueue);
    }

    /**
     *  启动调度员
     *  启动先执行停止操作
     *
     */
    public void start(){
        stop();
        reptileDispatcher.start();
    }

    public void add(BaseReptileRequest reptileRequest){
        linkedBlockingQueue.add(reptileRequest);
    }

    /**
    *  停止 抓取
    * */
    public void stop(){
        reptileDispatcher.quit();
    }


    public void reSet(){
        reptileDispatcher.taskFinish();
    }
}
