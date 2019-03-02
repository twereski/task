package com.twereski.task.app.github;

import com.twereski.task.app.dto.Sort;
import com.twereski.task.app.github.dto.UserReposDto;
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
            ResponseEntity<List<UserReposDto>> responseA = restTemplate.exchange(
                    builder.toUri(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserReposDto>>(){}
            );

            return responseA.getBody();

        } catch (HttpClientErrorException e) {
            if(e.getStatusCode().is4xxClientError()) {
                throw new GithubException(e.getMessage(), e);
            }

            if(e.getStatusCode().is5xxServerError()) {
                throw new GithubException(e.getMessage(), e);
            }
            throw new GithubException(e.getMessage(), e);
        }
    }

}
