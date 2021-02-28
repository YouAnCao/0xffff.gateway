package com.gateway.server.config;

/**
 * TODO
 * create by Lyon.Cao in 2021/02/28 21:58
 **/
public class Route {
    private int    id;
    private String description;
    private String topic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
