# java-threads

-- Runnable implementation
-- Thread implementation


-- Atomic variables are used to prevent thread interference instead of using synchronise blocks

-- A synchronizer is an object that controls thread flow based on some constrains

-- Java.util.concurrent
-- Semaphores
-- CyclicBarrier

#Semaphore Synchronizer
It is a classic concurrent tool
It is used to restrict use of resources among threads
It make use of acquire() and release() to acquire and release access to resources.

Semaphore s = new Semaphore(NO_OF_PERMIT)

#Barriers
It can be used when you have a task that can be divided into subtasks.
It can't be reused

#Latches
It makes some group of thread to wait until certain number of one time activities have completed.
It can be reused.

#Phasers
We can add more threads dynamically
It is reuseable

phaser.arriveAndDeregister would deregister all threads

#Exchanger
It lets 2 threads waith for each other at synchronization point
