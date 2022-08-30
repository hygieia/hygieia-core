package com.capitalone.dashboard.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.testutil.EmbeddedMongoConfig;
import com.capitalone.dashboard.testutil.EmbeddedMongoRule;
import com.capitalone.dashboard.util.LoadTestData;
import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
@DirtiesContext
public class ComponentRepositoryTest {

    @Autowired
    @Rule
    public EmbeddedMongoRule embeddedMongoRule;

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