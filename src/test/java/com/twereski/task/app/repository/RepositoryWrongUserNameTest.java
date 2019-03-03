package com.twereski.task.app.repository;

import com.twereski.task.app.exception.ExceptionDto;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryWrongUserNameTest extends RepositoryAbstractTest {

    @Test
    public void shouldReturnNotFoundError() throws Exception {

        stubFor(
                get(urlMatching("/users/wrong.*")).willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value()))
        );

        ResponseEntity<ExceptionDto> response = restTemplate.exchange(
                createURLWithPort("/repository/" + "wrong"),
                HttpMethod.GET, new HttpEntity<>(headers), ExceptionDto.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}