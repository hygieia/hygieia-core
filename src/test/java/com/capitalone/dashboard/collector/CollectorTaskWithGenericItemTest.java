package com.capitalone.dashboard.collector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.GenericCollectorItem;
import com.capitalone.dashboard.model.relation.RelatedCollectorItem;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.GenericCollectorItemRepository;
import com.capitalone.dashboard.repository.RelatedCollectorItemRepository;
import com.capitalone.dashboard.util.LoadTestData;
//import com.github.fakemongo.junit.FongoRule;
import com.google.common.collect.Lists;

@ExtendWith(MockitoExtension.class)
public class CollectorTaskWithGenericItemTest<TestCollectorTaskTwoWithGenericItem> {

    @Mock
    private CollectorItemRepository collectorItemRepository;

    @Mock
    private GenericCollectorItemRepository genericCollectorItemRepository;

    @Mock
    RelatedCollectorItemRepository relatedCollectorItemRepository;

    @Mock
    CollectorTaskWithGenericItem testCollectorTaskWithGenericItem;

    @Mock
    TestCollectorTaskTwoWithGenericItem testCollectorTaskTwoWithGenericItem;

    @Mock
    CollectorRepository collectorRepository;


    private void reset() throws IOException {
        collectorItemRepository.deleteAll();
        genericCollectorItemRepository.deleteAll();
        relatedCollectorItemRepository.deleteAll();
        LoadTestData.loadCollectorItems(collectorItemRepository);
    }

    @Test
    public void processGenericItems() throws IOException {
        reset();
        RelatedCollectorItem item = new RelatedCollectorItem();
        List<RelatedCollectorItem> items = new ArrayList<RelatedCollectorItem>();
        Iterable<RelatedCollectorItem> itemsIt = items;
        items.add(item);

        item.setLeft(new ObjectId("5bae541b099739600663ef9a"));
        item.setRight(new ObjectId("5ba136290be2d32568777fa9"));


        List<GenericCollectorItem> gItems = new ArrayList<GenericCollectorItem>();
        Iterable<GenericCollectorItem> gItemsIt = gItems;
        GenericCollectorItem gci = createGenericItem("Sonar", "C1 Custom Env Var Inject", "some source", "5ba136290be2d32568777fa8", "5ba136290be2d32568777fa8", "5bae541b099739600663ef9a");
        gci.setProcessTime(1L);
        gItems.add(gci);
        genericCollectorItemRepository.save(gci);

        when(relatedCollectorItemRepository.findAll()).thenReturn(itemsIt);
        when(genericCollectorItemRepository.findAll()).thenReturn(gItemsIt);

        Map<ObjectId, Set<ObjectId>> objectIdSetMap = testCollectorTaskWithGenericItem.processGenericItems(Lists.newArrayList("http://localhost:9000"));
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertNotNull(objectIdSetMap);
        assertNotNull(relatedCollectorItems);
        assertEquals(1, relatedCollectorItems.size());

        assertEquals(new ObjectId("5bae541b099739600663ef9a"), relatedCollectorItems.get(0).getLeft());
        assertEquals(new ObjectId("5ba136290be2d32568777fa9"), relatedCollectorItems.get(0).getRight());

        GenericCollectorItem genericCollectorItem = genericCollectorItemRepository.findAll().iterator().next();
        assertTrue(genericCollectorItem.getProcessTime() > 0);
    }

    @Test
    public void processGenericItemsTwoGenericItems() throws IOException {
        reset();
        RelatedCollectorItem item = new RelatedCollectorItem();
        List<RelatedCollectorItem> items = new ArrayList<RelatedCollectorItem>();
        Iterable<RelatedCollectorItem> itemsIt = items;

        List<GenericCollectorItem> gItems = new ArrayList<GenericCollectorItem>();
        Iterable<GenericCollectorItem> gItemsIt = gItems;

        GenericCollectorItem gci = createGenericItem("Sonar", "C1 Custom Env Var Inject", "some source", "5ba136290be2d32568777fa8", "5ba136290be2d32568777fa8", "5bae541b099739600663ef9a");
        genericCollectorItemRepository.save(gci);

        gci.setProcessTime(1L);
        gItems.add(gci);
        when(genericCollectorItemRepository.findAllByToolName("Sonar")).thenReturn(gItems);

        item.setLeft(new ObjectId("5bae541b099739600663ef9a"));
        item.setRight(new ObjectId("5ba136290be2d32568777fa8"));
        item.setId(gci.getRelatedCollectorItem());
        items.add(item);

        when(relatedCollectorItemRepository.findAll()).thenReturn(itemsIt);

        gci = createGenericItem("MyTool", "Raw Data", "some source", "5ca136290be2d32568777fa8", "5ca136290be2d32568777fa8", "5cae541b099739600663ef9a");
        genericCollectorItemRepository.save(gci);

        Collector collector = new Collector();
        collector.setId(new ObjectId("5ba136290be2d32568777fa8"));
        collector.setName("Sonar");



        Map<ObjectId, Set<ObjectId>> objectIdSetMap = testCollectorTaskWithGenericItem.processGenericItems(Lists.newArrayList("http://localhost:9000"));
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertNotNull(objectIdSetMap);
        assertNotNull(relatedCollectorItems);
        assertEquals(1, relatedCollectorItems.size());

        assertEquals(new ObjectId("5bae541b099739600663ef9a"), relatedCollectorItems.get(0).getLeft());
        assertEquals(new ObjectId("5ba136290be2d32568777fa8"), relatedCollectorItems.get(0).getRight());

        GenericCollectorItem genericCollectorItem = genericCollectorItemRepository.findAllByToolName("Sonar").get(0);
        assertTrue(genericCollectorItem.getProcessTime() > 0);

        gItems.clear();
        gci.setProcessTime(0L);
        gItems.add(gci);
        when(genericCollectorItemRepository.findAllByToolName("MyTool")).thenReturn(gItems);

        genericCollectorItem = genericCollectorItemRepository.findAllByToolName("MyTool").get(0);
        assertEquals(0, genericCollectorItem.getProcessTime());

    }


