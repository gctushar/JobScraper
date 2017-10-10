/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.parser;

import java.util.ArrayList;
import jobscraper.common.Scraper;
import jobscraper.job.entity.Job;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author tushar
 */
public class MonstarParser implements JobScraperInterface{

    ArrayList<String> getJobUrls(Document doc) {
        ArrayList<String> jobUrls = new ArrayList<>();
        try {

            Elements jobUrlElements = doc.select(".js_result_details >.js_result_details-left > .jobTitle").select("a");

            for (Element e : jobUrlElements) {

                String jobUrl = e.attr("abs:href");
                System.out.println(e.text());
                System.out.println(jobUrl);
                jobUrls.add(jobUrl);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return jobUrls;
    }
    
    public static void main(String[] args) {
        MonstarParser monstarParser = new MonstarParser();
        Document doc = Scraper.Scrap("https://www.monster.com/jobs/search/?");
        ArrayList<String> jobUrls = monstarParser.getJobUrls(doc);
        
        for(String s: jobUrls){
            System.out.println(s);
        }
        
    }

    @Override
    public Job scrap(Document document, String url) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> scrapJobUrls(String jobListUrl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
