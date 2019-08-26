package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.model.AutoDiscovery;
import com.capitalone.dashboard.model.DashboardType;
import com.capitalone.dashboard.model.Owner;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

/**
 * {@link AutoDiscovery} repository.
 */
public interface AutoDiscoveryRepository extends PagingAndSortingRepository<AutoDiscovery, ObjectId> {

	List<AutoDiscovery> findByMetaDataTitle(String title);
	AutoDiscovery findByMetaDataTitleAndMetaDataType(String title, DashboardType type);

}
