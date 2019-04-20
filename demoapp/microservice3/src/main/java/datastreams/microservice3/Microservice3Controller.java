package datastreams.microservice3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author stefansebii@gmail.com
 */
@RestController
public class Microservice3Controller {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Random random = new Random();

    @RequestMapping("/method1")
    public String method1(@RequestParam(value = "duration", required = false) Long duration) {
        if (duration == null) {
            duration = 100L + random.nextInt(1000);
        }
        logger.info("Method 1 " + duration);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "method1";
    }

    @RequestMapping("/method2")
    public String method2(@RequestParam(value = "duration", required = false) Long duration) {
        if (duration == null) {
            duration = 100L + random.nextInt(1000);
        }
        logger.info("Method 2 " + duration);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "method2";
    }

    @RequestMapping("/method3")
    public String method3(@RequestParam(value = "duration", required = false) Long duration) {
        if (duration == null) {
            duration = 100L + random.nextInt(1000);
        }
        logger.info("Method 3 " + duration);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "method3";
    }
}
