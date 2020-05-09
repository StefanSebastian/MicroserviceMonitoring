package datastream.stats.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import datastream.stats.model.Heartbeat;

@Repository
public interface HeartbeatRepository extends CrudRepository<Heartbeat, Long>{
	
}
