package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.CodeReposBuilds;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link CodeReposBuilds} data.
 */
public interface CodeReposBuildsRepository extends CrudRepository<CodeReposBuilds, ObjectId>, QuerydslPredicateExecutor<CodeReposBuilds> {

    CodeReposBuilds findByCodeRepo(String codeRepo);

}
