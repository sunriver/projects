################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/template/ClassTest.cpp \
../src/template/String.cpp 

OBJS += \
./src/template/ClassTest.o \
./src/template/String.o 

CPP_DEPS += \
./src/template/ClassTest.d \
./src/template/String.d 


# Each subdirectory must supply rules for building sources it contributes
src/template/%.o: ../src/template/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


