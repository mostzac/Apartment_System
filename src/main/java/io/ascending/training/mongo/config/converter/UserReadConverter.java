package io.ascending.training.mongo.config.converter;

import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.model.MongoUser;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

//@Component
@ReadingConverter
public class UserReadConverter implements Converter<Document, MongoUser> {
    @Override
    public MongoUser convert(Document document) {
        final MongoUser user = new MongoUser(document.get("name").toString(), (Integer) document.get("age"));
        if (document.get("message") != null) {
            final MongoMessage message = new MongoMessage(document.get("message.content").toString());
            user.setMessage(message);
        }
        return user;
    }
}
