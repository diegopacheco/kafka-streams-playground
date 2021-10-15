package com.github.diegopacheco.kafka.streams.model;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class Tweet {

    @SerializedName("created_at")
    private Long createdAt;

    @SerializedName("id")
    private Long id;

    @SerializedName("lang")
    private String language;

    @SerializedName("retweet")
    private Boolean retweet;

    @SerializedName("text")
    private String text;

    @SerializedName("additionalProps")
    private Map<String,String> additionalProps = new HashMap<>();

    public Long getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean isRetweet() {
        return retweet;
    }
    public void setRetweet(Boolean retweet) {
        this.retweet = retweet;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public Boolean getRetweet() {
        return retweet;
    }

    public Map<String, String> getAdditionalProps() {
        return (null!=additionalProps) ? additionalProps : new HashMap<>();
    }
    public void setAdditionalProps(Map<String, String> additionalProps) {
        this.additionalProps = additionalProps;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", language='" + language + '\'' +
                ", retweet=" + retweet +
                ", text='" + text + '\'' +
                ", additionalProps=" + getAdditionalProps() +
                '}';
    }
}
