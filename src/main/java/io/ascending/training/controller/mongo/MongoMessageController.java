package io.ascending.training.controller.mongo;

import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.repository.MessageRepository;
import io.ascending.training.mongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mongo/messages")
public class MongoMessageController {
    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<MongoMessage> getMessages() {
        return messageRepository.findAll();
    }

    @RequestMapping(value = "", method = RequestMethod.GET,params ={"content"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<MongoMessage> getMessagesContains(@RequestParam(name = "content") String content) {
        return messageRepository.findAllByContentRegex(content);
    }
}
