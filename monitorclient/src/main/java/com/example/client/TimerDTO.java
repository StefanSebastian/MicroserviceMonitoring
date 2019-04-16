package com.example.client;

/**
 * @author stefansebii@gmail.com
 */
public class TimerDTO {
    private String microserviceName;
    private long timestamp;
    private long pid;
    private String machine;

    private String method;
    private long duration;

    public TimerDTO() {
    }

    public String getMicroserviceName() {
        return microserviceName;
    }

    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "TimerDTO{" +
                "microserviceName='" + microserviceName + '\'' +
                ", timestamp=" + timestamp +
                ", pid=" + pid +
                ", machine='" + machine + '\'' +
                ", method='" + method + '\'' +
                ", duration=" + duration +
                '}';
    }
}
