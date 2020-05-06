package datastreams.microservice2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import datastreams.microservice2.WorkloadSimulator;

import java.util.Random;

/**
 * @author stefansebii@gmail.com
 */
@RestController
public class Microservice2Controller {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Random random = new Random();
    
    @Autowired
    private WorkloadSimulator workloadSimulator;

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
    public String method2(@RequestParam(value = "n", required = false) Integer n) {
        if (n == null) {
            n = 20; // a short computation time by default
        }
        logger.info("Method 2, with workload param " + n);
        long res = workloadSimulator.fibonacci(n);
        
        return "method2:" + res;
    }
}
