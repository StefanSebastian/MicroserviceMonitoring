package datastream.stats.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import datastream.stats.model.RequestLog;

@Repository
public interface RequestLogRepository extends CrudRepository<RequestLog, Long>{

}
