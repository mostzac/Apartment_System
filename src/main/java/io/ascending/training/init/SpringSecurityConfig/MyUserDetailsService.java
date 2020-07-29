package io.ascending.training.init.SpringSecurityConfig;

import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.repository.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            User user = userDAO.getUserByAccount(s);
            return UserPrincipal.build(user);
        } catch (NullPointerException e) {
            throw new UsernameNotFoundException("User Not Found with -> account : " + s);
        }
    }
}
