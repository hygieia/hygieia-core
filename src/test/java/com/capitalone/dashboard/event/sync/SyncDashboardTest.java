package com.capitalone.dashboard.event.sync;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Arrays;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
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

    private LibraryPolicyResultsRepository libraryPolicyResultsRepository = Mockito
            .mock(LibraryPolicyResultsRepository.class);

    private TestResultRepository testResultsRepository = Mockito.mock(TestResultRepository.class);

    private RelatedCollectorItemRepository relatedCollectorItemRepository = Mockito
            .mock(RelatedCollectorItemRepository.class);

    private FeatureFlagRepository featureFlagRepository = Mockito.mock(FeatureFlagRepository.class);


    @Bean
    private SyncDashboard syncDashboard() {
        return new SyncDashboard(dashboardRepository, componentRepository, collectorRepository, collectorItemRepository,
                buildRepository, relatedCollectorItemRepository, codeQualityRepository, featureFlagRepository);
    }

    @BeforeEach
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
        List<Widget> widgets = new ArrayList<Widget>();
        Dashboard d = new Dashboard();

        Widget w1 = new Widget();
        widgets.add(w1);
        w1.setName("repo");

        Widget w2 = new Widget();
        widgets.add(w2);
        w2.setName("repo");

        d.setWidgets(widgets);

        ArrayList<Dashboard> dashboardsAL = new ArrayList<Dashboard>();
        dashboardsAL.add(d);

        Iterable<Dashboard> dashboardsIt = dashboardsAL;

        when(dashboardRepository.findAll()).thenReturn(dashboardsIt);

        Dashboard dd = dashboardRepository.findAll().iterator().next();
        Widget w = syncDashboard().getWidget("repo", dd);
//
//
//        assertNotEquals(w.getOptions(), null);
        assertEquals(w.getOptions().size(), 0);
//        assertEquals(w.getOptions().get("url"), "https://mygithub.com/myOrg/myRepo");
//
        w = syncDashboard().getWidget("codeanalysis", dashboardRepository.findAll().iterator().next());
        assertNull(w);
//
        w = syncDashboard().getWidget("dummy", dashboardRepository.findAll().iterator().next());
        assertNull(w);

        verify(dashboardRepository, times(3)).findAll();
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

        collectorItemRepository.findById(new ObjectId("5ba136220be2d32568777fa5"));

        when(syncDashboard().getDashboardsByCollectorItems(Sets.newHashSet(item.get()), CollectorType.Build))
                .thenReturn(dashboardList);

        List<Dashboard> dList = syncDashboard().getDashboardsByCollectorItems(Sets.newHashSet(item.get()), CollectorType.Build);

        assertTrue(!CollectionUtils.isEmpty(dList));
        assertTrue(dList.size() == 3);
        List<String> titles = dList.stream().map(Dashboard::getTitle).collect(Collectors.toList());
        assertTrue(titles.contains("TestPlugin"));
        assertTrue(titles.contains("Test212"));
        assertTrue(titles.contains("WidgetTest"));

        verify(collectorItemRepository).findById(new ObjectId("5ba136220be2d32568777fa5"));
