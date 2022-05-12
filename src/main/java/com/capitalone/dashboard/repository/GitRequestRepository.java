package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.GitRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for {@link GitRequest} data.
 */
public interface GitRequestRepository  extends CrudRepository<GitRequest, ObjectId>, QuerydslPredicateExecutor<GitRequest> {


    List<GitRequest> findByCollectorItemIdAndRequestType(ObjectId collectorItemId, String requestType);

    @Query(value="{ 'collectorItemId' : ?0, 'requestType' : ?1}", fields="{ 'number' : 2, 'updatedAt' : 3 }")
    List<GitRequest> findRequestNumberAndLastUpdated(ObjectId collectorItemId, String requestType);

    @Query(value="{ 'collectorItemId' : ?0, 'state' : { $ne : 'merged' }}", fields="{ 'requestType' : 1, 'number' : 2, 'updatedAt' : 3, 'id' : 4}")
    List<GitRequest> findNonMergedRequestNumberAndLastUpdated(ObjectId collectorItemId);

    @Query(value="{ 'collectorItemId' : ?0}", fields="{ 'requestType' : 1, 'number' : 2, 'updatedAt' : 3, 'id' : 4}")
    List<GitRequest> findRequestNumberAndLastUpdated(ObjectId collectorItemId);

    GitRequest findByCollectorItemIdAndScmRevisionNumber(ObjectId collectorItemId, String revisionNumber);

    GitRequest findByCollectorItemIdAndNumberAndRequestType(ObjectId collectorItemId, String number, String requestType);

    @Query(value="{ 'collectorItemId': ?0, 'scmCommitTimestamp': { $gt: ?1 }}")
    List<GitRequest> findByCollectorItemIdAndScmCommitTimestamp(ObjectId collectorItemid,
                                                          Long scmCommitTimestampThreshold);

    GitRequest findByCollectorItemIdAndNumber(ObjectId collectorItemId, String number);

    List<GitRequest> findByScmUrlIgnoreCaseAndScmBranchIgnoreCaseAndCreatedAtGreaterThanEqualAndMergedAtLessThanEqual(String scmUrl, String scmBranch, long beginDt, long endDt);

    List<GitRequest> findByScmUrlIgnoreCaseAndScmBranchIgnoreCase(String scmUrl, String scmBranch);

    List<GitRequest> findByScmRevisionNumber(String revisionNumber);

    List<GitRequest> findByCollectorItemIdAndMergedAtIsBetween(ObjectId collectorItemId, long beginDate, long endDate);

    GitRequest findByScmUrlIgnoreCaseAndScmBranchIgnoreCaseAndNumberAndRequestTypeIgnoreCase(String scmUrl, String scmBranch, String number, String requestType);

    @Query(value="{'$or':[{'scmRevisionNumber' : ?0}, {'scmMergeEventRevisionNumber' : ?0}]}")
    GitRequest findByScmRevisionNumberOrScmMergeEventRevisionNumber(String revisionNumber);

    @Query(value="{'commits.scmRevisionNumber' : ?0}")
    GitRequest findByCommitScmRevisionNumber(String revisionNumber);

    GitRequest findTopByCollectorItemIdOrderByTimestampDesc(ObjectId collectorItemId);
}
