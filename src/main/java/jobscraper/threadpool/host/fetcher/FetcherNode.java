/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.threadpool.host.fetcher;


import genericthreadpool.SyncQueueNode;
import jobscraper.parser.JobScraperInterface;

/**
 *
 * @author tushar
 */
public class FetcherNode extends SyncQueueNode {

    private boolean crawlStatus;
    private JobScraperInterface jobScraper;
    private String domain;
    private String jobUrl;
    private int maxRetry;
    private int maxRetryTime;

    public FetcherNode() {
        super();
        crawlStatus = false;
        domain = "";
        jobUrl = "";
        maxRetry = 1;
        maxRetryTime = 1000;
    }

    public boolean isCrawlStatus() {
        return crawlStatus;
    }

    public void setCrawlStatus(boolean crawlStatus) {
        this.crawlStatus = crawlStatus;
    }

    public JobScraperInterface getJobParser() {
        return jobScraper;
    }

    public void setJobParser(JobScraperInterface jobParser) {
        this.jobScraper = jobParser;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getMaxRetryTime() {
        return maxRetryTime;
    }

    public void setMaxRetryTime(int maxRetryTime) {
        this.maxRetryTime = maxRetryTime;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

}
