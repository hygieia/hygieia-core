package com.capitalone.dashboard.collector;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.GenericCollectorItem;
import com.capitalone.dashboard.repository.BaseCollectorRepository;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.GenericCollectorItemRepository;
import com.capitalone.dashboard.repository.RelatedCollectorItemRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TestCollectorTaskTwoWithGenericItem extends CollectorTaskWithGenericItem<Collector> {

    private final CollectorRepository collectorRepository;

    @Autowired
    public TestCollectorTaskTwoWithGenericItem(TaskScheduler taskScheduler, CollectorItemRepository collectorItemRepository, GenericCollectorItemRepository genericCollectorItemRepository, RelatedCollectorItemRepository relatedCollectorItemRepository,
                                               CollectorRepository collectorRepository) {
        super(taskScheduler, "MyTool", collectorItemRepository, genericCollectorItemRepository, relatedCollectorItemRepository);
        this.collectorRepository = collectorRepository;
    }

    @Override
    public Map<String, Object> getGenericCollectorItemOptions(String serverUrl, GenericCollectorItem genericCollectorItem) {
        Map<String, Object> options = new HashMap<>();
        options.put("projectName", "C1 Custom Env Var Inject");
        options.put("instanceUrl", "http://localhost:9000");
        return options;
    }

    @Override
    public Collector getCollector() {
        Collector collector = new Collector();
        collector.setId(new ObjectId("5ba136290be2d32568777fa8"));
        collector.setName("MyTool");
        return collector;
    }

    @Override
    public BaseCollectorRepository<Collector> getCollectorRepository() {
        return collectorRepository;
    }

    @Override
    public String getCron() {
        return "* * * * * *";
    }

    @Override
    public void collect(Collector collector) { }
}