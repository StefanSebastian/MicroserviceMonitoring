package datastreams.stats.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import datastreams.stats.StatsEngine;
import datastreams.stats.kafkadtos.HeartbeatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class HeartbeatListener {

    @Autowired
    private StatsEngine statsEngine;

    @KafkaListener(topics = "heartbeat", groupId="monitor")
    public void listen(String message) {
        System.out.println("Received message " + message);

        try {
            ObjectMapper mapper = new ObjectMapper();
            HeartbeatDTO heartbeatDTO = mapper.readValue(message, HeartbeatDTO.class);
            statsEngine.addHeartbeat(heartbeatDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