//		verify(syncDashboard()).getDashboardsByCollectorItems(Sets.newHashSet(item.get()), CollectorType.Build);
    }

    @Test
    public void getDashboardsByCollectorItemsNullTest() {
        List<Dashboard> dashboardList = syncDashboard().getDashboardsByCollectorItems(null, CollectorType.Build);
        assertTrue(CollectionUtils.isEmpty(dashboardList));
    }

    @Test
    public void syncBuildAndRepo() {
        Build b = new Build();
        b.setId(new ObjectId("5ba520c40be2d3f98f795054"));
        Optional<Build> bOptional = Optional.of(b);

        Iterable<RelatedCollectorItem> rcItems = new ArrayList<RelatedCollectorItem>();

        Mockito.lenient().doNothing().when(relatedCollectorItemRepository).deleteAll();
        when(buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054"))).thenReturn(bOptional);
        when(relatedCollectorItemRepository.findAll()).thenReturn(rcItems);
        relatedCollectorItemRepository.deleteAll();
        Optional<Build> buildOptional = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054"));

        buildOptional.ifPresent(bl -> syncDashboard().sync(bl));

        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());

        verify(relatedCollectorItemRepository).deleteAll();
        verify(buildRepository).findById(new ObjectId("5ba520c40be2d3f98f795054"));
        verify(relatedCollectorItemRepository).findAll();
        assertTrue(relatedCollectorItems.size() == 0);

    }

    @Test
    public void syncBuildAndRepoEmptyRepo() {
        relatedCollectorItemRepository.deleteAll();
        Optional<Build> build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795055"));

        build.ifPresent(b -> syncDashboard().sync(b));

        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertTrue(relatedCollectorItems.size() == 0);
    }


    @Test
    public void syncBuildAndRepoNoSCMCollector() {
        List<Collector> scmCollectors = collectorRepository.findAllByCollectorType(CollectorType.SCM);
        collectorRepository.deleteAll(scmCollectors);
        Optional<Build> build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795055"));

        build.ifPresent(b -> syncDashboard().sync(b));
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertTrue(relatedCollectorItems.size() == 0);
    }

    @Test
    public void syncBuildAndCodeQualityWithBuild() {
        relatedCollectorItemRepository.deleteAll();
        CodeQuality cq = new CodeQuality();
        Optional<CodeQuality> codeQuality = Optional.of(cq);

        Build bd = new Build();
        Optional<Build> build = Optional.of(bd);

        when(codeQualityRepository.findById(new ObjectId("5ba98d055de4b1195307bf5a"))).thenReturn(codeQuality);
        when(buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054"))).thenReturn(build);

        build.ifPresent(b -> syncDashboard().sync(b));
        // now sync code quality
        codeQuality.ifPresent(c -> syncDashboard().sync(c));

        codeQuality = codeQualityRepository.findById(new ObjectId("5ba98d055de4b1195307bf5a"));
        build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054"));

        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());

        assertTrue(relatedCollectorItems.size() == 0);
        verify(codeQualityRepository).findById(new ObjectId("5ba98d055de4b1195307bf5a"));
        verify(buildRepository).findById(new ObjectId("5ba520c40be2d3f98f795054"));
    }

    @Test
    public void syncWithRelatedCollectorItems() {
        relatedCollectorItemRepository.deleteAll();
        Optional<CodeQuality> codeQuality = codeQualityRepository.findById(new ObjectId("5ba98d055de4b1195307bf5a"));
        Optional<Dashboard> testSubject = dashboardRepository.findById(new ObjectId("5baa458b0be2d337e3885815"));
        Optional<Component> component = null;
        Widget widget = null;

        ArrayList<RelatedCollectorItem> itemsAr = new ArrayList<RelatedCollectorItem>();
        RelatedCollectorItem r1 = new RelatedCollectorItem();
        RelatedCollectorItem r2 = new RelatedCollectorItem();

        r1.setLeft(new ObjectId());
        r1.setRight(new ObjectId());
        r2.setLeft(new ObjectId());
        r2.setRight(new ObjectId());

        itemsAr.add(new RelatedCollectorItem());
        itemsAr.add(new RelatedCollectorItem());
        Iterable<RelatedCollectorItem> itemsIt = itemsAr;

        when(relatedCollectorItemRepository.findAll()).thenReturn(itemsIt);

        if (!testSubject.isEmpty()) {
            widget = syncDashboard().getWidget("codeanalysis", testSubject.get());
        }

        assertTrue(codeQuality.isEmpty());
        assertTrue(testSubject.isEmpty());
        assertNull(widget);

        Optional<Build> build = buildRepository.findById(new ObjectId("5ba520c40be2d3f98f795054"));
        // sync build
        build.ifPresent(bl -> syncDashboard().sync(bl));
        // now sync code quality
        codeQuality.ifPresent(cq -> syncDashboard().sync(cq));
        relatedCollectorItemRepository.findAll();

        verify(relatedCollectorItemRepository).findAll();

        assertThrows(NoSuchElementException.class, () -> {
            relatedCollectorItemRepository.findAll().forEach(r -> {
                try {
                    syncDashboard().sync(r, true);
                } catch (SyncException e) {
                }
            });
        });

        testSubject = dashboardRepository.findById(new ObjectId("5baa458b0be2d337e3885815"));
        if (!testSubject.isEmpty()) {
            widget = syncDashboard().getWidget("codeanalysis", testSubject.get());
        }

        assertTrue(testSubject.isEmpty());
        assertNull(widget);

        if (widget != null) {
            component = componentRepository.findById(widget.getComponentId());
        }

        assertNull(component);

        // CollectorItem si =
        // component.get().getCollectorItems(CollectorType.CodeQuality).get(0);

    }
}