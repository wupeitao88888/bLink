package com.shiliuke.view.stickerview;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by wangzhi on 15/11/2.
 */
public class StickerExecutor {
    private static ScheduledExecutorService scheduledThreadPool;
    private static ExecutorService cachedThreadPool;

    private StickerExecutor() {
    }

    /**
     * 得到周期线程池实例
     *
     * @return
     */
    public static ScheduledExecutorService getSingleScheduledExecutor() {
        if (scheduledThreadPool == null) {
            scheduledThreadPool = Executors.newScheduledThreadPool(5);
        }
        return scheduledThreadPool;
    }

    /**
     * 得到普通线程池实例
     *
     * @return
     */
    public static ExecutorService getSingleExecutor() {
        if (cachedThreadPool == null) {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        return cachedThreadPool;
    }

    /**
     * 停止执行
     */
    public static void stopExecutor() {
        if (scheduledThreadPool != null) {
            scheduledThreadPool.shutdown();
            scheduledThreadPool = null;
        }
        if (cachedThreadPool != null) {
            cachedThreadPool.shutdown();
            cachedThreadPool = null;
        }
    }
}
