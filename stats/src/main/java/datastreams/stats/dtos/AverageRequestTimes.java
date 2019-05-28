package datastreams.stats.dtos;

/**
 * @author stefansebii@gmail.com
 */
public class AverageRequestTimes {
    private String service;
    private long duration;

    public AverageRequestTimes(String service, long duration) {
        this.service = service;
        this.duration = duration;
    }

    public AverageRequestTimes() {
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
