/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.parser;

import java.util.ArrayList;
import jobscraper.common.Scraper;
import jobscraper.datastore.DatabaseMongo;
import jobscraper.job.entity.Job;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author tushar
 */
public class DiceParser implements JobScraperInterface {

    private static Logger logger = LogManager.getLogger(DiceParser.class);

    Job getJobDetails(Document doc) {
        Job job = new Job();

        try {

            String jobTitle = doc.select("h1.jobTitle").text();
            String location = doc.select("ul.list-inline > li.location").text();
            String jobDetils = doc.select("div#jobdescSec").text();

            job.setJobTitle(jobTitle);
            job.setJobLocation(location);
            job.setJobDetails(jobDetils);

        } catch (Exception e) {
            logger.error(e);
        }

        return job;
    }

    ArrayList<String> getJobUrls(Document doc) {
        ArrayList<String> jobUrls = new ArrayList<>();
        try {
            Elements jobUrlElements = doc.select(".complete-serp-result-div").select("li>h3>a");

            for (Element e : jobUrlElements) {
                String jobUrl = e.attr("abs:href");
                jobUrls.add(jobUrl);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return jobUrls;
    }

    public static void main(String[] args) {
        DiceParser diceParser = new DiceParser();
        Document doc = Scraper.Scrap("https://www.dice.com/jobs?q=&l=&searchid=791646907487&stst=");
        ArrayList<String> jobUrls = diceParser.getJobUrls(doc);

        Datastore datastore = DatabaseMongo.getDatastore();
        for (String s : jobUrls) {
            System.out.println(s);
            doc = Scraper.Scrap(s);

            Job job = diceParser.getJobDetails(doc);
            job.setJobUrl(s);
            datastore.save(job);
            System.out.println(job);
            break;
        }

    }

    @Override
    public Job scrap(Document document, String url) {
        Job job = new Job();
        try {
            job = getJobDetails(document);
            job.setJobUrl(url);

        } catch (Exception e) {
            logger.error(e);
            return null;
        }

        return job;
    }

    @Override
    public ArrayList<String> scrapJobUrls(String jobListUrl) {
        try {
            Document doc = Scraper.Scrap(jobListUrl);
            return getJobUrls(doc);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

}
