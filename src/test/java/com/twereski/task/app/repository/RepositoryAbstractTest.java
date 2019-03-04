package com.twereski.task.app.repository;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.twereski.task.app.dto.RepositoryDto;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Ignore
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class RepositoryAbstractTest {

    @LocalServerPort
    protected int port;
    protected TestRestTemplate restTemplate = new TestRestTemplate();
    protected HttpHeaders headers = new HttpHeaders();

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);


    @Before
    public void setup() throws Exception {
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        headers.add("Authorization", "Basic dG9tOmFiY2Rl");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

    }

    protected void stabGithub(String filePath) {
        stubFor(
                get(
                        urlMatching("/users/.*"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withBodyFile(filePath)
                        )
        );

    }

    protected ResponseEntity<List<RepositoryDto>> invokeRepository(String path) {
        return restTemplate.exchange(
                createURLWithPort("/repository/" + path),
                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<RepositoryDto>>() {
                });
    }


    protected String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}