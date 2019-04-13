package datastreams.microservice3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stefansebii@gmail.com
 */
@RestController
public class Microservice3Controller {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/method1")
    public String method1() {
        logger.info("Method 1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "method1";
    }

    @RequestMapping("/method2")
    public String method2() {
        logger.info("Method 2");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "method2";
    }

    @RequestMapping("/method3")
    public String method3() {
        logger.info("Method 3");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "method3";
    }
}
