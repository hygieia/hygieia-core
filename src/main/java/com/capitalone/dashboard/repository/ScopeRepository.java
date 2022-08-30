package com.capitalone.dashboard.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.Scope;

/**
 * Repository for {@link Scope}.
 */
public interface ScopeRepository extends CrudRepository<Scope, ObjectId>,
		QuerydslPredicateExecutor<Scope> {
	/**
	 * This essentially returns the max change date from the collection, based
	 * on the last change date (or default delta change date property) available
	 * 
	 * @param collectorId
	 *            Collector ID of source system collector
	 * @param changeDate
	 *            Last available change date or delta begin date property
	 * @return A single Change Date value that is the maximum value of the
	 *         existing collection
	 */
	@Query
	List<Scope> findTopByCollectorIdAndChangeDateGreaterThanOrderByChangeDateDesc(
			ObjectId collectorId, String changeDate);

	@Query(value = "{'pId' : ?0}", fields="{'pId' : 1}")
	List<Scope> getScopeIdById(String pId);

	@Query
	List<Scope> findByOrderByProjectPathDesc();

	@Query(value = "{'pId' : ?0 }")
	List<Scope> getScopeById(String pId);

	@Query(value = "{ 'collectorId' : ?0 }")
	List<Scope> findByCollectorId(ObjectId collectorId);

	Page<Scope> findAllByCollectorIdAndNameContainingIgnoreCase(ObjectId collectorId, String name, Pageable pageable);

	Scope findByCollectorIdAndPId(ObjectId collectorId, String pId);
}
