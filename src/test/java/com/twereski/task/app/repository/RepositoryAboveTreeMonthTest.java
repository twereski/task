package com.twereski.task.app.repository;

import com.twereski.task.app.dto.RepositoryDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryAboveTreeMonthTest extends RepositoryAbstractTest {

    @Test
    public void shouldReturnTwoOfTheThreeRepos() throws Exception {

        stabGithub("json/githubAbove.json");

        ResponseEntity<List<RepositoryDto>> response = invokeRepository("twereski?actual=true");

        List<RepositoryDto> body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(2);
        assertThat(body).filteredOn(
                r -> r.getName().equals("euro-draw-simulator") && r.isUpdatedLessThanThreeMonthAgo()).isNotEmpty();
        assertThat(body).filteredOn(r -> r.getName().equals("pretius")).isEmpty();

    }

}