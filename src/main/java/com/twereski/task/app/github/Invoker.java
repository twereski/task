package com.twereski.task.app.github;

import com.twereski.task.app.dto.Sort;
import com.twereski.task.app.exception.RepositoryException;
import com.twereski.task.app.github.dto.UserReposDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class Invoker {

    @Value("${github.api.url}")
    private String url;

    private final String userRepos = "users/{userName}/repos?direction={direction}";

    List<UserReposDto> getUserRepositories(String user, Sort sort) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("userName", user);
        params.put("direction", sort.name());

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url + userRepos)
                .buildAndExpand(params);

        try {
            ResponseEntity<List<UserReposDto>> response = restTemplate.exchange(
                    builder.toUri(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserReposDto>>() {
                    }
            );

            return response.getBody();

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                log.warn(e.getMessage(), e);
                throw new RepositoryException(RepositoryException.Code.CLIENT_FAILED, "Wrong github username", e);
            }

            log.error(e.getMessage(), e);
            if (e.getStatusCode().is5xxServerError()) {
                throw new RepositoryException(RepositoryException.Code.SERVER_FAILED, "Github - server internal error", e);
            }
            throw new RepositoryException(RepositoryException.Code.SERVER_FAILED, "Github - error with connection", e);
        }
    }

}
