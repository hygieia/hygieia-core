package com.capitalone.dashboard.event;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;

import com.capitalone.dashboard.model.Application;
import com.capitalone.dashboard.model.AuthType;
import com.capitalone.dashboard.model.BaseModel;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Commit;
import com.capitalone.dashboard.model.CommitType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.model.Dashboard;
import com.capitalone.dashboard.model.DashboardType;
import com.capitalone.dashboard.model.EnvironmentStage;
import com.capitalone.dashboard.model.Owner;
import com.capitalone.dashboard.model.Pipeline;
import com.capitalone.dashboard.model.PipelineCommit;
import com.capitalone.dashboard.model.PipelineStage;
import com.capitalone.dashboard.model.ScoreDisplayType;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.repository.DashboardRepository;
import com.capitalone.dashboard.repository.PipelineRepository;


@ExtendWith(MockitoExtension.class)
public class CommitEventListenerTest {

    
    private ComponentRepository componentRepository = Mockito.mock(ComponentRepository.class);

    private DashboardRepository dashboardRepository = Mockito.mock(DashboardRepository.class);

    private CollectorRepository collectorRepository = Mockito.mock(CollectorRepository.class);

    private CollectorItemRepository collectorItemRepository = Mockito.mock(CollectorItemRepository.class);

    private PipelineRepository pipelineRepository = Mockito.mock(PipelineRepository.class);

    @InjectMocks
    private CommitEventListener eventListener;

    private static final boolean HAS_BUILD_COLLECTOR = true;
    private static final boolean NO_BUILD_COLLECTOR = false;
    
//    private MongoCollection<Document> collection;
//    private MongoClient client;
//    private MongoServer server;

//    @Before
//    public void setUp() {
//        server = new MongoServer(new MemoryBackend());
//
//        // bind on a random local port
//        InetSocketAddress serverAddress = server.bind();
//
//        client = new MongoClient(new ServerAddress(serverAddress));
//        collection = client.getDatabase("testdb").getCollection("testcollection");
//    }
//
//    @After
//    public void tearDown() {
//        client.close();
//        server.shutdown();
//    }

    
    @Test
    public void onAfterSaveTest() {
    	Commit commit = createCommit("myCommit");
    	doNothing().when(eventListener).onAfterSave(new AfterSaveEvent<>(commit, null, ""));
    }
    
    @Test
    public void commitSaved_addedToPipeline() {
        // Arrange
        Commit commit = createCommit("myCommit");
        PipelineCommit pipelineCommit = new PipelineCommit(commit, commit.getScmCommitTimestamp());
        Dashboard dashboard = createDashboard(HAS_BUILD_COLLECTOR);
        Pipeline pipeline = new Pipeline();
        Map<String, EnvironmentStage> esMap = new HashMap<>();
        esMap.put("Commit", new EnvironmentStage());
        esMap.get("Commit").getCommits().add(pipelineCommit);
        pipeline.setEnvironmentStageMap(esMap);

        setupFindDashboards(commit, dashboard);
        setupGetOrCreatePipeline(dashboard, pipeline);

        // Act
        doNothing().when(eventListener).onAfterSave(new AfterSaveEvent<>(commit, null, ""));

        // Assert
        boolean commitFound = pipeline.getEnvironmentStageMap()
                .get(PipelineStage.COMMIT.getName())
                .getCommits()
                .stream()
                .anyMatch(pc -> pc.getScmRevisionNumber().equals(commit.getScmRevisionNumber()));
        assertThat(commitFound, is(true));
    }

    @Test
    public void mergeCommitSaved_addedToPipeline() {
        // Arrange
        Commit commit = createMergeCommit("myCommit");
        Dashboard dashboard = createDashboard(HAS_BUILD_COLLECTOR);
        Pipeline pipeline = new Pipeline();

        setupFindDashboards(commit, dashboard);
        setupGetOrCreatePipeline(dashboard, pipeline);

        // Act
        eventListener.onAfterSave(new AfterSaveEvent<>(commit, null, ""));

        // Assert
        boolean commitFound = !pipeline.getEnvironmentStageMap().isEmpty() &&  pipeline.getEnvironmentStageMap()
                .get(PipelineStage.COMMIT.getName())
                .getCommits()
                .stream()
                .anyMatch(pc -> pc.getScmRevisionNumber().equals(commit.getScmRevisionNumber()));
        assertThat(commitFound, is(false));
        verify(pipelineRepository, never()).save(pipeline);
    }

