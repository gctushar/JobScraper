/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.job.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Indexes;

/**
 *
 * @author tushar
 */

@Entity("job")
@Indexes(@Index(value = "jobUrl", fields = @Field("jobUrl")))
public class Job {

    private String jobTitle;
    @Id
    @Indexed( unique=true , dropDups=true)
    private String jobUrl;
    private String jobLocation;
    private String jobDetails;
    

    public Job() {
        this.jobTitle = "";
        this.jobUrl = "";
        this.jobLocation = "";
        this.jobDetails = "";
    }

    @Override
    public String toString() {
        return "Job{" + "jobTitle=" + jobTitle + ", jobUrl=" + jobUrl + ", jobLocation=" + jobLocation + ", jobDetails=" + jobDetails + '}';
    }

    public boolean isValid() {
        if (jobTitle == null || jobTitle.isEmpty() || jobUrl == null || jobUrl.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Get the value of jobDetails
     *
     * @return the value of jobDetails
     */
    public String getJobDetails() {
        return jobDetails;
    }

    /**
     * Set the value of jobDetails
     *
     * @param jobDetails new value of jobDetails
     */
    public void setJobDetails(String jobDetails) {
        this.jobDetails = jobDetails;
    }

    /**
     * Get the value of jobLocation
     *
     * @return the value of jobLocation
     */
    public String getJobLocation() {
        return jobLocation;
    }

    /**
     * Set the value of jobLocation
     *
     * @param jobLocation new value of jobLocation
     */
    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    /**
     * Get the value of jobUrl
     *
     * @return the value of jobUrl
     */
    public String getJobUrl() {
        return jobUrl;
    }

    /**
     * Set the value of jobUrl
     *
     * @param jobUrl new value of jobUrl
     */
    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    /**
     * Get the value of jobTitle
     *
     * @return the value of jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Set the value of jobTitle
     *
     * @param jobTitle new value of jobTitle
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
