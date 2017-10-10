/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.indexer;

import java.io.IOException;
import jobscraper.common.Configuration;
import jobscraper.common.Utils;
import jobscraper.job.entity.Job;
import org.apache.log4j.LogManager;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author tushar
 */

public class JobIndexer {

    private HttpSolrClient solrIndexer;
    private static org.apache.log4j.Logger logger = LogManager.getLogger(JobIndexer.class);

    public JobIndexer() {
        this.solrIndexer = new HttpSolrClient.Builder(Configuration.SOLR_SERVER_URL).build();
    }

    public boolean index(Job job) {

        logger.info("Now Indexing  Job");
        try {

            SolrInputDocument doc = new SolrInputDocument();
            String domain;

            domain = Utils.getDomainName(job.getJobUrl());

            doc.setField("job_title", job.getJobTitle());

            if (job.getJobLocation() != null && !job.getJobLocation().isEmpty()) {
                doc.setField("location", job.getJobLocation());
            }

            if (job.getJobDetails() != null && !job.getJobDetails().isEmpty()) {
                doc.setField("job_details", job.getJobDetails());
            }

            doc.setField("job_url", job.getJobUrl());

            doc.setField("domain", domain);

            UpdateResponse response = this.solrIndexer.add(doc);

            return true;

        } catch (NullPointerException | IOException | SolrServerException ex) {
            logger.error(ex);
        }

        return false;
    }
}
