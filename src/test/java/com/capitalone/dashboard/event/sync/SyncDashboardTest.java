package com.capitalone.dashboard.event.sync;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;

import com.capitalone.dashboard.model.Build;
import com.capitalone.dashboard.model.CodeQuality;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
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
import com.capitalone.dashboard.util.LoadTestData;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@ExtendWith(MockitoExtension.class)
public class SyncDashboardTest {


    private DashboardRepository dashboardRepository = Mockito.mock(DashboardRepository.class);
    private ComponentRepository componentRepository = Mockito.mock(ComponentRepository.class);
    private CollectorRepository collectorRepository = Mockito.mock(CollectorRepository.class);
    private CollectorItemRepository collectorItemRepository = Mockito.mock(CollectorItemRepository.class);

    private CodeQualityRepository codeQualityRepository = Mockito.mock(CodeQualityRepository.class);

    private BuildRepository buildRepository = Mockito.mock(BuildRepository.class);

    private LibraryPolicyResultsRepository libraryPolicyResultsRepository = Mockito.mock(LibraryPolicyResultsRepository.class);

    private TestResultRepository testResultsRepository = Mockito.mock(TestResultRepository.class);

    private RelatedCollectorItemRepository relatedCollectorItemRepository = Mockito.mock(RelatedCollectorItemRepository.class);

    private FeatureFlagRepository featureFlagRepository = Mockito.mock(FeatureFlagRepository.class);


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
    	CollectorItem collectorItem = new CollectorItem();
    	collectorItem.setId(new ObjectId("5ba136220be2d32568777fa5"));
    	
        Optional<CollectorItem> item = Optional.of(collectorItem);
       
        when(collectorItemRepository.findById(new ObjectId("5ba136220be2d32568777fa5"))).thenReturn(item);
        List<Dashboard> dashboardList = new ArrayList<Dashboard>();
        Dashboard dash = new Dashboard();
        dash.setId(new ObjectId("612ee5e8209a9935fbc38dbe"));
        dash.setTitle("TestPlugin");
        dashboardList.add(dash);
        Dashboard dash2 = new Dashboard();
        dash2.setId(new ObjectId("612ee74b209a9935fbc38dc2"));
        dash2.setTitle("Test212");
        dashboardList.add(dash2);
        Dashboard dash3 = new Dashboard();
        dash3.setId(new ObjectId("6130152818963b26724dc63a"));
        dash3.setTitle("WidgetTest");
        dashboardList.add(dash3);
        
        when(syncDashboard().getDashboardsByCollectorItems(Sets.newHashSet(item.get()), CollectorType.Build)).thenReturn(dashboardList);
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
        Optional<Build> build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054"));

        syncDashboard().sync(build.get());
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());

        assertTrue(relatedCollectorItems.size() == 0);

    }

    @Test
    public void syncBuildAndRepoEmptyRepo() {
        relatedCollectorItemRepository.deleteAll();
        Optional<Build> build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795055"));

        syncDashboard().sync(build.get());
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertTrue(relatedCollectorItems.size() == 0);
    }


    @Test
    public void syncBuildAndRepoNoSCMCollector() {
        List<Collector> scmCollectors = collectorRepository.findAllByCollectorType(CollectorType.SCM);
        collectorRepository.deleteAll(scmCollectors);
        Optional<Build> build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795055"));

        syncDashboard().sync(build.get());
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertTrue(relatedCollectorItems.size() == 0);
    }


    @Test
    public void syncBuildAndCodeQualityWithBuild() {
        relatedCollectorItemRepository.deleteAll();
        Optional<CodeQuality> codeQuality = codeQualityRepository.findById(new ObjectId("5ba98d055de4b1195307bf5a"));

        Optional<Build> build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054"));
        syncDashboard().sync(build.get());
        // now sync code quality
        syncDashboard().sync(codeQuality.get());

        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());

        assertTrue(relatedCollectorItems.size() == 0);


    }

    @Test
    public void syncWithRelatedCollectorItems() {
        relatedCollectorItemRepository.deleteAll();
        Optional<CodeQuality> codeQuality = codeQualityRepository.findById(new ObjectId("5ba98d055de4b1195307bf5a"));
        Optional<Dashboard> testSubject = dashboardRepository.findById(new ObjectId("5baa458b0be2d337e3885815"));
        Widget widget = syncDashboard().getWidget("codeanalysis", testSubject.get());
        assertTrue(widget == null);

        Optional<Build> build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054"));
        // sync build
        syncDashboard().sync(build.get());
        // now sync code quality
        syncDashboard().sync(codeQuality.get());

        relatedCollectorItemRepository.findAll().forEach( r -> {
            try {
                syncDashboard().sync(r,true);
            } catch (SyncException e) {
            }
        });


        testSubject = dashboardRepository.findById(new ObjectId("5baa458b0be2d337e3885815"));
        widget = syncDashboard().getWidget("codeanalysis", testSubject.get());
        assertTrue(widget != null);

        Optional<Component> component = componentRepository.findById(widget.getComponentId());
        assertTrue(component != null);
        CollectorItem si = component.get().getCollectorItems(CollectorType.CodeQuality).get(0);


    }
}