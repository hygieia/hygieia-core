package com.capitalone.dashboard.model.adapter;

import com.capitalone.dashboard.model.BuildStage;
import com.capitalone.dashboard.model.Error;
import com.capitalone.dashboard.model.FeatureFlag;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

public class FeatureFlagAdapter implements JsonDeserializer<FeatureFlag> {


    @Override
    public FeatureFlag deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        FeatureFlag featureFlag = new FeatureFlag();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject == null) return null;
        featureFlag.setScm(getBoolean(jsonObject, "scm"));
        featureFlag.setBuild(getBoolean(jsonObject, "build"));
        featureFlag.setArtifact(getBoolean(jsonObject, "artifact"));
        featureFlag.setCodeQuality(getBoolean(jsonObject, "codequality"));
        featureFlag.setLibraryPolicy(getBoolean(jsonObject, "librarypolicy"));
        featureFlag.setStaticSecurity(getBoolean(jsonObject, "staticsecurity"));
        featureFlag.setTest(getBoolean(jsonObject, "test"));
        featureFlag.setAgileTool(getBoolean(jsonObject, "agiletool"));
        featureFlag.setDeployment(getBoolean(jsonObject, "deployment"));
        return featureFlag;
    }

    private boolean getBoolean(JsonObject jsonObject, String key) {
        if (jsonObject == null || jsonObject.get(key) == null) return false;
        return jsonObject.get(key).getAsBoolean();
    }


}
