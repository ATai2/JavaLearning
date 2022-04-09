package com.atai.basic.collection;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class T003_DelayedQueue {
    public static void main(String[] args) throws InterruptedException {
        // 定义DelayQueue，无需指定容量，因为DelayQueue是一个"无边界"的阻塞队列
        DelayQueue<DelayedEntry> delayQueue = new DelayQueue<>();
// 存入数据A，数据A将在10000毫秒后过期，或者说会被延期10000毫秒后处理
        delayQueue.put(new DelayedEntry("A", 10 * 1000L));
// 存入数据A，数据B将在5000毫秒后过期，或者说会被延期5000毫秒后处理
        delayQueue.put(new DelayedEntry("B", 5 * 1000L));
// 记录时间戳
        final long timestamp = System.currentTimeMillis();
// 非阻塞读方法，立即返回null，原因是当前AB元素不会有一个到达过期时间
        assert delayQueue.poll() == null;


// take方法会阻塞5000毫秒左右，因为此刻队列中最快达到过期条件的数据B只能在5000毫秒以后
        DelayedEntry value = delayQueue.take();
// 断言队列头部的元素为B
        assert value.getValue().equals("B");
// 耗时5000毫秒或以上
        assert (System.currentTimeMillis() - timestamp) >= 5_000L;

// 再次执行take操作
        value = delayQueue.take();
// 断言队列头部的元素为A
        assert value.getValue().equals("A");
// 耗时在10000毫秒或以上
        assert (System.currentTimeMillis() - timestamp) >= 10_000L;
    }

    static class DelayedEntry implements Delayed {
        // 元素数据内容
        private final String value;
        // 用于计算失效时间
        private final long time;

        private DelayedEntry(String value, long delayTime) {
            this.value = value;
            // 该元素可在（当前时间+delayTime）毫秒后消费，也就是说延迟消费delayTime毫秒
            this.time = delayTime + System.currentTimeMillis();
        }

        // 重写getDelay方法，返回当前元素的延迟时间还剩余（remaining）多少个时间单位
        @Override
        public long getDelay(TimeUnit unit) {
            long delta = time - System.currentTimeMillis();
            return unit.convert(delta, TimeUnit.MILLISECONDS);
        }

        public String getValue() {
            return value;
        }

        // 重写compareTo方法，根据我们所实现的代码可以看出，队列头部的元素是最早即将失效的数据元素
        @Override
        public int compareTo(Delayed o) {
            if (this.time < ((DelayedEntry) o).time) {
                return -1;
            } else if (this.time > ((DelayedEntry) o).time) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return "DelayedEntry{" +
                    "value='" + value + '\'' +
                    ", time=" + time +
                    '}';
        }
    }

}
