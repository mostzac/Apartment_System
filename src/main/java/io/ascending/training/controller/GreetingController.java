package io.ascending.training.controller;

import io.ascending.training.websocket.Greeting;
import io.ascending.training.websocket.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
@CrossOrigin(value = "*")
public class GreetingController {
    @MessageMapping("/sayHello")
    @SendTo("/topic/greetings")
    public Greeting sayHello(@Payload HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


}
