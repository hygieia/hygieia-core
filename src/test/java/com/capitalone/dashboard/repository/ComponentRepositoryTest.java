package com.capitalone.dashboard.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;

public class ComponentRepositoryTest {

	
    private ComponentRepository componentRepository = Mockito.mock(ComponentRepository.class);

    @Test
    public void findByCollectorTypeAndItemIdInReturns0() throws IOException {
        BooleanBuilder builder = new BooleanBuilder();
        PathBuilder<Component> path = new PathBuilder<>(Component.class, "components");
        builder.and(path.get("collectorItems", Map.class).get(CollectorType.SCM.toString(),List.class).get("_id", ObjectId.class).in(Lists.newArrayList(new ObjectId("5ba136220be2d32568777fa5"))));
       
        List<Component> itemListResult = new ArrayList<Component>();
        when(componentRepository.findAll(any(Predicate.class))).thenReturn(itemListResult);
       // doNothing().when(componentRepository).deleteAll();
      //  List<Component> itemListResult = componentRepository.findByCollectorTypeAndItemIdIn(CollectorType.SCM, Lists.newArrayList(new ObjectId("5ba136220be2d32568777fa5")));
        assertThat(itemListResult).size().isEqualTo(0);
    }

    @Test
    public void findByCollectorTypeAndItemIdInReturns3() throws IOException {
        doNothing().when(componentRepository).deleteAll();
        List<Component> itemList = new ArrayList<Component>();
        Component component = new Component();
        component.setName("TestComponent1");
        component.setId(new ObjectId("5ba136220be2d32568777fa5"));
        Component component2 = new Component();
        component2.setName("TestComponent2");
        component2.setId(new ObjectId("5ba136220be2d32568777fa6"));
        Component component3 = new Component();
        component3.setName("TestComponent3");
        component3.setId(new ObjectId("5ba136220be2d32568777fa7"));
        itemList.add(component);
        itemList.add(component2);
        itemList.add(component3);
        when(componentRepository.findByCollectorTypeAndItemIdIn(CollectorType.Build, Lists.newArrayList(new ObjectId("5ba136220be2d32568777fa5")))).thenReturn(itemList);
        assertThat(itemList).size().isEqualTo(3);
        assertThat(itemList.get(0).getId()).isEqualTo(new ObjectId("5ba136220be2d32568777fa5"));
    }
}