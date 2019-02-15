package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.testutil.FongoConfig;
import com.capitalone.dashboard.util.LoadTestData;
import com.github.fakemongo.junit.FongoRule;
import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FongoConfig.class})
@DirtiesContext
public class CollectorItemRepositoryTest {

    @Rule
    public FongoRule fongoRule = new FongoRule();

    @Autowired
    private CollectorItemRepository collectorItemRepository;

    @Test
    public void findAllByOptionNameValueAndCollectorIdsInReturns0() throws IOException {
        collectorItemRepository.deleteAll();
        LoadTestData.loadCollectorItems(collectorItemRepository);
        Iterable<CollectorItem> items = collectorItemRepository.findAllByOptionNameValueAndCollectorIdsIn("jobName", "job/", Collections.singletonList(new ObjectId("5ba136220be2d32568777fa4")));
        List<CollectorItem> itemList = Lists.newArrayList(items);
        assertEquals(itemList.size(),0);
    }

    @Test
    public void findAllByOptionNameValueAndCollectorIdsInReturns1() throws IOException {
        collectorItemRepository.deleteAll();
        LoadTestData.loadCollectorItems(collectorItemRepository);
        Iterable<CollectorItem> items = collectorItemRepository.findAllByOptionNameValueAndCollectorIdsIn("jobName", "job/c1usercheck/", Collections.singletonList(new ObjectId("5ba136220be2d32568777fa4")));
        List<CollectorItem> itemList = Lists.newArrayList(items);
        assertEquals(itemList.size(),1);
        assertEquals(itemList.get(0).getId().toHexString(), "5ba136220be2d32568777fa5");
    }

    @Test
    public void findAllByOptionNameValueAndCollectorIdsInReturns2() throws IOException {
        collectorItemRepository.deleteAll();
        LoadTestData.loadCollectorItems(collectorItemRepository);
        Iterable<CollectorItem> items = collectorItemRepository.findAllByOptionNameValueAndCollectorIdsIn("instanceUrl", "http://localhost:8082/", Collections.singletonList(new ObjectId("5ba136220be2d32568777fa4")));
        List<CollectorItem> itemList = Lists.newArrayList(items);
        assertEquals(itemList.size(),2);
        List<String> objectIds = itemList.stream().map(i -> i.getId().toHexString()).collect(Collectors.toList());
        assertEquals(objectIds, Lists.newArrayList("5ba136220be2d32568777fa5", "5ba136220be2d32568777fa6"));
    }


    @Test
    public void findAllByOptionMapAndCollectorIdsIn2MapEntry() throws IOException {
        collectorItemRepository.deleteAll();
        LoadTestData.loadCollectorItems(collectorItemRepository);
        Map<String, Object> inMap = new HashMap<>();
        inMap.put("jobName", "job/c1usercheck/");
        inMap.put("instanceUrl", "http://localhost:8082/");

        Iterable<CollectorItem> items = collectorItemRepository.findAllByOptionMapAndCollectorIdsIn(inMap, Collections.singletonList(new ObjectId("5ba136220be2d32568777fa4")));
        List<CollectorItem> itemList = Lists.newArrayList(items);
        assertEquals(itemList.size(),1);
        assertEquals(itemList.get(0).getId().toHexString(), "5ba136220be2d32568777fa5");
    }

    @Test
    public void findAllByOptionMapAndCollectorIdsInStringAndNumber() throws IOException {
        collectorItemRepository.deleteAll();
        LoadTestData.loadCollectorItems(collectorItemRepository);
        Map<String, Object> inMap = new HashMap<>();
        inMap.put("jobName", "jobname");
        inMap.put("jobNumber", 123456789);

        Iterable<CollectorItem> items = collectorItemRepository.findAllByOptionMapAndCollectorIdsIn(inMap, Collections.singletonList(new ObjectId("5ba136220be2d32568777fff")));
        List<CollectorItem> itemList = Lists.newArrayList(items);
        assertEquals(itemList.size(),1);
        assertEquals(itemList.get(0).getId().toHexString(), "5ba136220be2d32568777fa7");
    }
}