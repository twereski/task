package com.twereski.task.app.repository;

import com.twereski.task.app.dto.RepositoryDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryUserNameTest extends RepositoryAbstractTest {

    @Test
    public void shouldHaveRepoWithCorrectName() throws Exception {

        stabGithub("json/githubUserName.json");

        ResponseEntity<List<RepositoryDto>> response = invokeRepository("twereski");

        List<RepositoryDto> body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(3);
        assertThat(body).filteredOn(r -> r.getName().equals("testPoNazwie")).isNotEmpty();

    }
}