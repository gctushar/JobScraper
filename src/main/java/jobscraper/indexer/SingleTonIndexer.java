/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.indexer;

/**
 *
 * @author tushar
 */
public class SingleTonIndexer {

    private static SingleTonIndexer SINGLE_TON_INDEXER = null;
    private static JobIndexer indexer = null;

    private SingleTonIndexer() {
        indexer = new JobIndexer();
    }

    public static synchronized SingleTonIndexer getInstance() {

        if (SINGLE_TON_INDEXER == null) {
            SINGLE_TON_INDEXER = new SingleTonIndexer();
        }

        return SINGLE_TON_INDEXER;
    }

    public static JobIndexer getIndexer() {
        return indexer;
    }
}
