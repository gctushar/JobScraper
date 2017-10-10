/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.threadpool.host.fetcher;

import genericthreadpool.SyncQueueNode;
import genericthreadpool.WorkerThread;
import jobscraper.common.Configuration;
import jobscraper.common.Scraper;
import jobscraper.datastore.DatabaseMongo;
import jobscraper.indexer.JobIndexer;
import jobscraper.indexer.SingleTonIndexer;
import jobscraper.job.entity.Job;
import jobscraper.parser.JobScraperFactory;
import jobscraper.parser.JobScraperInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author tushar
 */
public class FetcherWorker extends WorkerThread {

    private static Logger logger = LogManager.getLogger(FetcherWorker.class);
    private static Datastore datastore = DatabaseMongo.getDatastore();
    private JobIndexer indexer;

    public FetcherWorker() {
        super();
        indexer = SingleTonIndexer.getInstance().getIndexer();
    }

    @Override
    public void process(SyncQueueNode syncQueueNode) {

        FetcherNode fetcherNode = (FetcherNode) syncQueueNode;

        logger.debug("Fetching: " + fetcherNode.getJobUrl());

        try {
            JobScraperInterface jobScraper = fetcherNode.getJobParser();
            String jobUrl = fetcherNode.getJobUrl();

            if (jobScraper == null) {
                JobScraperFactory jobParserFactory = new JobScraperFactory();
                jobScraper = jobParserFactory.getJobScraper(fetcherNode.getDomain());
            }

            if (jobUrl != null && !jobUrl.isEmpty()) {

                logger.debug("Job scraping started form Url: " + jobUrl);
                Document doc = null;
                Job job = new Job();
                int count = 0;

                while (count < fetcherNode.getMaxRetry()) {

                    count++;
                    doc = Scraper.Scrap(jobUrl);

                    if (doc == null) {

                        try {
                            logger.debug("Empty Job List Trying Agin after : " + (fetcherNode.getMaxRetryTime() * count) + " Miliseond , Try Number: " + count);
                            Thread.sleep(fetcherNode.getMaxRetryTime() * count);
                        } catch (Exception e) {
                            logger.error(e);
                        }
                    } else {
                        job = jobScraper.scrap(doc, jobUrl);

                        if (job != null && job.isValid()) {
                            if (Configuration.SHOULD_INDEX) {
                                indexer.index(job);
                            }
                            if (Configuration.SHOULD_DATABASE_INSERT) {
                                datastore.save(job);
                            }
                        }
                        doc = null;
                        break;
                    }
                }
                doc = null;
                job = null;

            } else {
                logger.error("Empty Job  Domain: " + jobUrl);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
