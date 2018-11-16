package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.ServiceAccount;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface ServiceAccountRepository extends CrudRepository<ServiceAccount, ObjectId> {

}
