package datastreams.stats;

import datastreams.stats.dtos.AverageRequestTimes;
import datastreams.stats.dtos.Microservice;
import datastreams.stats.dtos.RequestsPerService;
import datastreams.stats.dtos.SLAStat;
import datastreams.stats.kafkadtos.HeartbeatDTO;
import datastreams.stats.kafkadtos.TimerDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Collections;

/**
 * @author stefansebii@gmail.com
 */
@Service
public class StatsEngine {

	@Value("${stats.heartbeat_timeout}")
    private long heartbeat_timeout = 5000;

    @Value("${stats.monitored_window}")
    private long monitored_window = 60000;

    /**
     * Last heartbeat for each service
     */
    private Map<Long, HeartbeatDTO> heartbeatMap = new HashMap<>();

    private List<TimerDTO> timerList = new LinkedList<>();

    public synchronized void addHeartbeat(HeartbeatDTO heartbeatDTO) {
        heartbeatMap.put(heartbeatDTO.getPid(), heartbeatDTO);
    }

    public synchronized List<Microservice> getOnlineServices() {
        List<Microservice> microservices = new LinkedList<>();
        for (HeartbeatDTO heartbeatDTO : heartbeatMap.values()) {
            if (System.currentTimeMillis() - heartbeatDTO.getTimestamp() <= heartbeat_timeout) {
                microservices.add(new Microservice(heartbeatDTO.getPid(), heartbeatDTO.getMicroserviceName(), heartbeatDTO.getMachine()));
            }
        }
        return microservices;
    }

    private void expireCache(){
        long cutoff = System.currentTimeMillis() - monitored_window;
        timerList.removeIf(t -> t.getTimestamp() < cutoff);
    }

    public synchronized void addTimer(TimerDTO timerDTO) {
        timerList.add(timerDTO);
        expireCache();
    }

    public synchronized List<RequestsPerService> getRequestsPerService() {
        expireCache();
        Map<String, Long> requestsPerServ = timerList.stream()
                .map(TimerDTO::getMicroserviceName)
                .collect(groupingBy(Function.identity(), counting()));
        List<RequestsPerService> requestsPerServices = new LinkedList<>();
        for (String key : requestsPerServ.keySet()) {
            requestsPerServices.add(new RequestsPerService(key, requestsPerServ.get(key)));
        }
        return requestsPerServices;
    }

    public synchronized List<AverageRequestTimes> getAverageRequestTimes() {
        expireCache();
        Map<String, List<Long>> timesPerServ = getTimesPerMicroservice();
        List<AverageRequestTimes> avgReqTimes = new LinkedList<>();
        for (String microserviceName : timesPerServ.keySet()) {
            List<Long> times = timesPerServ.get(microserviceName);
            long sum = times.stream().reduce(0L, Long::sum);
            long avgDuration = sum / times.size();
            avgReqTimes.add(new AverageRequestTimes(microserviceName, avgDuration));
        }
        return avgReqTimes;
    }
    
    public synchronized List<SLAStat> getSlaStats() {
    	expireCache();
    	
    	Map<String, List<Long>> timesPerServ = getTimesPerMicroservice();
    	List<SLAStat> slaStats = new LinkedList<>();
    	for (String microserviceName : timesPerServ.keySet()) {
    		List<Long> times = timesPerServ.get(microserviceName);
    		Collections.sort(times);
    		System.out.println(times);
    		int index = (int)Math.ceil(((double)90 / (double)100) * (double)times.size());
    		slaStats.add(new SLAStat(microserviceName, times.get(index)));
    	}
    	return slaStats;
    }
    
    private Map<String, List<Long>> getTimesPerMicroservice() {
    	Map<String, List<Long>> timesPerServ = new HashMap<>();
        for (TimerDTO timer : timerList) {
            if (timesPerServ.containsKey(timer.getMicroserviceName())) {
                timesPerServ.get(timer.getMicroserviceName()).add(timer.getDuration());
            } else {
                List<Long> timerList = new LinkedList<>();
                timerList.add(timer.getDuration());
                timesPerServ.put(timer.getMicroserviceName(), timerList);

            }
        }
        return timesPerServ;
    }
}
