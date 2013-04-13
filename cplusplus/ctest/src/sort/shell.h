/*
 * shell.h
 *
 *  Created on: Apr 13, 2013
 *      Author: leon
 */

#ifndef SHELL_H_
#define SHELL_H_

class shell {
public:
	shell();
	virtual ~shell();
	static void sort(int* s, int low, int high);

private:
	static int shellPass(int* s, int low, int high);
};

#endif /* SHELL_H_ */
