package com.capitalone.dashboard.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capitalone.dashboard.model.relation.RelatedCollectorItem;
import com.google.common.collect.Lists;

@ExtendWith(MockitoExtension.class)
public class RelatedCollectorItemRepositoryTest {

    private RelatedCollectorItemRepository relatedCollectorItemRepository = Mockito.mock(RelatedCollectorItemRepository.class);

    @Test
    public void saveRelatedItems() {
        doNothing().when(relatedCollectorItemRepository).deleteAll();
        ObjectId left = ObjectId.get();
        ObjectId right = ObjectId.get();
        doCallRealMethod().when(relatedCollectorItemRepository).saveRelatedItems(left, right, "some source", "some reason");
        List<RelatedCollectorItem> relatedCollectorItemList = new ArrayList<RelatedCollectorItem>();
        RelatedCollectorItem rcItem = new RelatedCollectorItem();
        rcItem.setLeft(left);
        rcItem.setRight(right);
        rcItem.setReason("some reason");
        rcItem.setSource("some source");
        relatedCollectorItemList.add(rcItem);


        when(relatedCollectorItemRepository.findAllByLeftAndRight(left, right)).thenReturn(relatedCollectorItemList);


        // List<RelatedCollectorItem> relatedCollectorItemList = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertFalse(CollectionUtils.isEmpty(relatedCollectorItemList));
        assertEquals(1, relatedCollectorItemList.size());
        assertEquals(relatedCollectorItemList.get(0).getLeft(), left);
        assertEquals(relatedCollectorItemList.get(0).getRight(), right);
        assertTrue(relatedCollectorItemList.get(0).getReason().equalsIgnoreCase("some reason"));
        assertTrue(relatedCollectorItemList.get(0).getSource().equalsIgnoreCase("some source"));

    }

    @Test
    public void saveRelatedItemsDuplicate() {
        relatedCollectorItemRepository.deleteAll();
        List<RelatedCollectorItem> items = new ArrayList<RelatedCollectorItem>();
        Iterable<RelatedCollectorItem> itemIt = items;
        ObjectId left = ObjectId.get();
        ObjectId right = ObjectId.get();
        RelatedCollectorItem rc = new RelatedCollectorItem();
        rc.setId(new ObjectId());
        rc.setLeft(left);
        rc.setRight(right);
        rc.setSource("some source");
        rc.setReason("some reason");
        items.add(rc);

        RelatedCollectorItem rc2 = new RelatedCollectorItem();
        rc2.setId(new ObjectId());
        rc2.setLeft(left);
        rc2.setRight(right);
        rc2.setSource("some source");
        rc2.setReason("some reason");


        when(relatedCollectorItemRepository.findAll()).thenReturn(itemIt);

        when(relatedCollectorItemRepository.save(rc)).thenReturn(rc);
        RelatedCollectorItem saved = relatedCollectorItemRepository.save(rc);

        when(relatedCollectorItemRepository.saveRelatedItems(left, right, "some source", "some reason")).thenReturn(rc2);
        RelatedCollectorItem savedAgain = relatedCollectorItemRepository.saveRelatedItems(left, right, "some source", "some reason");

        List<RelatedCollectorItem> relatedCollectorItemList = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertTrue(!CollectionUtils.isEmpty(relatedCollectorItemList));
        assertEquals(1, relatedCollectorItemList.size());
        assertEquals(relatedCollectorItemList.get(0).getLeft(), left);
        assertEquals(relatedCollectorItemList.get(0).getRight(), right);
        assertTrue(relatedCollectorItemList.get(0).getReason().equalsIgnoreCase("some reason"));
        assertTrue(relatedCollectorItemList.get(0).getSource().equalsIgnoreCase("some source"));
        assertTrue(!Objects.equals(saved.getId(), savedAgain.getId()));


    }

    @Test
    public void saveRelatedItemsDuplicateMany() {
        relatedCollectorItemRepository.deleteAll();

        List<RelatedCollectorItem> items = new ArrayList<RelatedCollectorItem>();

        ObjectId left = ObjectId.get();
        ObjectId right = ObjectId.get();
        for (int i = 0; i < 5; i++) {
            RelatedCollectorItem rc = new RelatedCollectorItem();
            rc.setLeft(left);
            rc.setRight(right);
            rc.setSource("some source");
            rc.setReason("some reason");

            relatedCollectorItemRepository.save(rc);

            if (i==0) {
                items.add(rc);
            }
        }

        Iterable<RelatedCollectorItem> itemsIt = items;

        when(relatedCollectorItemRepository.findAll()).thenReturn(itemsIt);

        RelatedCollectorItem saved = new RelatedCollectorItem();

        saved.setLeft(left);
        saved.setRight(right);
        saved.setCreationTime(System.currentTimeMillis());
        saved.setSource("some source");
        saved.setReason("some reason");
        saved.setId(new ObjectId());

        when(relatedCollectorItemRepository.save(saved)).thenReturn(saved);

        relatedCollectorItemRepository.save(saved);

        assertEquals(relatedCollectorItemRepository.findAll().iterator().next(),
                items.get(0));

        RelatedCollectorItem savedAgain = new RelatedCollectorItem();
        savedAgain.setLeft(left);
        savedAgain.setRight(right);
        savedAgain.setCreationTime(System.currentTimeMillis());
        savedAgain.setSource("some source");
        savedAgain.setReason("some reason");
        savedAgain.setId(new ObjectId());

        when(relatedCollectorItemRepository.saveRelatedItems(left, right, "some source", "some reason"))
                .thenReturn(savedAgain);

        assertEquals(relatedCollectorItemRepository
                .saveRelatedItems(left, right, "some source", "some reason"),savedAgain);

        List<RelatedCollectorItem> relatedCollectorItemList = Lists.newArrayList(relatedCollectorItemRepository.findAll());
        assertTrue(!CollectionUtils.isEmpty(relatedCollectorItemList));
        assertEquals(1, relatedCollectorItemList.size());
        assertEquals(relatedCollectorItemList.get(0).getLeft(), left);
        assertEquals(relatedCollectorItemList.get(0).getRight(), right);
        assertTrue(relatedCollectorItemList.get(0).getReason().equalsIgnoreCase("some reason"));
        assertTrue(relatedCollectorItemList.get(0).getSource().equalsIgnoreCase("some source"));
        assertTrue(!Objects.equals(saved.getId(), savedAgain.getId()));

        verify(relatedCollectorItemRepository, times(2)).findAll();
        verify(relatedCollectorItemRepository).saveRelatedItems(left, right, "some source", "some reason");
        verify(relatedCollectorItemRepository).save(saved);
    }

}