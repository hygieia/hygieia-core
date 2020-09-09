package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.testutil.FongoConfig;
import com.capitalone.dashboard.util.LoadTestData;
import com.github.fakemongo.junit.FongoRule;
import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FongoConfig.class})
@DirtiesContext
public class ComponentRepositoryTest {

    @Rule
    public FongoRule fongoRule = new FongoRule();

    @Autowired
    private ComponentRepository componentRepository;

    @Test
    public void findByCollectorTypeAndItemIdInReturns0() throws IOException {
        componentRepository.deleteAll();
        LoadTestData.loadComponent(componentRepository);
        Iterable<Component> items = componentRepository.findByCollectorTypeAndItemIdIn(CollectorType.SCM, Lists.newArrayList(new ObjectId("5ba136220be2d32568777fa5")));
        List<Component> itemList = Lists.newArrayList(items);
        assertThat(itemList, hasSize(0));
    }

    @Test
    public void findByCollectorTypeAndItemIdInReturns3() throws IOException {
        componentRepository.deleteAll();
        LoadTestData.loadComponent(componentRepository);
        Iterable<Component> items = componentRepository.findByCollectorTypeAndItemIdIn(CollectorType.Build, Lists.newArrayList(new ObjectId("5ba136220be2d32568777fa5")));
        List<Component> itemList = Lists.newArrayList(items);
        assertThat(itemList, hasSize(3));
        assertThat(itemList.get(0).getId().toHexString(), equalTo("5ba155820be2d339d2f24626"));
    }
}