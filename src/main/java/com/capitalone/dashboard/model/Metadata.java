package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Document(collection = "metadata")
@CompoundIndexes({
        @CompoundIndex(name = "unique_mdata_key_type", def = "{'key' : 1, 'type': 1}", unique = true)
})
public class Metadata extends BaseModel {

    @Indexed
    @NotNull
    private String key;

    @NotNull
    private String type;

    private String source;

    @NotNull
    private Object rawData;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Object getRawData() {
        return rawData;
    }

    public void setRawData(Object rawData) {
        this.rawData = rawData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metadata = (Metadata) o;
        return key.equals(metadata.key) &&
                type.equals(metadata.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, type);
    }
}
