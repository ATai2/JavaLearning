package com.atai.basic.juc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;

/**
 * CoundDownLatch的await方法会等待计数器被count down到0，而执行CyclicBarrier的await方法的线程将会等待其他线程到达barrier point。
 * CyclicBarrier内部的计数器count是可被重置的，进而使得CyclicBarrier也可被重复使用，而CoundDownLatch则不能。
 * CyclicBarrier是由Lock和Condition实现的，而CountDownLatch则是由同步控制器AQS（AbstractQueuedSynchronizer）来实现的。
 * 在构造CyclicBarrier时不允许parties为0，而CountDownLa
 */
public class T005_CyclicBarrier extends T004_CountDownLatch {
//    它允许多个线程在执行完相应的操作之后彼此等待共同到达一个障点（barrier point）

    public static void main(String[] args)
            throws InterruptedException {
        // 根据商品品类获取一组商品ID
        final int[] products = getProductsByCategoryId();
        // 通过转换将商品编号转换为ProductPrice
        List<ProductPrice> list = Arrays.stream(products)
                .mapToObj(ProductPrice::new)
                .collect(toList());
        // ① 定义CyclicBarrier ，指定parties为子任务数量
        final CyclicBarrier barrier = new CyclicBarrier(list.size());
        // ② 用于存放线程任务的list
        final List<Thread> threadList = new ArrayList<>();
        list.forEach(pp ->
                {
                    Thread thread = new Thread(() ->
                    {
                        System.out.println(pp.getProdID() + "start calculate price.");
                        try {
                            TimeUnit.SECONDS.sleep(current().nextInt(10));
                            if (pp.prodID % 2 == 0) {
                                pp.setPrice(pp.prodID * 0.9D);
                            } else {
                                pp.setPrice(pp.prodID * 0.71D);
                            }
                            System.out.println(pp.getProdID() + "->price calculate completed.");
                        } catch (InterruptedException e) {
                            // ignore exception
                        } finally {
                            try {
                                // ③ 在此等待其他子线程到达barrier point
                                barrier.await();
                            } catch (InterruptedException
                                    | BrokenBarrierException e) {
                            }
                        }
                    });
                    threadList.add(thread);
                    thread.start();
                }
        );
        // ④ 等待所有子任务线程结束
        threadList.forEach(t ->
        {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("all of prices calculate finished.");
        list.forEach(System.out::println);
    }

}
