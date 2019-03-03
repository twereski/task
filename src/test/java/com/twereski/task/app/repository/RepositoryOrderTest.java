package com.twereski.task.app.repository;

import com.twereski.task.app.dto.RepositoryDto;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class RepositoryOrderTest extends RepositoryAbstractTest {

    @Test
    public void shouldPastDESCToInvoker() throws Exception {

        stabGithub("/json/githubOrder.json");

        ResponseEntity<List<RepositoryDto>> response = invokeRepository("twereski?sort=DESC");

        verify(getRequestedFor(urlEqualTo("/users/twereski/repos?direction=DESC")));
    }
}