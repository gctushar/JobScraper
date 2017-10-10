/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericthreadpool;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tushar
 */
public class ThreadPool {

    private SyncQueue syncQueue;
    private boolean isStopped;
    private ArrayList<WorkerThread> workerThreads;
    private Class<?> workerThreadClass;
    private int totalWorkerThread;
    private ThreadPoolListener threadPoolListener;
    public String identifier;

    public ThreadPool(int totalWorkerThread, int queueSize, String identifier) {

        this.isStopped = false;
        this.workerThreads = new ArrayList<>();
        this.syncQueue = new SyncQueue(this, queueSize);
        this.workerThreadClass = null;
        this.totalWorkerThread = totalWorkerThread;
        this.threadPoolListener = null;
        this.identifier = identifier;
    }

    public void setThreadPoolListener(ThreadPoolListener listener) {
        this.threadPoolListener = listener;
    }

    private void addWorkerTheard(WorkerThread workerThread) {

        workerThread.setThreadPool(this);
        this.workerThreads.add(workerThread);
    }

    public void start() throws InstantiationException, IllegalAccessException {

        int count;

        for (count = 0; count < this.totalWorkerThread; count++) {

            WorkerThread workerThread = (WorkerThread) workerThreadClass.newInstance();
            this.addWorkerTheard(workerThread);

            if (this.threadPoolListener != null) {
                this.threadPoolListener.initWorkerThread(workerThread);
            }
        }

        System.out.println("Thead pool starting...");

        count = 0;

        for (WorkerThread workerThread : workerThreads) {
            workerThread.start();
        }

        System.out.println("Thead pool started");
    }

    public SyncQueueNode getNode() {
        return this.syncQueue.dequeue();
    }

    public void addNode(SyncQueueNode syncQueueNode) {
        this.syncQueue.enqueue(syncQueueNode);
    }

    public boolean isStopped() {
        return isStopped & this.syncQueue.isEmpty();
    }

    public void stopPool() {

        System.out.println("Thread pool stopping...");

        this.isStopped = true;

        for (WorkerThread workerThread : workerThreads) {
            try {
                workerThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("Thread pool stopped");
    }

    public void setWorkerThreadClass(Class<?> aClass) {
        this.workerThreadClass = aClass;
    }

    public void nodeProcessed(SyncQueueNode syncQueueNode) {

        if (this.threadPoolListener != null) {
            this.threadPoolListener.nodeProcessed(syncQueueNode);
        }
    }

}
