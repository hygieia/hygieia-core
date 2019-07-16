package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.FeatureFlag;
import com.capitalone.dashboard.model.ServiceAccount;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface FeatureFlagRepository extends CrudRepository<FeatureFlag, ObjectId> {

}
