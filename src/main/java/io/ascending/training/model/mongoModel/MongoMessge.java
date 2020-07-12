package io.ascending.training.model.mongoModel;


import org.springframework.data.annotation.Id;

public class MongoMessge {
    @Id
    private String id;
    private String content;

    public MongoMessge(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
