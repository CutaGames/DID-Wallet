package com.carbonchain.server.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IdGenerator {
	/**
	 * SnowFlake算法 64位Long类型生成唯一ID 第一位0，表明正数 2-42，41位，表示毫秒时间戳差值，起始值自定义
	 * 43-52，10位，机器编号，5位数据中心编号，5位进程编号 53-64，12位，毫秒内计数器 本机内存生成，性能高
	 * 
	 * 主要就是三部分： 时间戳，进程id，序列号 时间戳41，id10位，序列号12位
	 * 
	 */
	private final static long beginTs = 1483200000000L;
	private static long lastTs = 0L;
	private static long processId = 511;
	private static int processIdBits = 9;
	private final static Lock LOCK = new ReentrantLock();
	private static long sequence = 0L;
	private static int sequenceBits = 12;

	public static String nextId(String prefix) {
		LOCK.lock();
		try {

			long ts = System.currentTimeMillis();
			if (ts < lastTs) {// 刚刚生成的时间戳比上次的时间戳还小，出错
				throw new RuntimeException("时间戳顺序错误");
			}
			if (ts == lastTs) {// 刚刚生成的时间戳跟上次的时间戳一样，则需要生成一个sequence序列号
				// sequence循环自增
				sequence = (sequence + 1) & ((1 << sequenceBits) - 1);
				// 如果sequence=0则需要重新生成时间戳
				if (sequence == 0) {
					// 且必须保证时间戳序列往后
					ts = nextTs(lastTs);
				}
			} else {// 如果ts>lastTs，时间戳序列已经不同了，此时可以不必生成sequence了，直接取0
				sequence = 0L;
			}
			lastTs = ts;// 更新lastTs时间戳
			return prefix + String.valueOf(
					((ts - beginTs) << (processIdBits + sequenceBits)) | (processId << sequenceBits) | sequence);
		} finally {
			LOCK.unlock();
		}
	}

	private static long nextTs(long lastTs) {
		long ts = System.currentTimeMillis();
		while (ts <= lastTs) {
			ts = System.currentTimeMillis();
		}
		return ts;
	}

	public static void main(String[] args) throws Exception {
		final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		final CountDownLatch latch = new CountDownLatch(8);
		String nextId1 = IdGenerator.nextId("RUN");
		System.out.println(nextId1);
		map.clear();
		ExecutorService threadPool = Executors.newFixedThreadPool(8);
		for (int i = 0; i < 8; i++) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 100000; j++) {
						String nextId = IdGenerator.nextId("RUN");
						System.out.println(nextId);
						map.put(nextId, nextId);
					}
					latch.countDown();
				}
			});
		}

		latch.await();
		System.out.println(map.size());
	}
}