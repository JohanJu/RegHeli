# Produced by CVXGEN, 2016-05-10 07:07:34 -0400.
# CVXGEN is Copyright (C) 2006-2012 Jacob Mattingley, jem@cvxgen.com.
# The code in this file is Copyright (C) 2006-2012 Jacob Mattingley.
# CVXGEN, or solvers produced by CVXGEN, cannot be used for commercial
# applications without prior written permission from Jacob Mattingley.

# Filename: Makefile.
# Description: Basic Makefile.
OPT = -Wall -Os
# libmath is needed for sqrt, which is used only for reporting the gap. Can
# remove if desired for production solvers..
LDLIBS = -lm
CFLAGS = $(OPT) $(INCLUDES)
CC = gcc
.PHONY: all
all: testsolver
testsolver: solver.o matrix_support.o ldl.o testsolver.o util.o
# Include util.o for random functions and easy matrix printing.
#testsolver: solver.o matrix_support.o ldl.o util.o testsolver.o
solver.o: solver.h
matrix_support.o: solver.h
ldl.o: solver.h
util.o: solver.h
testsolver.o: solver.h
java: solver.o matrix_support.o ldl.o util.o
	javac JavaCall.java
	gcc -shared -fpic -o libmpc.so -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux JavaCall.c solver.c matrix_support.c ldl.c util.c
	java -Djava.library.path=. JavaCall
run:
	javac JavaCall.java
	java -Djava.library.path=. JavaCall
.PHONY : clean
clean :
	-rm -f *.o testsolver *.so *.class
