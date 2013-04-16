package com.current;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * simulate produce / consume thread module.
 * @author leon
 *
 */

public class ConditionTest {
	
	private Queue<Integer> mList = new LinkedList<Integer>();
	private Lock mLock = new ReentrantLock();
	private Condition mNotEmptyCondition = mLock.newCondition();
	private Condition mNotFullCondition = mLock.newCondition();
	private static final int MAX = 10;
	
	
	private Thread mProduceThread = new Thread() {
		int i = 0;
		@Override
		public void run() {
			while (true) {
				try {
					mLock.lock();
					while (mList.size() == MAX) {
						mNotFullCondition.await();
					}
					//wait 1s
					Thread.sleep(1000);
					i++;
					System.out.println("produce i:" + i);
					mList.add(i);
					if (mList.size() == 1) {
						mNotEmptyCondition.signal();
					}
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} finally {
					mLock.unlock();
				}
			}
		}
	};
	
	private Thread mConsumeThead = new Thread() {

		@Override
		public void run() {
			while (true) {
				try {
					mLock.lock();
					while (mList.size() == 0) {
						mNotEmptyCondition.await();
					}
					int i = mList.remove();
					System.out.println("comsume i:" + i);
					if (mList.size() + 1 == MAX) {
						mNotFullCondition.signal();
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} finally {
					mLock.unlock();
				}
			}
		}
	};
	
	
	public static void test() {
		System.out.println("test start!");
		ConditionTest ct = new ConditionTest();
		ct.mConsumeThead.start();
		ct.mProduceThread.start();
		try {
			ct.mConsumeThead.join();
			ct.mProduceThread.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("test end!");
	}
}
