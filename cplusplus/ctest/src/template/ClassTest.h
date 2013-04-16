/*
 * ClassTest.h
 *
 *  Created on: Apr 15, 2013
 *      Author: leon
 */

#ifndef CLASSTEST_H_
#define CLASSTEST_H_


template <class T>
class A {

private:

	A();
	~A();
	virtual T max(T a, T b) {
		return (a >= b) ? a : b;
	}
};

class B : virtual A<int> {
private:
	int max;
	virtual void printA();
public :
	virtual void printB();
};

class c : virtual A<double> {
};






#endif /* CLASSTEST_H_ */
