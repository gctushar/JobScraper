/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.parser;

import java.util.ArrayList;
import jobscraper.job.entity.Job;
import org.jsoup.nodes.Document;

/**
 *
 * @author tushar
 */
public interface JobScraperInterface {
    
    public Job scrap(Document document,String url);
    public ArrayList<String> scrapJobUrls(String jobListUrl);
    
    
}
