package max.malakhov.home.cartracker.service.collector;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Max Malakhov <malakhovbox@gmail.com>
 * @version 0.1
 * @since 2013-02-17
 */

public class Collector implements Job {

    private Crawler crawler;
    private Parser parser;

    private static Collector collector;

    public static Collector getInstance() {
        if (collector == null) {
            collector = new Collector();
        }
        return collector;
    }



    private Collector() {
        crawler = new Crawler();
        parser = new Parser();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        perform();
    }

    public void perform() {

        StringBuffer rawData = crawler.getRawData();
        parser.getCars(rawData.toString());

    }

    public void schedule() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail job = newJob(Collector.class)
                    .withIdentity("job1", "collector")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "collector")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(20)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
