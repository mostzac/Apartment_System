package io.ascending.training.mongo.config.eventListener;

import io.ascending.training.mongo.config.annotaion.CascadeSave;
import io.ascending.training.mongo.model.MongoMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.springframework.data.mongodb.core.query.Criteria.where;


// method from https://gist.github.com/heyomi/a4104ed45aab38098899f144681bbff7  https://www.baeldung.com/cascading-with-dbref-and-lifecycle-events-in-spring-data-mongodb
public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {
    @Autowired
    private MongoOperations mongoOperations;


    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
                ReflectionUtils.makeAccessible(field);

                if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
//                if (field.isAnnotationPresent(DBRef.class) || field.isAnnotationPresent(CascadeSave.class)) {
                    Object fieldValue = field.get(source);
                    if (fieldValue != null) {
                        final DbRefFieldCallback callback = new DbRefFieldCallback();
                        ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
                        if (!callback.isIdFound()) {
                            throw new MappingException("Cannot perform cascade save on child object without id set");
                        }
                        try {
                            mongoOperations.save(fieldValue);
                        } catch (DuplicateKeyException e) {
                            if (fieldValue instanceof MongoMessage) {
                                fieldValue = mongoOperations.findOne(Query.query(where("content").is(((MongoMessage) fieldValue).getContent())), MongoMessage.class);
                                field.set(source,fieldValue);
                            }
                        }
                    }

                }
            }
        });
    }

    private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
        private boolean idFound;


        @Override
        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                ReflectionUtils.makeAccessible(field);

                if (field.isAnnotationPresent(Id.class)) {
                    idFound = true;
                }
        }

        public boolean isIdFound() {
            return idFound;
        }
    }
}
