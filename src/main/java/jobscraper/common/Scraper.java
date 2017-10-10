/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.common;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author tushar
 */
public class Scraper {

    private static Logger log = LogManager.getLogger(Scraper.class);

    public static Document Scrap(String url) {

        Document document = null;
        int retry = 0;

        while (retry < Configuration.MAX_RETRY) {
            retry++;
            log.debug("Trying : " + retry + " Fetching Content for Url: " + url);
            
            try {
                document = Jsoup.connect(url).userAgent(Configuration.USER_AGENT).timeout(Configuration.CONNECTION_TIME_OUT).get();
                if (document != null) {
                    log.debug("Scraped Content for Url" + url);
                    break;
                } else {
                    try {
                        log.debug("Content is not scraped trying agin after " + Configuration.RETRY_DELAY_TIME + " milisec");
                        Thread.sleep(Configuration.RETRY_DELAY_TIME);
                    } catch (InterruptedException ex) {
                        log.error(ex);
                    }
                }
            } catch (IOException ex) {
                log.error(ex);
            }
        }
        return document;
    }
}