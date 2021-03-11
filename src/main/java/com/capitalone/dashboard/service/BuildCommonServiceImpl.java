package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.Build;
import com.capitalone.dashboard.repository.BuildRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildCommonServiceImpl implements BuildCommonService {


    private final BuildRepository buildRepository;

    @Autowired
    public BuildCommonServiceImpl(BuildRepository buildRepository){
        this.buildRepository = buildRepository;
    }

    @Override
    public Build get(ObjectId id) {
        return buildRepository.findOne(id);
    }

    @Override
    public Build get(String buildUrl) {
        return buildRepository.findByBuildUrl(buildUrl);
    }
}
