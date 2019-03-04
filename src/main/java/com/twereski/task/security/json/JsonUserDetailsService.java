package com.twereski.task.security.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Log4j2
@Component
public class JsonUserDetailsService implements UserDetailsService {

    private final ObjectMapper mapper;
    private final String path;

    public JsonUserDetailsService(@Value("${credentials.path}") String path) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(JsonParser.Feature.ALLOW_COMMENTS);

        this.mapper = mapper;
        this.path = path;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return getUsersList().stream().filter(u -> u.getUsername().equals(s)).findFirst()
                .orElseThrow(() -> new UsernameNotFoundException(s));

    }

    private List<User> getUsersList() {
        TypeReference<List<User>> userType = new TypeReference<List<User>>() {
        };
        InputStream is = TypeReference.class.getResourceAsStream(path);

        try {
            return mapper.readValue(is, userType);
        } catch (IOException e) {
            log.error("Failed to read credential file", e);
            throw new UsernameNotFoundException("Failed to read credential file");
        }
    }
}
