package io.ascending.training.model.mongoModel;


import org.springframework.data.annotation.Id;

public class MongoMessage {
    @Id
    private String id;
    private String content;
    private String[] tags;

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public MongoMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
