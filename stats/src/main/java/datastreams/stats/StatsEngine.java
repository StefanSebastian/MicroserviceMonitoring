package datastreams.stats;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author stefansebii@gmail.com
 */
@Service
public class StatsEngine {

    private static final long expiration = 5000;

    /**
     * Last heartbeat for each service
     */
    private static Map<Long, HeartbeatDTO> heartbeatMap = new HashMap<>();

    public synchronized void addHeartbeat(HeartbeatDTO heartbeatDTO) {
        heartbeatMap.put(heartbeatDTO.getPid(), heartbeatDTO);
    }

    public synchronized List<Microservice> getOnlineServices() {
        List<Microservice> microservices = new LinkedList<>();
        for (HeartbeatDTO heartbeatDTO : heartbeatMap.values()) {
            if (System.currentTimeMillis() - heartbeatDTO.getTimestamp() <= expiration) {
                microservices.add(new Microservice(heartbeatDTO.getPid(), heartbeatDTO.getMicroserviceName(), heartbeatDTO.getMachine()));
            }
        }
        return microservices;
    }
}
