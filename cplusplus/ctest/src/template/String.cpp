/*
 * String.cpp
 *
 *  Created on: Apr 15, 2013
 *      Author: leon
 */

#include "String.h"

String::String(const char* str) {
	if (NULL == str) {
		mData = new char[1];
		mData[0] = '\0';
	} else {
		mData = new char[strlen(str) + 1];
		strcpy(mData, str);
	}
}

String& String::operator=(const String& str) {
	if (this == &str) {
		return *this;
	}

	//Allocate new memory and Delete useless str memory
	mData = new char[strlen(str.mData) + 1];
	strcpy(mData, str.mData);
	delete []mData;
	return *this;
}

String::String(const String& str) {
	mData = new char[strlen(str.mData) + 1];
	strcpy(mData, str.mData);
}


String::~String() {
	delete []mData;
}

