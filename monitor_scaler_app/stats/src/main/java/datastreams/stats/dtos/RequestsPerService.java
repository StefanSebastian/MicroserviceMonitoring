package datastreams.stats.dtos;

/**
 * @author stefansebii@gmail.com
 */
public class RequestsPerService {
    private String serviceName;
    private Long nrRequests;

    public RequestsPerService() {
    }

    public RequestsPerService(String serviceName, Long nrRequests) {
        this.serviceName = serviceName;
        this.nrRequests = nrRequests;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getNrRequests() {
        return nrRequests;
    }

    public void setNrRequests(Long nrRequests) {
        this.nrRequests = nrRequests;
    }
}
