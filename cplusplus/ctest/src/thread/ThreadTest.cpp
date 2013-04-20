/*
 * ThreadTest.cpp
 *
 *  Created on: Apr 16, 2013
 *      Author: leon
 */

#include "ThreadTest.h"
#include <iostream>


void init() {
	timeout.tv_sec = 1;
	timeout.tv_nsec = 0;

	pthread_mutex_init(&lock, NULL);
	pthread_cond_init(&notEmptyCond, NULL);
	pthread_cond_init(&notFullCond, NULL);

	//argument 3 can't be as  a memember function , should be as static or globe function
	int ret = pthread_create(&produceThread, NULL, (void* (*)(void*)) produce, NULL);
	if (ret != 0) {
		cout << "Fail to create produce thread.\n";
		return;
	}

	ret = pthread_create(&consumeThread, NULL, (void* (*)(void*)) consume, NULL);
	if (ret != 0) {
		cout << "Fail to create consume thread.\n";
		return;
	}
}

void produce() {
	cout << "produce()+\n";
	int i = 0;
	while (true) {
		pthread_mutex_lock(&lock);
		while (productList.size() == MAX) {
			pthread_cond_wait(&notFullCond, &lock);
		}

		pthread_cond_timedwait(&notFullCond, &lock, &timeout);

		i++;
		productList.push(i);
		cout << "produce i:" << i << "\n";
		if (productList.size() == 1) {
			pthread_cond_signal(&notEmptyCond);
		}
		pthread_mutex_unlock(&lock);
	}
}

void consume() {
	cout << "consume()+\n";
	while (true) {
		pthread_mutex_lock(&lock);
		while (productList.size() == 0) {
			pthread_cond_wait(&notEmptyCond, &lock);
		}
		int i = productList.front();
		cout << "consume i:" << i << "\n";
		if (productList.size() == MAX - 1) {
			pthread_cond_signal(&notFullCond);
		}
	}
}

void testThread() {
	init();

}



