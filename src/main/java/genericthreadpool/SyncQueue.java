/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericthreadpool;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tushar
 */
public class SyncQueue {

    private LinkedList<SyncQueueNode> queue;
    private int size;
    private Lock accessLock;
    private Condition canRead;
    private Condition canWrite;
    private ThreadPool genericThreadPool;

    private final int WAIT_TIME = 2; // in seconds

    public SyncQueue(ThreadPool genericThreadPool, int size) {

        this.size = size;
        this.queue = new LinkedList<>();
        this.accessLock = new ReentrantLock();
        this.canRead = accessLock.newCondition();
        this.canWrite = accessLock.newCondition();
        this.genericThreadPool = genericThreadPool;
    }

    public boolean isFull() {
        return this.queue.size() == this.size;
    }

    public boolean isEmpty() {
        return this.queue.size() == 0;
    }

    public void enqueue(SyncQueueNode node) {

        this.accessLock.lock();

        try {

            while (isFull()) {

//                System.out.println("SyncQueue is full.");
                this.canWrite.await(WAIT_TIME, TimeUnit.SECONDS);
            }

            this.queue.addLast(node);
//            System.out.println("SyncQueueNode has been added.");

            this.canRead.signalAll();

        } catch (InterruptedException ex) {
            Logger.getLogger(SyncQueue.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.accessLock.unlock();
        }

//        System.out.println("Queue Size:"+ this.queue.size());
    }

    public SyncQueueNode dequeue() {

        this.accessLock.lock();

        SyncQueueNode node = null;

        try {

            while (isEmpty() && !this.genericThreadPool.isStopped()) {

//                System.out.println("SyncQueue is empty.");
                this.canRead.await(WAIT_TIME, TimeUnit.SECONDS);
//                this.canRead.await();
            }

            if (!this.genericThreadPool.isStopped() && !this.queue.isEmpty()) {

                node = this.queue.removeFirst();
//                System.out.println("SyncQueueNode has been removed.");

                this.canWrite.signalAll();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(SyncQueue.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.accessLock.unlock();
        }

        return node;
    }
}
