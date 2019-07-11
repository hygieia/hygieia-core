package com.capitalone.dashboard.model;


import java.util.Map;

public class BuildStage extends BaseModel {

    private String stageId;
    private String name;
    private String status;
    private String startTimeMillis;
    private String endTime;
    private String log;
    private String parentId;
    private Error error;
    private String durationMillis;
    private Map<String,Object> _links;
    private String exec_node_logUrl;

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    public String getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(String startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public String getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(String durationMillis) {
        this.durationMillis = durationMillis;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getExec_node_logUrl() {
        return exec_node_logUrl;
    }

    public void setExec_node_logUrl(String exec_node_logUrl) {
        this.exec_node_logUrl = exec_node_logUrl;
    }
    public void set_links(Map<String, Object> _links) {
        this._links = _links;
    }


    public Map<String, Object> get_links() {
        return _links;
    }



}
