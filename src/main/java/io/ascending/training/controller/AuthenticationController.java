package io.ascending.training.controller;

import io.ascending.training.postgres.model.Apartment;
import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.service.ApartmentService;
import io.ascending.training.postgres.service.UserService;
import io.ascending.training.util.JwtUtil;
import jdk.jfr.Unsigned;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/auth"})
public class AuthenticationController {
    @Autowired
    @Qualifier("postgresService")
    private UserService userService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity singup(@RequestBody User user, @RequestParam(name = "aptName") String aptName) {
        Apartment apartment = apartmentService.getApartmentByName(aptName);
        user.setApartment(apartment);
        userService.save(user);
        return ResponseEntity.ok().body("User registered successfully!");
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticate(@RequestBody User user) throws Exception {
        Authentication request = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
        Authentication principal = authenticationManager.authenticate(request);

        SecurityContextHolder.getContext().setAuthentication(principal);
        Map<String, String> result = new HashMap<>();
        user = userService.getUserByAccount(user.getAccount());
        String token = JwtUtil.generateToken(user);
        result.put("token", token);
        return ResponseEntity.ok(result);
/*
        Map<String, String> result = new HashMap<>();
        String token = "";

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword()));
        } catch (BadCredentialsException e) {
            result.put("Error", "Account or Password Incorrect!");
            return ResponseEntity.status(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION).body(result);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getAccount());
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
    }*/

    }

}
