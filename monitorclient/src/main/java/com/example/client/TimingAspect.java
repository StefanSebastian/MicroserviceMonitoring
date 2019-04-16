package com.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author stefansebii@gmail.com
 */
@Aspect
@Component
public class TimingAspect {

    @Value("${kafka.timer.topic.name}")
    private String topicName;

    @Value("${spring.application.name}")
    private String appName;

    private long pid;
    private String machine;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public TimingAspect(){
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

        String jvmName = bean.getName();
        pid = Long.valueOf(jvmName.split("@")[0]);
        machine = jvmName.split("@")[1];
    }

    @Around("@annotation(requestMapping)")
    public Object measureExecutionTime(ProceedingJoinPoint thisJoinPoint, RequestMapping requestMapping) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = thisJoinPoint.proceed();
        long timestamp = System.currentTimeMillis();

        TimerDTO timerDTO = new TimerDTO();
        timerDTO.setMachine(machine);
        timerDTO.setPid(pid);
        timerDTO.setMicroserviceName(appName);
        timerDTO.setTimestamp(timestamp);
        timerDTO.setMethod(thisJoinPoint.getSignature().getName());
        timerDTO.setDuration(timestamp - startTime);

        System.out.println(timerDTO);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(timerDTO);
            kafkaTemplate.send(topicName, jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
