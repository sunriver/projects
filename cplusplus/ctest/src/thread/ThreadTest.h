/*
 * ThreadTest.h
 *
 *  Created on: Apr 16, 2013
 *      Author: leon
 */

#ifndef THREADTEST_H_
#define THREADTEST_H_

#include <queue>
#include <pthread.h>

using namespace std;

const int MAX = 10;
queue<int> productList;

pthread_mutex_t lock;
pthread_cond_t notEmptyCond;
pthread_cond_t notFullCond;

pthread_t produceThread;
pthread_t consumeThread;
timespec timeout;

void produce();
void consume();
void testThread();
void init();

#endif /* THREADTEST_H_ */
