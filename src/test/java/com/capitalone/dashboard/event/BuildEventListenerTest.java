package com.capitalone.dashboard.event;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;

import com.capitalone.dashboard.event.sync.SyncDashboard;
import com.capitalone.dashboard.model.Application;
import com.capitalone.dashboard.model.AuthType;
import com.capitalone.dashboard.model.BaseModel;
import com.capitalone.dashboard.model.Build;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Commit;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.model.Dashboard;
import com.capitalone.dashboard.model.DashboardType;
import com.capitalone.dashboard.model.EnvironmentStage;
import com.capitalone.dashboard.model.Owner;
import com.capitalone.dashboard.model.Pipeline;
import com.capitalone.dashboard.model.PipelineStage;
import com.capitalone.dashboard.model.ScoreDisplayType;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.CommitRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.repository.DashboardRepository;
import com.capitalone.dashboard.repository.PipelineRepository;
import com.capitalone.dashboard.util.TestUtils;


@ExtendWith(MockitoExtension.class)
public class BuildEventListenerTest {

    @Mock
    private ComponentRepository componentRepository;

    @Mock
    private DashboardRepository dashboardRepository;

    @Mock
    private CollectorRepository collectorRepository;

    @Mock
    private CollectorItemRepository collectorItemRepository;

    @Mock
    private PipelineRepository pipelineRepository;

    @InjectMocks
    private BuildEventListener eventListener;

    @Mock
    private SyncDashboard syncDashboard;

    @Mock
    private CommitRepository commitRepository;

    private static final boolean HAS_BUILD_COLLECTOR = true;

    @Test
    public void buildSaved_addedToPipeline() {
        Build build = TestUtils.createBuild();
        CollectorItem productCI = collectorItem();
        Dashboard dashboard = createDashboard(HAS_BUILD_COLLECTOR, productCI);
        Pipeline pipeline = new Pipeline();
        setupFindDashboards(build, dashboard);
        setupGetOrCreatePipeline(dashboard, pipeline, productCI);
        eventListener.onAfterSave(new AfterSaveEvent<>(build, null, ""));
        Map<String,EnvironmentStage> pipelineMap = pipeline.getEnvironmentStageMap();
        Assert.assertEquals(pipelineMap.get("Build").getCommits().size(), 2);
        verify(pipelineRepository).save(pipeline);
    }

    @Test
    public void buildSaved_addedToPipeline_commitStage() {
        Build build = TestUtils.createBuild();
        CollectorItem productCI = collectorItem();
        Dashboard dashboard = createDashboard(HAS_BUILD_COLLECTOR, productCI);
        Pipeline pipeline = new Pipeline();
        pipeline.addCommit(PipelineStage.COMMIT.getName(), TestUtils.createPipelineCommit("scmRev3"));
        setupFindDashboards(build, dashboard);
        setupGetOrCreatePipeline(dashboard, pipeline, productCI);
        List<Commit> commits = new ArrayList<>();
       commits.add(TestUtils.createCommit("scmRev3","http://github.com/scmurl"));
        when(commitRepository.findByScmRevisionNumber("scmRev3")).thenReturn(commits);
        eventListener.onAfterSave(new AfterSaveEvent<>(build, null, ""));
        Map<String,EnvironmentStage> pipelineMap = pipeline.getEnvironmentStageMap();
        Assert.assertEquals(pipelineMap.get("Build").getCommits().size(), 3);
        verify(pipelineRepository).save(pipeline);
    }

    private Dashboard createDashboard(boolean hasBuildCollector, CollectorItem productCI) {
        Component component = new Component();
        component.setId(ObjectId.get());
        component.addCollectorItem(CollectorType.Product, productCI);
        if (hasBuildCollector) {
            component.addCollectorItem(CollectorType.Build, collectorItem());
        }

        Application application = new Application("app", component); List<String> activeWidgets = new ArrayList<>();
        List<Owner> owners = new ArrayList<Owner>();
        owners.add(new Owner("owner", AuthType.STANDARD));
        Dashboard dashboard = new Dashboard("template", "title", application, owners,  DashboardType.Team , "ASVTEST", "BAPTEST",activeWidgets, false, ScoreDisplayType.HEADER);
        dashboard.setId(ObjectId.get());
        return dashboard;
    }

    private void setupFindDashboards(Build build, Dashboard dashboard) {
        CollectorItem commitCollectorItem = new CollectorItem();
        List<Component> components = Collections.singletonList(dashboard.getApplication().getComponents().get(0));
        List<ObjectId> componentIds = components.stream().map(BaseModel::getId).collect(Collectors.toList());
        commitCollectorItem.setId(build.getCollectorItemId());
        when(collectorItemRepository.findById(build.getCollectorItemId())).thenReturn(Optional.of(commitCollectorItem));
        when(componentRepository.findByBuildCollectorItemId(commitCollectorItem.getId())).thenReturn(components);
        when(dashboardRepository.findByApplicationComponentIdsIn(componentIds)).thenReturn(Collections.singletonList(dashboard));
    }

    private void setupGetOrCreatePipeline(Dashboard dashboard, Pipeline pipeline, CollectorItem productCI) {
        Collector productCollector = new Collector();
        productCollector.setId(ObjectId.get());
        when(collectorRepository.findByCollectorType(CollectorType.Product))
                .thenReturn(Collections.singletonList(productCollector));
        when(pipelineRepository.findByCollectorItemId(productCI.getId())).thenReturn(pipeline);
    }

    private CollectorItem collectorItem() {
        CollectorItem item = new CollectorItem();
        item.setId(ObjectId.get());
        return item;
    }

}
