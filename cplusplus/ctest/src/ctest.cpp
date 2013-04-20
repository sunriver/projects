//============================================================================
// Name        : ctest.cpp
// Author      : leon
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "./sort/shell.h"
#include "./template/ClassTest.h"
#include "./thread/ThreadTest.h"

using namespace std;

char * testSprintf() {
	char s[64];
	int offset = 0;
	for (int i = 0; i < 10; i++) {
		offset += sprintf(s + offset, "%d,", rand() % 100);
	}

	cout << "s:" << s;
	return s;
}

void testMemcpy() {
	char* src = testSprintf();
	char dst[100];
	cout << "sizeof(src):" << sizeof(src) << "\n";
	memcpy(dst, src, strlen(src));
	cout << "dst:" << dst;
}


/**
 * convert a string to a integer
 * case : negative number;
 * case : filter characters
 * case : null string
 */
int atoi(const char* str) {
	if (NULL == str) {
		return -1;
	}
	bool negative = false;
	int i = 0;
	if (str[0] == '-') {
		negative = true;
		i++;
	}
	int num = 0;
	while (str[i] != '\0') {
		if (str[i] >= '0' && str[i] <= '9') {
			num = num * 10 + str[i] - '0';
		}
		i++;
	}
	if (negative) {
		num = 0 - num;
	}
	return num;
}

void accessPrivateVirtualMethod() {

}

int main() {
	cout << "!!!Hello World!!!" << endl; // prints !!!Hello World!!!
	//testMemcpy();

	int s[] = { 23, 10 , 65, 2, 87, 33, 9, 32, 22, 34, 21, 4, 85, 33, 27};
	int len = sizeof(s) / sizeof(s[0]);
	cout << "len:" << len << "\n";
	shell::sort(s, 0, len);
	for (int i = 0; i < len; i++) {
		cout << s[i] << ", ";
	}
	cout << "\n";
	int num = atoi("-4389tf93");
	cout << "atoi:" << num << "\n";

	return 0;
}
