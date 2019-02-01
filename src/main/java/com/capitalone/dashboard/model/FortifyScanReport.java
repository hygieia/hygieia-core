package com.capitalone.dashboard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fortify_report")
@CompoundIndexes(value = { @CompoundIndex(name = "project_id", def = "{'projectId' : 1, 'collectorItemId': 1}") })
public class FortifyScanReport extends BaseModel {

	private ObjectId collectorItemId;
	private long timestamp;
	private String name;
	private String url;
	private long projectId;
	private String version;
	private Map<String, Threat> threats = new HashMap<>();

	public static class Threat {
		List<String> components = new ArrayList<>();
		int count;

		public Threat(int count) {
			this.count = count;
		}

		public Threat() {
			// TODO Auto-generated constructor stub
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public List<String> getComponents() {
			return components;
		}

		public void setComponents(List<String> components) {
			this.components = components;
		}
	}

	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public ObjectId getCollectorItemId() {
		return collectorItemId;
	}

	public void setCollectorItemId(ObjectId collectorItemId) {
		this.collectorItemId = collectorItemId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Threat> getThreats() {
		return threats;
	}

	public void addThreats(String level, String component) {
		Threat threatSet = threats.get(level);
		if (threatSet == null) {
			Threat threat = new Threat(1);
			threat.components.add(component);
			threats.put(level, threat);
		} else {
			if (threatSet.getComponents().contains(component)) {
				threatSet.setCount(threatSet.getCount() + 1);
			} else {
				threatSet.getComponents().add(component);
				threatSet.setCount(threatSet.getCount() + 1);
				threats.put(level, threatSet);
			}
		}
	}
}
