package com.capitalone.dashboard.event.sync;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capitalone.dashboard.model.Build;
import com.capitalone.dashboard.model.CodeQuality;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Dashboard;
import com.capitalone.dashboard.model.Widget;
import com.capitalone.dashboard.model.relation.RelatedCollectorItem;
import com.capitalone.dashboard.repository.BuildRepository;
import com.capitalone.dashboard.repository.CodeQualityRepository;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.repository.DashboardRepository;
import com.capitalone.dashboard.repository.FeatureFlagRepository;
import com.capitalone.dashboard.repository.LibraryPolicyResultsRepository;
import com.capitalone.dashboard.repository.RelatedCollectorItemRepository;
import com.capitalone.dashboard.repository.TestResultRepository;
import com.capitalone.dashboard.util.EmbeddedMongoConfig;
import com.capitalone.dashboard.util.EmbeddedMongoRule;
import com.capitalone.dashboard.util.LoadTestData;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
@DirtiesContext
public class SyncDashboardTest {

    @Autowired
    @Rule
    public EmbeddedMongoRule embeddedMongoRule;

    @Autowired
    private DashboardRepository dashboardRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private CollectorRepository collectorRepository;
    @Autowired
    private CollectorItemRepository collectorItemRepository;

    @Autowired
    private CodeQualityRepository codeQualityRepository;

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private LibraryPolicyResultsRepository libraryPolicyResultsRepository;

    @Autowired
    private TestResultRepository testResultsRepository;

    @Autowired
    private RelatedCollectorItemRepository relatedCollectorItemRepository;

    @Autowired
    private FeatureFlagRepository featureFlagRepository;


    @Bean
    private SyncDashboard syncDashboard() {
        return new SyncDashboard(dashboardRepository, componentRepository, collectorRepository, collectorItemRepository, buildRepository, relatedCollectorItemRepository, codeQualityRepository,featureFlagRepository);
    }


    @Before
    public void loadStuff() throws IOException {
        LoadTestData.loadComponent(componentRepository);
        LoadTestData.loadDashBoard(dashboardRepository);
        LoadTestData.loadCollectorItems(collectorItemRepository);
        LoadTestData.loadCollector(collectorRepository);

        LoadTestData.loadBuilds(buildRepository);
        LoadTestData.loadSSCRequests(codeQualityRepository);
        LoadTestData.loadLibraryPolicy(libraryPolicyResultsRepository);
        LoadTestData.loadTestResults(testResultsRepository);
        LoadTestData.loadCodeQuality(codeQualityRepository);
        LoadTestData.loadFeatureFlags(featureFlagRepository);
    }


    @Test
    public void getWidget() {
        Widget w = syncDashboard().getWidget("repo", dashboardRepository.findAll().iterator().next());
        assertNotEquals(w.getOptions(), null);
        assertEquals(w.getOptions().get("url"), "https://mygithub.com/myOrg/myRepo");

        w =syncDashboard().getWidget("codeanalysis", dashboardRepository.findAll().iterator().next());
        assertNotEquals(w.getOptions(), null);

        w =syncDashboard().getWidget("dummy", dashboardRepository.findAll().iterator().next());
        assertEquals(w, null);
    }



    @Test
    public void getDashboardsByCollectorItems() {
        CollectorItem item = collectorItemRepository.findById(new ObjectId("5ba136220be2d32568777fa5")).orElse(null);
        List<Dashboard> dashboardList = syncDashboard().getDashboardsByCollectorItems(Sets.newHashSet(item), CollectorType.Build);
        assertTrue(!CollectionUtils.isEmpty(dashboardList));
        assertTrue(dashboardList.size() == 3);
        List<String> titles = dashboardList.stream().map(Dashboard::getTitle).collect(Collectors.toList());
        assertTrue(titles.contains("TestPlugin"));
        assertTrue(titles.contains("Test212"));
        assertTrue(titles.contains("WidgetTest"));
    }

    @Test
    public void getDashboardsByCollectorItemsNullTest() {
        List<Dashboard> dashboardList = syncDashboard().getDashboardsByCollectorItems(null, CollectorType.Build);
        assertTrue(CollectionUtils.isEmpty(dashboardList));
    }


    @Test
    public void syncBuildAndRepo() {
        relatedCollectorItemRepository.deleteAll();
        Build build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054")).orElse(null);

        syncDashboard().sync(build);
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertTrue(relatedCollectorItems.size() == 0);
    }

    @Test
    public void syncBuildAndRepoEmptyRepo() {
        relatedCollectorItemRepository.deleteAll();
        Build build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795055")).orElse(null);

        syncDashboard().sync(build);
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertTrue(relatedCollectorItems.size() == 0);
    }


    @Test
    public void syncBuildAndRepoNoSCMCollector() {
        List<Collector> scmCollectors = collectorRepository.findAllByCollectorType(CollectorType.SCM);
        collectorRepository.deleteAll(scmCollectors);
        Build build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795055")).orElse(null);

        syncDashboard().sync(build);
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertTrue(relatedCollectorItems.size() == 0);
    }


    @Test
    public void syncBuildAndCodeQualityWithBuild() {
        relatedCollectorItemRepository.deleteAll();
        CodeQuality codeQuality = codeQualityRepository.findById(new ObjectId("5ba98d055de4b1195307bf5a")).orElse(null);

        Build build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054")).orElse(null);
        syncDashboard().sync(build);
        // now sync code quality
        syncDashboard().sync(codeQuality);

        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertTrue(relatedCollectorItems.size() == 0);

    }

    @Test
    public void syncWithRelatedCollectorItems() {
        relatedCollectorItemRepository.deleteAll();
        CodeQuality codeQuality = codeQualityRepository.findById(new ObjectId("5ba98d055de4b1195307bf5a")).orElse(null);
        Dashboard testSubject = dashboardRepository.findById(new ObjectId("5baa458b0be2d337e3885815")).orElse(null);
        Widget widget = syncDashboard().getWidget("codeanalysis", testSubject);
        assertTrue(widget == null);

        Build build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054")).orElse(null);
        // sync build
        syncDashboard().sync(build);
        // now sync code quality
        syncDashboard().sync(codeQuality);

        relatedCollectorItemRepository.findAll().forEach( r -> {
            try {
                syncDashboard().sync(r,true);
            } catch (SyncException e) {
            }
        });

        testSubject = dashboardRepository.findById(new ObjectId("5baa458b0be2d337e3885815")).orElse(null);
        widget = syncDashboard().getWidget("codeanalysis", testSubject);
        assertTrue(widget == null);

    }
}