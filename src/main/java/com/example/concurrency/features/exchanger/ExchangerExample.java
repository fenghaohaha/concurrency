package com.example.concurrency.features.exchanger;

import com.example.concurrency.features.threadPool.ThreadPoolBuilder;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述:
 * 交换者 用于两个线程间的数据交换
 *
 * 只能是两个线程，不支持更多的线程，当线程A调用exchange方法后，会陷入到阻塞状态，
 * 直到线程B也调用了exchange方法，然后两者以线程安全的方式交换数据，各自继续运行
 * @author zed
 * @since 2019-07-03 5:39 PM
 */
public class ExchangerExample {
    private static final Exchanger<String> EXCHANGER = new Exchanger<>();
    private static ThreadPoolExecutor poolExecutor = ThreadPoolBuilder.fixedPool().setPoolSize(2).build();

    public static void main(String[] args) {
        poolExecutor.execute(()->{
            try{
                String s ="SomethingAndA";
                String s1 = EXCHANGER.exchange(s);
                System.out.println("thread 1  s1 = " + s1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        poolExecutor.execute(()->{
            try{
                String s1 = "SomethingAndB";
                Thread.sleep(10000);
                String s = EXCHANGER.exchange(s1);
                System.out.println("s和s1值是否相等：" + s1.equals(s) + ",s：" + s + ",s1:" + s1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        poolExecutor.shutdown();
    }
}

