package datastreams.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author stefansebii@gmail.com
 */
@Controller
public class StatsController {

    @Autowired
    private StatsEngine statsEngine;

    @RequestMapping("/onlineservices")
    public @ResponseBody  List<Microservice> getOnlineServices() {
        return statsEngine.getOnlineServices();
    }
}
