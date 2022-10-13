package com.capitalone.dashboard.event;

import com.capitalone.dashboard.model.BaseModel;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Commit;
import com.capitalone.dashboard.model.CommitType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.model.Dashboard;
import com.capitalone.dashboard.model.Pipeline;
import com.capitalone.dashboard.model.PipelineCommit;
import com.capitalone.dashboard.model.PipelineStage;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.repository.DashboardRepository;
import com.capitalone.dashboard.repository.PipelineRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Component
public class CommitEventListener extends HygieiaMongoEventListener<Commit> {

    private final ComponentRepository componentRepository;
    private final DashboardRepository dashboardRepository;

    @Autowired
    public CommitEventListener(ComponentRepository componentRepository,
                               DashboardRepository dashboardRepository,
                               CollectorRepository collectorRepository,
                               CollectorItemRepository collectorItemRepository,
                               PipelineRepository pipelineRepository) {
        super(collectorItemRepository, pipelineRepository, collectorRepository);
        this.componentRepository = componentRepository;
        this.dashboardRepository = dashboardRepository;
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Commit> event) {
        Commit commit = event.getSource();
        // Add the commit to all pipelines associated with the team dashboards
        // this commit is part of. But only if there is a build collector item
        // configured on that dashboard. Otherwise, the commit will be orphaned
        // in the commit stage.
        findAllDashboardsForCommit(commit)
                .stream()
                .filter(this::dashboardHasBuildCollector)
                .forEach(teamDashboard -> {
                    if (CommitType.New.equals(commit.getType())) {
                        Pipeline pipeline = getOrCreatePipeline(teamDashboard);
                        PipelineCommit pipelineCommit = new PipelineCommit(commit, commit.getScmCommitTimestamp());
                        pipeline.addCommit(PipelineStage.COMMIT.getName(), pipelineCommit);
                        pipelineRepository.save(pipeline);
                    }
                });
    }

    /**
     * Finds all dashboards for a commit by way of the SCM collector item id of the dashboard that is tied to the commit
     * @param commit
     * @return
     */
    private List<Dashboard> findAllDashboardsForCommit(Commit commit){
        if (commit.getCollectorItemId() == null) return new ArrayList<>();
        CollectorItem commitCollectorItem = collectorItemRepository.findOne(commit.getCollectorItemId());
        List<Component> components = componentRepository.findBySCMCollectorItemId(commitCollectorItem.getId());
        List<ObjectId> componentIds = components.stream().map(BaseModel::getId).collect(Collectors.toList());
        return dashboardRepository.findByApplicationComponentIdsIn(componentIds);
    }

    /**
     * Returns true if the provided dashboard has a build CollectorItem registered.
     *
     * @param teamDashboard a team Dashboard
     * @return true if build CollectorItem found
     */
    private boolean dashboardHasBuildCollector(Dashboard teamDashboard) {
        return teamDashboard.getApplication().getComponents()
                .stream()
                .anyMatch(c -> {
                    List<CollectorItem> buildCollectorItems = c.getCollectorItems(CollectorType.Build);
                    return buildCollectorItems != null && !buildCollectorItems.isEmpty();
                });
    }

}
