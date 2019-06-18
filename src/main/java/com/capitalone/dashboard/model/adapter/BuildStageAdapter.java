package com.capitalone.dashboard.model.adapter;

import com.capitalone.dashboard.model.BuildStage;
import com.capitalone.dashboard.model.Error;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

public class BuildStageAdapter implements JsonDeserializer<BuildStage> {


    @Override
    public BuildStage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        BuildStage buildStage = new BuildStage();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject == null) return null;
        buildStage.setStageId(getStringValue(jsonObject, "id"));
        buildStage.setName(getStringValue(jsonObject, "name"));
        buildStage.setStatus(getStringValue(jsonObject, "status"));
        buildStage.setStartTimeMillis(getStringValue(jsonObject, "startTimeMillis"));
        buildStage.setEndTime(getStringValue(jsonObject, "endTime"));
        buildStage.setLog(getStringValue(jsonObject, "log"));
        buildStage.setParentId(getStringValue(jsonObject, "parentId"));
        buildStage.setError(new Gson().fromJson(jsonObject.get("error"),Error.class));
        buildStage.setDurationMillis(getStringValue(jsonObject, "durationMillis"));
        JsonElement linksElement = jsonObject.get("_links");
        if (linksElement != null) {
            buildStage.set_links(new Gson().fromJson(linksElement, Map.class));
        }
        buildStage.setExec_node_logUrl(getStringValue(jsonObject, "exec_node_logUrl"));
        return buildStage;
    }

    private String getStringValue(JsonObject jsonObject, String key) {
        if (jsonObject == null || jsonObject.get(key) == null) return null;
        return jsonObject.get(key).getAsString();
    }


}
