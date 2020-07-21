package io.ascending.training.mongo.config.converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import io.ascending.training.mongo.model.MongoUser;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.query.Criteria.where;


//@Component
// using like this save() method will not update the original document
@WritingConverter
public class UserWriteConverter implements Converter<MongoUser, Document> {

    @Override
    public Document convert(final MongoUser mongoUser) {
        final Document document = new Document();
//        document.put("_id", mongoUser.getId());
        document.put("name", mongoUser.getName());
        document.put("age",mongoUser.getAge());
        if (mongoUser.getMessage() != null) {
            final Document messageDocument = new Document();
            messageDocument.put("_id",  mongoUser.getMessage().getId());
            messageDocument.put("content",mongoUser.getMessage().getContent());
            messageDocument.put("tags", mongoUser.getMessage().getTags());
            document.put("message", messageDocument);
        }
//        dbObject.removeField("_class");
        return document;
    }
}



//
//@Component
//@WritingConverter
//public class UserWriteConverter implements Converter<MongoUser, DBObject> {
//    @Override
//    public DBObject convert(final MongoUser mongoUser) {
//        final DBObject dbObject = new BasicDBObject();
//        dbObject.put("nameaaa", mongoUser.getName());
//        dbObject.put("age",mongoUser.getAge());
//        if (mongoUser.getMessage() != null) {
//            final DBObject messageDbObject = new BasicDBObject();
//            messageDbObject.put("content",mongoUser.getMessage().getContent());
//            messageDbObject.put("tags", mongoUser.getMessage().getTags());
//            dbObject.put("messagesssss", messageDbObject);
//        }
////        dbObject.removeField("_class");
//        return dbObject;
//    }
//}
