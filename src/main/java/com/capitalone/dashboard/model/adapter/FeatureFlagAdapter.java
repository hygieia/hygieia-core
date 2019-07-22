package com.capitalone.dashboard.model.adapter;

import com.capitalone.dashboard.model.FeatureFlag;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class FeatureFlagAdapter implements JsonDeserializer<FeatureFlag> {


    @Override
    public FeatureFlag deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        FeatureFlag featureFlag = new FeatureFlag();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject == null) return null;
        Map<String, Boolean> b = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        JsonObject flags = jsonObject.getAsJsonObject("flags");
        Set<Map.Entry<String, JsonElement>> entries = flags.entrySet();
        for (Map.Entry<String, JsonElement> entry: entries) {
          b.put(entry.getKey(),getBoolean(flags, entry.getKey()));
        }
        featureFlag.setFlags(b);
        featureFlag.setName(getStringValue(jsonObject,"name"));
        featureFlag.setDescription(getStringValue(jsonObject,"description"));
        return featureFlag;
    }

    private boolean getBoolean(JsonObject jsonObject, String key) {
        if (jsonObject == null || jsonObject.get(key) == null) return false;
        return jsonObject.get(key).getAsBoolean();
    }
    private String getStringValue(JsonObject jsonObject, String key) {
        if (jsonObject == null || jsonObject.get(key) == null) return null;
        return jsonObject.get(key).getAsString();
    }


}
