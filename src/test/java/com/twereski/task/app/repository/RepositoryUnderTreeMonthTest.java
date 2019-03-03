package com.twereski.task.app.repository;

import com.twereski.task.app.dto.RepositoryDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryUnderTreeMonthTest extends RepositoryAbstractTest {


    @Test
    public void shouldReturnTwoOfTheThreeRepos() throws Exception {
        stabGithub("json/githubUnder.json");

        ResponseEntity<List<RepositoryDto>> response = invokeRepository("twereski?actual=false");

        List<RepositoryDto> body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(2);
        assertThat(body).filteredOn(
                r -> r.getName().equals("events") && r.isUpdatedMoreThanThreeMonthAgo()).isNotEmpty();
        assertThat(body).filteredOn(r -> r.getName().equals("euro-draw-simulator")).isEmpty();
        assertThat(body).filteredOn(RepositoryDto::isUpdatedMoreThanThreeMonthAgo).size().isEqualTo(2);

    }
}