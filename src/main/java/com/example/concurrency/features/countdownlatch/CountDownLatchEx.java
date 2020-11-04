package com.example.concurrency.features.countdownlatch;

import com.example.concurrency.features.threadPool.ThreadPoolBuilder;
import com.example.concurrency.util.ThreadUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * 描述:
 * CountDownLatchEx 中文意思 倒计时 主要用来解决一个线程等待多个线程的场景
 *
 * 让一个线程，等待countDownlatch中的所有任务都执行完成之后，countDown后会继续执行
 * @author zed
 * @since 2019-06-18 10:20 AM
 */
public class CountDownLatchEx {
    public static void main(String[] args) throws InterruptedException{
        System.out.println();
        // 创建 2 个线程的线程池
        Executor executor = ThreadPoolBuilder.fixedPool().setPoolSize(2).build();
        // 计数器初始化为 2
        CountDownLatch latch = new CountDownLatch(2);
        executor.execute(()-> {
            System.out.println("T1");
            latch.countDown();
            System.out.println("t1 finish");
        });
        executor.execute(()-> {
            System.out.println("T2");
            latch.countDown();
            ThreadUtil.sleep(3000);
            System.out.println("t2 finish");
        });
        executor.execute(()-> {
            System.out.println("T3");
            latch.countDown();
            System.out.println("t3 finish");
        });
        // 等待两个查询操作结束

        System.out.println("main thread continue");

        executor.execute(()-> {
            System.out.println("T4");
            latch.countDown();
            System.out.println("t4 finish");
        });

    }
}

