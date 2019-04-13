package datastreams.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stefansebii@gmail.com
 */
@Component
@EnableScheduling
@ComponentScan(basePackages = "datastreams")
public class Heartbeat {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Heartbeat() {
        log.info("Starting heartbeat service");
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 2000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
