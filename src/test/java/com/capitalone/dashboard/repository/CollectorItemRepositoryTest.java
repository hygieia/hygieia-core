package com.capitalone.dashboard.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capitalone.dashboard.model.CollectorItem;

@ExtendWith(MockitoExtension.class)
public class CollectorItemRepositoryTest {

	private CollectorItemRepository collectorItemRepository = Mockito.mock(CollectorItemRepository.class);

	@Test
	public void findAllByOptionNameValueAndCollectorIdsInReturns0() throws IOException {
		doNothing().when(collectorItemRepository).deleteAll();

		List<CollectorItem> itemList = new ArrayList<CollectorItem>();

		when(collectorItemRepository.findAllByOptionNameValueAndCollectorIdsIn("jobName", "job/",
				Collections.singletonList(new ObjectId("5ba136220be2d32568777fa4")))).thenReturn(itemList);
		assertEquals(itemList.size(), 0);
	}

	@Test
	public void findAllByOptionNameValueAndCollectorIdsInReturns1() throws IOException {
		doNothing().when(collectorItemRepository).deleteAll();

		List<CollectorItem> itemList = new ArrayList<CollectorItem>();
		CollectorItem item = new CollectorItem();
		item.setId(new ObjectId("5ba136220be2d32568777fa4"));
		itemList.add(item);

		when(collectorItemRepository.findAllByOptionNameValueAndCollectorIdsIn("jobName",
				"job/c1usercheck/", Collections.singletonList(new ObjectId("5ba136220be2d32568777fa4")))).thenReturn(itemList);
		assertEquals(itemList.size(), 1);
		assertEquals(itemList.get(0).getId().toHexString(), "5ba136220be2d32568777fa4");
	}

	@Test
	public void findAllByOptionNameValueAndCollectorIdsInReturns2() throws IOException {
		doNothing().when(collectorItemRepository).deleteAll();

		List<CollectorItem> itemList = new ArrayList<CollectorItem>();
		CollectorItem item = new CollectorItem();
		item.setId(new ObjectId("5ba136220be2d32568777fa5"));
		
		CollectorItem item2 = new CollectorItem();
		item.setId(new ObjectId("5ba136220be2d32568777fa6"));
		
		itemList.add(item);
		itemList.add(item2);

		when(collectorItemRepository.findAllByOptionNameValueAndCollectorIdsIn("instanceUrl",
				"http://localhost:8082/", Collections.singletonList(new ObjectId("5ba136220be2d32568777fa4")))).thenReturn(itemList);

		assertEquals(itemList.size(), 2);
		assertEquals(itemList.get(0).getId(), new ObjectId("5ba136220be2d32568777fa5"));
		assertEquals(itemList.get(1).getId(), new ObjectId("5ba136220be2d32568777fa6"));
		
	}

	@Test
	public void findAllByOptionMapAndCollectorIdsIn2MapEntry() throws IOException {
		doNothing().when(collectorItemRepository).deleteAll();
		
		Map<String, Object> inMap = new HashMap<>();
		inMap.put("jobName", "job/c1usercheck/");
		inMap.put("instanceUrl", "http://localhost:8082/");

		List<CollectorItem> itemList = new ArrayList<CollectorItem>();
		CollectorItem item = new CollectorItem();
		item.setId(new ObjectId("5ba136220be2d32568777fa5"));
		
		
		itemList.add(item);

		when(collectorItemRepository.findAllByOptionMapAndCollectorIdsIn(inMap,
				Collections.singletonList(new ObjectId("5ba136220be2d32568777fa4")))).thenReturn(itemList);
		assertEquals(itemList.size(), 1);
		assertEquals(itemList.get(0).getId().toHexString(), "5ba136220be2d32568777fa5");
	}

	@Test
	public void findAllByOptionMapAndCollectorIdsInStringAndNumber() throws IOException {
		doNothing().when(collectorItemRepository).deleteAll();
		Map<String, Object> inMap = new HashMap<>();
		inMap.put("jobName", "jobname");
		inMap.put("jobNumber", 123456789);
		
		List<CollectorItem> itemList = new ArrayList<CollectorItem>();
		CollectorItem item = new CollectorItem();
		item.setId(new ObjectId("5ba136220be2d32568777fa7"));
		itemList.add(item);

		when(collectorItemRepository.findAllByOptionMapAndCollectorIdsIn(inMap,
				Collections.singletonList(new ObjectId("5ba136220be2d32568777fff")))).thenReturn(itemList);
		assertEquals(itemList.size(), 1);
		assertEquals(itemList.get(0).getId().toHexString(), "5ba136220be2d32568777fa7");
	}
}