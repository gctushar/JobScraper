package jobscraper.threadpool.host;

import genericthreadpool.SyncQueueNode;
import genericthreadpool.ThreadPool;
import genericthreadpool.ThreadPoolListener;
import genericthreadpool.WorkerThread;
import java.util.ArrayList;
import jobscraper.common.Configuration;
import jobscraper.common.Utils;
import jobscraper.parser.JobScraperFactory;
import jobscraper.parser.JobScraperInterface;
import org.apache.log4j.LogManager;
import jobscraper.threadpool.host.fetcher.FetcherNode;
import jobscraper.threadpool.host.fetcher.FetcherWorker;
import org.apache.log4j.Logger;

/**
 *
 * @author tushar
 */
public class CrawlerWorker extends WorkerThread implements ThreadPoolListener {

    JobScraperFactory jobScraperFactory;
    private static Logger logger = LogManager.getLogger(CrawlerWorker.class);

    public CrawlerWorker() {
        super();
        jobScraperFactory = new JobScraperFactory();
    }

    @Override
    public void process(SyncQueueNode syncQueueNode) {

        try {
            CrawlerNode crawlerNode = (CrawlerNode) syncQueueNode;
            ThreadPool hostFetcherPool = new ThreadPool(Configuration.THREAD_POOL_FOR_PER_SITE, Configuration.HOST_FETCHER_QUEUE_SIZE_PER_SITE, "Host Fetcher");
            hostFetcherPool.setWorkerThreadClass(FetcherWorker.class);
            hostFetcherPool.setThreadPoolListener(this);
            hostFetcherPool.start();

            String domainName = crawlerNode.getDomain();
            String jobListUrl = crawlerNode.getJobListUrl();
            domainName = Utils.getDomainName(domainName);

            JobScraperInterface jobParser = jobScraperFactory.getJobScraper(domainName);

            if (jobParser != null) {
                logger.debug("Job Url scraping from Domain : " + domainName);

                ArrayList<String> jobUrls = jobParser.scrapJobUrls(jobListUrl);

                logger.debug("Found total jobs : " + jobUrls.size() + " from " + jobListUrl);

                for (String jobUrl : jobUrls) {

                    jobUrl = jobUrl.trim();
                    if (jobUrl != null && !jobUrl.isEmpty()) {

                        FetcherNode fetcherNode = new FetcherNode();
                        fetcherNode.setDomain(domainName);
                        fetcherNode.setJobUrl(jobUrl);
                        fetcherNode.setJobParser(jobParser);
                        fetcherNode.setMaxRetry(crawlerNode.getMaxRetry());
                        fetcherNode.setMaxRetryTime(crawlerNode.getMaxRetryTime());
                        hostFetcherPool.addNode(fetcherNode);
                    } else {
                        logger.error("Empty or error job url on domain: " + domainName);
                    }
                }
                jobUrls = null;
                logger.debug("Job Urls generated for Domain : " + domainName);
            } else {
                logger.error("Job Scraper is Not Found for Domain: " + domainName);
            }
            hostFetcherPool.stopPool();

        } catch (InstantiationException ex) {
            logger.error("Exception", ex);
        } catch (IllegalAccessException ex) {
            logger.error("Exception", ex);
        } catch (Exception ex) {
            logger.error("Exception", ex);
        }
    }

    @Override
    public void initWorkerThread(WorkerThread workerThread) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nodeProcessed(SyncQueueNode syncQueueNode) {

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
