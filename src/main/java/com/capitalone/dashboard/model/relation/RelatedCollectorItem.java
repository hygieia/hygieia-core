package com.capitalone.dashboard.model.relation;

import com.capitalone.dashboard.model.BaseModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Nonnull;

@Document(collection = "related_items")
public class RelatedCollectorItem extends BaseModel{
	@Nonnull
    private ObjectId left;
	@Nonnull
    private ObjectId right;
	@Nonnull
    private String source;
	@Nonnull
    private String reason;
    @Nonnull
    private long creationTime;

    public ObjectId getLeft() {
        return left;
    }

    public void setLeft(ObjectId left) {
        this.left = left;
    }

    public ObjectId getRight() {
        return right;
    }

    public void setRight(ObjectId right) {
        this.right = right;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
