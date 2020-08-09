package com.tank.util.async;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.vavr.CheckedConsumer;
import io.vavr.control.Try;
import lombok.NonNull;
import lombok.val;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple producer and Simple consumer
 *
 * @author tank198435163.com
 */
public class SpSc<T> {

  public SpSc(@NonNull final String consumerName) {
    this(1000, consumerName);
  }

  public SpSc(@NonNull final Integer capacity, @NonNull final String consumerName) {
    this.queue = new ArrayBlockingQueue<>(capacity);
    this.consumerName = consumerName;
  }

  public void processItem(@NonNull final CheckedConsumer<List<T>> consumer) {
    val list = Lists.<T>newLinkedList();

    val defaultBatch = 10;
    val processInterVal = 50;
    val threadPool = this.initThreadPool();
    threadPool.execute(() -> {
      System.out.println(String.format("Thread name is:[%s]", Thread.currentThread().getName()));
      while (running.get()) {
        synchronized (queue) {
          long start = System.currentTimeMillis();
          val item = Try.of(this.queue::take).get();
          try {
            list.add(item);
            // enough bach or time out will done
            val isOk = list.size() == defaultBatch || System.currentTimeMillis() - start >= processInterVal;
            if (isOk) {
              consumer.accept(list);
              list.clear();
            }
          } catch (Throwable throwable) {
            throwable.printStackTrace();
          }
        }
      }
    });
  }

  public void close() {
    this.running.set(false);
  }

  public boolean addItem(@NonNull final T item) {
    return this.queue.add(item);
  }

  private ThreadPoolExecutor initThreadPool() {
    val threadName = String.format("%s-%d", this.consumerName, this.counter.incrementAndGet());
    val threadFactoryBuilder = new ThreadFactoryBuilder().setNameFormat(threadName);
    val threadFactory = threadFactoryBuilder.build();
    return new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), threadFactory);
  }

  private final AtomicBoolean running = new AtomicBoolean(true);

  private final ArrayBlockingQueue<T> queue;

  private final String consumerName;

  private AtomicInteger counter = new AtomicInteger(0);

}
