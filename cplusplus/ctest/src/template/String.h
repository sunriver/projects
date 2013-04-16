/*
 * String.h
 *
 *  Created on: Apr 15, 2013
 *      Author: leon
 */

#ifndef STRING_H_
#define STRING_H_
#include <iostream>
#include <string.h>

class String {
public:
	String(const char* str = NULL);
	String(const String& str);

	//是一个重载函数，有返回值
	String& operator=(const String& str);
	virtual ~String();

private:
	char* mData;
};

#endif /* STRING_H_ */
