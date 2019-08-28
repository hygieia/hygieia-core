package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.AutoDiscovery;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * {@link AutoDiscovery} repository.
 */
public interface AutoDiscoveryRepository extends PagingAndSortingRepository<AutoDiscovery, ObjectId> {

	List<AutoDiscovery> findByMetaDataTitle(String title);
	AutoDiscovery findByMetaDataTitleAndMetaDataType(String title, String type);

}
