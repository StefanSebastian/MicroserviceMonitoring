package datastreams.stats;

import datastreams.stats.dtos.AverageRequestTimes;
import datastreams.stats.dtos.Microservice;
import datastreams.stats.dtos.RequestsPerService;
import datastreams.stats.dtos.SLAStat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author stefansebii@gmail.com
 */
@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class StatsController {

    @Autowired
    private StatsEngine statsEngine;

    @RequestMapping("/onlineservices")
    public @ResponseBody  List<Microservice> getOnlineServices() {
        return statsEngine.getOnlineServices();
    }

    @RequestMapping("/nrrequests")
    public @ResponseBody List<RequestsPerService> getNrRequestsPerService() {
        return statsEngine.getRequestsPerService();
    }

    @RequestMapping("/avgtimes")
    public @ResponseBody List<AverageRequestTimes> getAverageRequestTimes() {
        return statsEngine.getAverageRequestTimes();
    }
    
    @RequestMapping("/slastats")
    public @ResponseBody List<SLAStat> getSlaStats() {
    	return statsEngine.getSlaStats();
    }
}
