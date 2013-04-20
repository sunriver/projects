################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/thread/ThreadTest.cpp 

OBJS += \
./src/thread/ThreadTest.o 

CPP_DEPS += \
./src/thread/ThreadTest.d 


# Each subdirectory must supply rules for building sources it contributes
src/thread/%.o: ../src/thread/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


