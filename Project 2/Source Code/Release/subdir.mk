################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../module3.cpp \
../operand.cpp \
../parse.cpp \
../subexpression.cpp \
../symboltable.cpp \
../variable.cpp 

OBJS += \
./module3.o \
./operand.o \
./parse.o \
./subexpression.o \
./symboltable.o \
./variable.o 

CPP_DEPS += \
./module3.d \
./operand.d \
./parse.d \
./subexpression.d \
./symboltable.d \
./variable.d 


# Each subdirectory must supply rules for building sources it contributes
%.o: ../%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