    @Test
    public void releaseCommitSaved_addedToPipeline() {
        // Arrange
        Commit commit = createMavenCommit("myCommit");
        Dashboard dashboard = createDashboard(HAS_BUILD_COLLECTOR);
        Pipeline pipeline = new Pipeline();

        setupFindDashboards(commit, dashboard);
        setupGetOrCreatePipeline(dashboard, pipeline);

        // Act
        eventListener.onAfterSave(new AfterSaveEvent<>(commit, null, ""));

        // Assert
        boolean commitFound = !pipeline.getEnvironmentStageMap().isEmpty() &&  pipeline.getEnvironmentStageMap()
                .get(PipelineStage.COMMIT.getName())
                .getCommits()
                .stream()
                .anyMatch(pc -> pc.getScmRevisionNumber().equals(commit.getScmRevisionNumber()));
        assertThat(commitFound, is(false));
        verify(pipelineRepository, never()).save(pipeline);
    }
    @Test
    public void commitSaved_noBuildCollector_notAddedToPipeline() {
        // Arrange
        Commit commit = createCommit("myCommit");
        Dashboard dashboard = createDashboard(NO_BUILD_COLLECTOR);
        Pipeline pipeline = new Pipeline();

        setupFindDashboards(commit, dashboard);
        setupGetOrCreatePipeline(dashboard, pipeline);

        // Act
        eventListener.onAfterSave(new AfterSaveEvent<>(commit, null, ""));

        // Assert
        assertThat(pipeline.getEnvironmentStageMap().get(PipelineStage.COMMIT.getName()), nullValue());
        verify(pipelineRepository, never()).save(pipeline);
    }

    private Commit createCommit(String revisionNumber) {
        Commit commit = new Commit();
        commit.setScmRevisionNumber(revisionNumber);
        commit.setCollectorItemId(ObjectId.get());
        commit.setType(CommitType.New);
        commit.setScmCommitTimestamp(1);
        return commit;
    }

    private Commit createMergeCommit(String revisionNumber) {
        Commit commit = new Commit();
        commit.setScmRevisionNumber(revisionNumber);
        commit.setCollectorItemId(ObjectId.get());
        commit.setType(CommitType.Merge);
        return commit;
    }

    private Commit createMavenCommit(String revisionNumber) {
        Commit commit = new Commit();
        commit.setScmRevisionNumber(revisionNumber);
        commit.setCollectorItemId(ObjectId.get());
        commit.setType(CommitType.NotBuilt);
        return commit;
    }


    private Dashboard createDashboard(boolean hasBuildCollector) {
        Component component = new Component();
        component.setId(ObjectId.get());
        component.addCollectorItem(CollectorType.Product, collectorItem());
        if (hasBuildCollector) {
            component.addCollectorItem(CollectorType.Build, collectorItem());
        }
        component.addCollectorItem(CollectorType.SCM, collectorItem());

        Application application = new Application("app", component);
        List<String> activeWidgets = new ArrayList<>();
        List<Owner> owners = new ArrayList<Owner>();
        owners.add(new Owner("owner", AuthType.STANDARD));
        Dashboard dashboard = new Dashboard("template", "title", application, owners,  DashboardType.Team , "ASVTEST", "BAPTEST",activeWidgets, false, ScoreDisplayType.HEADER);
        dashboard.setId(ObjectId.get());
        return dashboard;
    }

    private void setupFindDashboards(Commit commit, Dashboard dashboard) {
        CollectorItem commitCollectorItem = new CollectorItem();
        List<Component> components = Collections.singletonList(dashboard.getApplication().getComponents().get(0));
        List<ObjectId> componentIds = components.stream().map(BaseModel::getId).collect(Collectors.toList());
        commitCollectorItem.setId(commit.getCollectorItemId());
        when(collectorItemRepository.findById(commit.getCollectorItemId())).thenReturn(Optional.of(commitCollectorItem));
        when(componentRepository.findBySCMCollectorItemId(commitCollectorItem.getId())).thenReturn(components);
        when(dashboardRepository.findByApplicationComponentIdsIn(componentIds)).thenReturn(Collections.singletonList(dashboard));
    }

    private void setupGetOrCreatePipeline(Dashboard dashboard, Pipeline pipeline) {
        Collector productCollector = new Collector();
        productCollector.setId(ObjectId.get());
        CollectorItem teamDashboardCI = collectorItem();

        when(collectorRepository.findByCollectorType(CollectorType.Product))
                .thenReturn(Collections.singletonList(productCollector));
        when(pipelineRepository.findByCollectorItemId(teamDashboardCI.getId())).thenReturn(pipeline);
    }

    private CollectorItem collectorItem() {
        CollectorItem item = new CollectorItem();
        item.setId(ObjectId.get());
        return item;
    }

}
