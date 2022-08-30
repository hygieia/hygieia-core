package com.capitalone.dashboard.repository;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capitalone.dashboard.testutil.EmbeddedMongoConfig;
import com.capitalone.dashboard.testutil.EmbeddedMongoRule;


@ContextConfiguration(classes = { EmbeddedMongoConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public abstract class EmbeddedMongoBaseRepositoryTest {

    @Autowired
    @Rule
    public EmbeddedMongoRule embeddedMongoRule;

}
