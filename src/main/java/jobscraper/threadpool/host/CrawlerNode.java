package jobscraper.threadpool.host;


import genericthreadpool.SyncQueueNode;

/**
 *
 * @author tushar
 */
public class CrawlerNode extends SyncQueueNode {

    private String domainName;
    private String jobListUrl;
    private int maxRetry;
    private int maxRetryDelayTime;

    public CrawlerNode() {
        super();
    }

    public String getDomain() {
        return domainName;
    }

    public void setDomain(String domain) {
        this.domainName = domain;
    }

    public String getJobListUrl() {
        return jobListUrl;
    }

    public void setJobListUrl(String jobListUrl) {
        this.jobListUrl = jobListUrl;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public int getMaxRetryTime() {
        return maxRetryDelayTime;
    }

    public void setMaxRetryTime(int maxRetryTime) {
        this.maxRetryDelayTime = maxRetryTime;
    }

}
