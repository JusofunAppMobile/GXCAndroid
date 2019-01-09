package com.jusfoun.jusfouninquire.ui.util.crawl;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhaoyapeng
 * @version create time:17/7/3116:28
 * @Email zyp@jusfoun.com
 * @Description ${所有 请求 最终都在这里执行}
 */
public class ReptileDispatcher extends Thread {

    private boolean mQuit = false;
    private boolean lock = false;// 锁标志位，当前 任务正在执行，不再弹出 任务
    private boolean taskEnd = false;// 整个task 是否结束（）
    private LinkedBlockingQueue<BaseReptileRequest> mQueue;

    public ReptileDispatcher(LinkedBlockingQueue<BaseReptileRequest> mQueue) {
        this.mQueue = mQueue;
    }


    /**
     * 从队列中取元素 有三种方法
     * poll 队列为空返回null
     * remove 队列为空 出现 NoSuchElementException异常
     * task  队列为空 发生阻塞，等待元素
     * <p>
     * 此处 take 最适合需求
     */
    @Override
    public void run() {
        BaseReptileRequest reptileRequest;

        while (true) {
            if (!lock) {
                try {
                    reptileRequest = mQueue.take();

//                    if (mQueue.size() == 0) {
//                        //当队列 size为0 时，为最后一个 请求，设置标记位
//                        reptileRequest.setAtLast(true);
//                    } else {
//                        reptileRequest.setAtLast(false);
//                    }
                    reptileRequest.setDispatcher(this);
                } catch (Exception e) {
                    // 出现异常时， 如果此时 是退出状态则结束，否则 继续进行
                    if (mQuit) {
                        return;
                    }
                    continue;

                }
                lock = true;
                reptileRequest.start();
            }

        }
    }

    // 停止全部
    public void quit() {
        mQuit = true;
    }

    /**
     * 上一个任务已执行完毕
     */
    public void taskFinish() {
        lock = false;
    }
}
