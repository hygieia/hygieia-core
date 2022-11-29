package com.capitalone.dashboard.event;

import com.capitalone.dashboard.event.constants.sync.Reason;
import com.capitalone.dashboard.event.sync.SyncDashboard;
import com.capitalone.dashboard.event.sync.SyncException;
import com.capitalone.dashboard.model.relation.RelatedCollectorItem;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.PipelineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;



@org.springframework.stereotype.Component
public class RelatedCollectorItemEventListener extends HygieiaMongoEventListener<RelatedCollectorItem> {
    private static final Logger LOG = LoggerFactory.getLogger(RelatedCollectorItemEventListener.class);
    private final SyncDashboard syncDashboard;


    @Autowired
    public RelatedCollectorItemEventListener(
            CollectorItemRepository collectorItemRepository,
            PipelineRepository pipelineRepository,
            CollectorRepository collectorRepository, SyncDashboard syncDashboard) {
        super(collectorItemRepository, pipelineRepository, collectorRepository);
        this.syncDashboard = syncDashboard;
    }

    @Override
    public void onAfterSave(AfterSaveEvent<RelatedCollectorItem> event) {
        RelatedCollectorItem relatedCollectorItem = event.getSource();
        try {
            syncDashboard.sync(relatedCollectorItem,!Reason.ARTIFACT_REASON.getAction().equalsIgnoreCase(relatedCollectorItem.getReason()));
        } catch (SyncException e) {
            LOG.error("Error processing related collector item. ID = " + relatedCollectorItem.getId() + ". Reason " + e.getMessage());
        }
    }
}
