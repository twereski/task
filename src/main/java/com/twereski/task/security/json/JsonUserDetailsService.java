package com.twereski.task.security.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class JsonUserDetailsService implements UserDetailsService {

    private List<User> users;

    public JsonUserDetailsService(@Value("${credentials.path}") String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(JsonParser.Feature.ALLOW_COMMENTS);

        TypeReference<List<User>> userType = new TypeReference<List<User>>() {};
        InputStream is = TypeReference.class.getResourceAsStream(path);

        users = mapper.readValue(is, userType);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return users.stream().filter(u -> u.getUsername().equals(s)).findFirst()
                .orElseThrow(() -> new UsernameNotFoundException(s));

    }
}
