package io.ascending.training.controller;

import io.ascending.training.model.postgresModel.User;
import io.ascending.training.service.postgres.UserService;
import io.ascending.training.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/auth"})
public class AuthenticationController {
    @Autowired
    @Qualifier("postgresService")
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticate(@RequestBody User user) {
        Map<String, String> result = new HashMap<>();
        String token = "";
        try {
            User u = userService.getUserByCredential(user.getAccount(), user.getPassword());
            if (u == null) {
                result.put("error", "User Not Found");
                return ResponseEntity.status(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION).body(result);
            }
            token = JwtUtil.generateToken(u);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg == null) msg = "BAD REQUEST!";
            result.put("error", msg);
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(result);

        }

        result.put("token", token);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(result);
    }
}
