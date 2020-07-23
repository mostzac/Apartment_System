package io.ascending.training.websocket;

import io.ascending.training.websocket.Greeting;
import io.ascending.training.websocket.HelloMessage;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class GreetingController {

    @MessageMapping("/sayHello")
    @SendTo("/topic/greetings")
    public Greeting sayHello(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


}
