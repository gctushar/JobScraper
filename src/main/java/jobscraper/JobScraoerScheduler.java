package jobscraper;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import jobscraper.common.Configuration;
import org.apache.log4j.Logger;

/**
 *
 * @author tushar
 */

public class JobScraoerScheduler {

    Logger log = Logger.getLogger(JobScraoerScheduler.class.getName());

    public void scheduler() {
        //PropertyConfigurator.configure("src/recentnewsarchiver/log4j.properties");
        //Displaying current time
        log.debug("Time now is -> " + new Date());
        Timer timer = new Timer();
        timer.schedule(new taskSchedule(), 0, Configuration.JOB_SCRAPER_SCHEDULE_TIME);
        timer.schedule(new mailNotificationSchedule(), Configuration.JOB_EMAIL_NOTIFICATION_SCHEDULE_TIME, Configuration.JOB_EMAIL_NOTIFICATION_SCHEDULE_TIME); //add 86400000
    }
}

class taskSchedule extends TimerTask {

    public void run() {
        scheduleExecutor();
    }

    public void scheduleExecutor() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable() {
            public Object call() throws Exception {

                JobScraper jobScraper = new JobScraper();

                return "Called!";
            }
        },
                5,
                TimeUnit.SECONDS);
    }
}

class mailNotificationSchedule extends TimerTask {

    public void run() {
           
    }
}
