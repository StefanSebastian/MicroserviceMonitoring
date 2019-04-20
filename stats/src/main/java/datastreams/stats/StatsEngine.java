package datastreams.stats;

import datastreams.stats.dtos.Microservice;
import datastreams.stats.dtos.RequestsPerService;
import datastreams.stats.kafkadtos.HeartbeatDTO;
import datastreams.stats.kafkadtos.TimerDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author stefansebii@gmail.com
 */
@Service
public class StatsEngine {

    private static final long heartbeat_timeout = 5000;

    private static final long cache_expiration = 60000;

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
        long cutoff = System.currentTimeMillis() - cache_expiration;
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
}
