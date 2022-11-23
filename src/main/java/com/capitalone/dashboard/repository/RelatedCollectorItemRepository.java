package com.capitalone.dashboard.repository;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.capitalone.dashboard.model.relation.RelatedCollectorItem;

/**
 * Repository for {@link RelatedCollectorItem} data.
 */
public interface RelatedCollectorItemRepository extends CrudRepository<RelatedCollectorItem, ObjectId>, QuerydslPredicateExecutor<RelatedCollectorItem> {
    List<RelatedCollectorItem> findRelatedCollectorItemByLeft(ObjectId left);
    List<RelatedCollectorItem> findRelatedCollectorItemByRight(ObjectId right);
    List<RelatedCollectorItem> findAllByLeftAndRight(ObjectId left, ObjectId right);

    default RelatedCollectorItem saveRelatedItems(ObjectId left, ObjectId right, String source, String reason) {
        List<RelatedCollectorItem> items = findAllByLeftAndRight(left, right);
        if (!CollectionUtils.isEmpty(items)) {
            this.deleteAll(items);
        }
        RelatedCollectorItem related = new RelatedCollectorItem();
        related.setLeft(left);
        related.setRight(right);
        related.setCreationTime(System.currentTimeMillis());
        related.setSource(source);
        related.setReason(reason);
        return save(related);
    }
    List<RelatedCollectorItem> findAllByCreationTimeIsBetweenOrderByCreationTimeDesc(long beginDate, long endDate);
    List<RelatedCollectorItem> findAllByReasonAndCreationTimeIsBetweenOrderByCreationTimeDesc(String reason, long beginDate, long endDate);
}
