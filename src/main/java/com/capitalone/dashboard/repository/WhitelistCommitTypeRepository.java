package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.WhitelistCommitType;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface WhitelistCommitTypeRepository extends CrudRepository<WhitelistCommitType, ObjectId> {
}
