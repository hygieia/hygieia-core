package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.CodeReposBuilds;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link CodeReposBuilds} data.
 */
public interface CodeReposBuildsRepository extends CrudRepository<CodeReposBuilds, ObjectId>, QueryDslPredicateExecutor<CodeReposBuilds> {

    CodeReposBuilds findByCodeRepo(String codeRepo);

}
