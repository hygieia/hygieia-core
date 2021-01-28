package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.FilterCommitType;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface FilterCommitTypeRepository extends CrudRepository<FilterCommitType, ObjectId> {
}
