/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericthreadpool;

import java.util.concurrent.locks.Condition;

/**
 *
 * @author tushar
 */
public abstract class WorkerThread extends Thread {

    private ThreadPool genericThreadPool;
    private Condition canStartWorkerThread;
    private int count = 0;

    public WorkerThread() {
        this.genericThreadPool = null;
    }

    public void setThreadPool(ThreadPool genericThreadPool) {
        this.genericThreadPool = genericThreadPool;
    }

    public void run() {

        System.out.println("Started Thread: " + this.genericThreadPool.identifier + " ID: " + this.hashCode());

        while (!genericThreadPool.isStopped()) {

//            System.out.println("Processing New Node Thread: " + this.genericThreadPool.identifier + " ID: " + this.hashCode() + " Time: " + Calendar.getInstance().getTime().toString());

            SyncQueueNode syncQueueNode = this.genericThreadPool.getNode();

            if (syncQueueNode != null) {

                this.process(syncQueueNode);
                this.genericThreadPool.nodeProcessed(syncQueueNode);

//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
//                }
                count++;
            }

//            System.out.println("Finished Processing New Node Thread: " + this.genericThreadPool.identifier + " ID: " + this.hashCode());
        }

        System.out.println("Stopped Thread: " + this.genericThreadPool.identifier + " ID: " + this.hashCode() + "Total Node: " + count);
    }

    public abstract void process(SyncQueueNode syncQueueNode);

}
