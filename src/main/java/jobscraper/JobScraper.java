package jobscraper;

import genericthreadpool.ThreadPool;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import jobscraper.common.Configuration;
import jobscraper.common.Utils;
import jobscraper.threadpool.host.CrawlerNode;
import jobscraper.threadpool.host.CrawlerWorker;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author tushar
 */
public class JobScraper {

    private static Logger logger = LogManager.getLogger(JobScraper.class);
    private ThreadPool scraper;

    public JobScraper() {

        scraper = new ThreadPool(Configuration.CRAWLER_THREAD_POOL_SIZE, Configuration.CRALWER_THREAD_QUEUE_SIZE, "Host Crawler");
        scraper.setWorkerThreadClass(CrawlerWorker.class);

        try {

            scraper.start();
            logger.debug("\n....................\nCrawling Start\n....................\n");

            File jobSeedFile = new File(Configuration.READER_RESOURCE_LOCATION + "/seed.txt");

            if (jobSeedFile.exists()) {

                logger.debug("Job Url from File Reading......");
                FileReader fileReader = new FileReader(jobSeedFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                int jobSeedFileLineNumber = 0;

                while (bufferedReader.ready()) {

                    jobSeedFileLineNumber++;
                    String seedDetails[] = bufferedReader.readLine().split("~");
                    if (seedDetails.length >= 1 && seedDetails.length <= 4) {

                        String domainName = seedDetails[0].trim();
                        domainName = Utils.getDomainName(domainName);
                        String jobListUrl = seedDetails[1].trim();
                        int maxRetry = 1;
                        int maxRetryTime = 5000;
                        if (seedDetails.length > 4) {

                            try {
                                maxRetry = Integer.parseInt(seedDetails[2].trim());
                                maxRetryTime = Integer.parseInt(seedDetails[3].trim());
                            } catch (Exception e) {
                                logger.error(e);
                                maxRetry = 1;
                                maxRetryTime = 5000;
                            }
                        }
                        if (domainName == null || domainName.isEmpty()) {
                            logger.error("Error for domain name in seed file line: " + jobSeedFileLineNumber);
                            continue;
                        }

                        CrawlerNode crawlerNode = new CrawlerNode();
                        crawlerNode.setDomain(domainName);
                        crawlerNode.setJobListUrl(jobListUrl);
                        crawlerNode.setMaxRetry(maxRetry);
                        crawlerNode.setMaxRetryTime(maxRetryTime);
                        scraper.addNode(crawlerNode);
                        logger.debug("domainName " + domainName + "\nmaxRetry : " + maxRetry + "\nMaxRetryTime : " + maxRetryTime + "\nListUrl : " + jobListUrl);

                    } else {
                        logger.error("Error on seed file line:" + jobSeedFileLineNumber);
                    }
                }
                logger.debug("Job Url from File Reading completed !");

            } else {
                logger.error("Unable to read Job Url from File");            }

            scraper.stopPool();
            logger.debug("\n....................\nCrawling Stopped\n....................\n");
        } catch (InstantiationException ex) {
            logger.error(ex);
        } catch (IllegalAccessException ex) {
            logger.error(ex);
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JobScraper jobScraper = new JobScraper();

    }

}
