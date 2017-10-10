/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tushar
 */
public class Configuration {

    //Configuration properties
    private static Properties PROPERTIES = null;
    //Configuration file directory
    public static final String CONFIG_FILE_LOCATION = "config/Configuration.properties";

    //Jsoup Scraper Configuration
    public static final String USER_AGENT = Configuration.getConfiguration("USER_AGENT");
    public static final int CONNECTION_TIME_OUT = Integer.parseInt(Configuration.getConfiguration("CONNECTION_TIME_OUT")); // in milli secs6
    public static final int RETRY_DELAY_TIME = Integer.parseInt(Configuration.getConfiguration("RETRY_DELAY_TIME"));
    public static final int MAX_RETRY = Integer.parseInt(Configuration.getConfiguration("MAX_RETRY"));

    // Job Site Thread pool
    public static int CRAWLER_THREAD_POOL_SIZE = Integer.parseInt(Configuration.getConfiguration("CRAWLER_THREAD_POOL_SIZE"));
    public static int CRALWER_THREAD_QUEUE_SIZE = Integer.parseInt(Configuration.getConfiguration("CRALWER_THREAD_QUEUE_SIZE"));

    // Per job site thread pool 
    public static int THREAD_POOL_FOR_PER_SITE = Integer.parseInt(Configuration.getConfiguration("THREAD_POOL_FOR_PER_SITE"));
    public static int HOST_FETCHER_QUEUE_SIZE_PER_SITE = Integer.parseInt(Configuration.getConfiguration("HOST_FETCHER_QUEUE_SIZE_PER_SITE"));

    //Scheduler 
    public static int JOB_SCRAPER_SCHEDULE_TIME = Integer.parseInt(Configuration.getConfiguration("JOB_SCRAPER_SCHEDULE_TIME"));
    public static int JOB_EMAIL_NOTIFICATION_SCHEDULE_TIME = Integer.parseInt(Configuration.getConfiguration("JOB_EMAIL_NOTIFICATION_SCHEDULE_TIME"));

    //Email Notification Configuration
    public static final String EMAIL_SENDER_ADDRESS = Configuration.getConfiguration("EMAIL_SENDER_ADDRESS");
    public static final String EMAIL_SENDER_PASSWORD = Configuration.getConfiguration("EMAIL_SENDER_PASSWORD");
    public static final String EMAIL_RECEIVERS = Configuration.getConfiguration("EMAIL_RECEIVERS");

    // Resource Location configuration
    public static final String MAIN_RESOURCE_LOCATION = Configuration.getConfiguration("MAIN_RESOURCE_LOCATION");
    public static final String READER_RESOURCE_LOCATION = Configuration.getConfiguration("READER_RESOURCE_LOCATION");
    public static final String WRITTER_RESOURCE_LOCATION = Configuration.getConfiguration("WRITTER_RESOURCE_LOCATION");

    //MongoDb Configuration
    public static final boolean SHOULD_DATABASE_INSERT = Boolean.parseBoolean(Configuration.getConfiguration("SHOULD_DATABASE_INSERT"));
    public static final String DATABASE_NAME = Configuration.getConfiguration("DATABASE_NAME");
    //Solr Configuration
    public static final boolean SHOULD_INDEX = Boolean.parseBoolean(Configuration.getConfiguration("SHOULD_INDEX"));
    public static String SOLR_SERVER_URL = Configuration.getConfiguration("SOLR_SERVER_URL");

    public static String getConfiguration(String configName) {

        if (PROPERTIES == null) {

            synchronized (Configuration.class) {
                if (PROPERTIES == null) {

                    try {
                        PROPERTIES = Configuration.getConfiguration();
                    } catch (IOException ex) {
                        Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        return PROPERTIES.getProperty(configName);
    }

    private static Properties getConfiguration() throws FileNotFoundException, IOException {

        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(Configuration.CONFIG_FILE_LOCATION);

        if (inputStream == null) {
            throw new FileNotFoundException("Error loading Configuration.properties file.");
        }

        InputStreamReader inputStreamReader;
        inputStreamReader = new InputStreamReader(inputStream);

        properties.load(inputStreamReader);

        return properties;
    }

}