    @Test
    public void processGenericItemsEmptyGenericItemRepo() throws IOException {
        reset();

        Collector collector = new Collector();
        collector.setId(new ObjectId("5ba136290be2d32568777fa8"));
        collector.setName("Sonar");
        Map<ObjectId, Set<ObjectId>> objectIdSetMap = testCollectorTaskWithGenericItem.processGenericItems(Lists.newArrayList("http://localhost:9000"));
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertEquals(0, objectIdSetMap.size());
        assertEquals(0, relatedCollectorItems.size());
    }


    @Test
    public void processGenericItemsNoMatchingCollectorGenericItemRepo() throws IOException {
        reset();

        LoadTestData.loadCollectorItems(collectorItemRepository);
        GenericCollectorItem gci = createGenericItem("MyTool", "C1 Custom Env Var Inject", "some source", "5ca136290be2d32568777fa8", "5ba136290be2d32568777fa8", "5bae541b099739600663ef9a");
        genericCollectorItemRepository.save(gci);

        Collector collector = new Collector();
        collector.setId(new ObjectId("5ba136290be2d32568777fa8"));
        collector.setName("Sonar");
        Map<ObjectId, Set<ObjectId>> objectIdSetMap = testCollectorTaskWithGenericItem.processGenericItems(Lists.newArrayList("http://localhost:9000"));
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertEquals(0, objectIdSetMap.size());
        assertEquals(0, relatedCollectorItems.size());
    }


    @Test
    public void processGenericItemsNoMatchingCollectorGenericItemRepo2() throws IOException {
        reset();

        LoadTestData.loadCollectorItems(collectorItemRepository);
        GenericCollectorItem gci = createGenericItem("Sonar", "C1 Custom Env Var Inject", "some source", "5ca136290be2d32568777fa8", "5ba136290be2d32568777fa8", "5bae541b099739600663ef9a");
        genericCollectorItemRepository.save(gci);
        Map<ObjectId, Set<ObjectId>> objectIdSetMap = testCollectorTaskWithGenericItem.processGenericItems(Lists.newArrayList("http://localhost:9000"));
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertEquals(0, objectIdSetMap.size());
        assertEquals(0, relatedCollectorItems.size());
    }

    @Test
    public void processGenericItemsNoMatchingCollectorGenericItemRepoAllProcessed() throws IOException {
        reset();

        LoadTestData.loadCollectorItems(collectorItemRepository);
        GenericCollectorItem gci = createGenericItem("MyTool", "C1 Custom Env Var Inject", "some source", "5ca136290be2d32568777fa8", "5ba136290be2d32568777fa8", "5bae541b099739600663ef9a");
        gci.setProcessTime(1L);
        genericCollectorItemRepository.save(gci);

        Collector collector = new Collector();
        collector.setId(new ObjectId("5ba136290be2d32568777fa8"));
        collector.setName("Sonar");
        Map<ObjectId, Set<ObjectId>> objectIdSetMap = testCollectorTaskWithGenericItem.processGenericItems(Lists.newArrayList("http://localhost:9000"));
        List<RelatedCollectorItem> relatedCollectorItems = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertEquals(0, objectIdSetMap.size());
        assertEquals(0, relatedCollectorItems.size());
    }

    private static GenericCollectorItem createGenericItem(String toolName, String rawData, String source, String collectorId, String buildId, String relatedCollectorItemId) {
        GenericCollectorItem genericCollectorItem = new GenericCollectorItem();
        genericCollectorItem.setCollectorId(new ObjectId(collectorId));
        genericCollectorItem.setToolName(toolName);
        genericCollectorItem.setBuildId(new ObjectId(buildId));
        genericCollectorItem.setRelatedCollectorItem(new ObjectId(relatedCollectorItemId));
        genericCollectorItem.setRawData(rawData);
        genericCollectorItem.setSource(source);
        return genericCollectorItem;
    }

}