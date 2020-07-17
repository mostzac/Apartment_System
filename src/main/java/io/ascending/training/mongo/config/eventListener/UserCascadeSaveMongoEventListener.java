package io.ascending.training.mongo.config.eventListener;

import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.model.MongoUser;
import io.ascending.training.mongo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;


// saving the user and message in the meantime, but in user document message field remains DBRef
public class UserCascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {
    @Autowired
    MessageRepository messageRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if ((source instanceof MongoUser) && (((MongoUser) source).getMessage() != null)) {
            messageRepository.save(((MongoUser) source).getMessage());
        }
    }
}
