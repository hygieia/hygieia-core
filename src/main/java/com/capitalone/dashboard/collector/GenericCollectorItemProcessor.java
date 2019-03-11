package com.capitalone.dashboard.collector;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.GenericCollectorItem;
import com.capitalone.dashboard.model.relation.RelatedCollectorItem;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.GenericCollectorItemRepository;
import com.capitalone.dashboard.repository.RelatedCollectorItemRepository;
import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class GenericCollectorItemProcessor<T extends Collector> {

    private final CollectorItemRepository collectorItemRepository;
    private final GenericCollectorItemRepository genericCollectorItemRepository;
    private final RelatedCollectorItemRepository relatedCollectorItemRepository;

    @Autowired
    public GenericCollectorItemProcessor(CollectorItemRepository collectorItemRepository, GenericCollectorItemRepository genericCollectorItemRepository, RelatedCollectorItemRepository relatedCollectorItemRepository) {
        this.collectorItemRepository = collectorItemRepository;
        this.genericCollectorItemRepository = genericCollectorItemRepository;
        this.relatedCollectorItemRepository = relatedCollectorItemRepository;
    }


    public Map<ObjectId, Set<ObjectId>> processGenericItems(T collector, List<String> toolServers, Map<String, Object> options) {
        List<GenericCollectorItem> genericCollectorItems = genericCollectorItemRepository.findAllByToolNameAndProcessTimeEquals(collector.getName(), 0L);
        Map<ObjectId, Set<ObjectId>> collectorItemBuildIds = new HashMap<>();
        genericCollectorItems.forEach(gci -> {
            toolServers.stream()
                    .map(server -> Lists.newArrayList(collectorItemRepository.findAllByOptionMapAndCollectorIdsIn(options, Lists.newArrayList(collector.getId()))))
                    .forEach(collectorItems -> collectorItems.forEach(item -> {
                        //Save as related item. Related Item event listener will process it.
                        if (!collectorItemBuildIds.containsKey(item.getId())) {
                            collectorItemBuildIds.put(item.getId(), new HashSet<>());
                        }
                        item.setEnabled(true);
                        collectorItemRepository.save(item);
                        collectorItemBuildIds.get(item.getId()).add(gci.getBuildId());
                        RelatedCollectorItem relatedCollectorItem = new RelatedCollectorItem();
                        relatedCollectorItem.setCreationTime(System.currentTimeMillis());
                        relatedCollectorItem.setLeft(gci.getRelatedCollectorItem());
                        relatedCollectorItem.setRight(item.getId());
                        relatedCollectorItem.setSource(gci.getSource());
                        relatedCollectorItemRepository.save(relatedCollectorItem);
                    }));
            // Save generic item as processed, ie, processing time non zero.
            gci.setProcessTime(System.currentTimeMillis());
            genericCollectorItemRepository.save(gci);
        });
        return collectorItemBuildIds;
    }

}
