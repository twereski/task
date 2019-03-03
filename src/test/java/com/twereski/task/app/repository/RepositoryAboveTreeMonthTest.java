package com.twereski.task.app.repository;

import com.twereski.task.app.dto.RepositoryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
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
                r -> r.getName().equals("euro-draw-simulator") && !r.isUpdatedMoreThanThreeMonthAgo()).isNotEmpty();
        assertThat(body).filteredOn(r -> r.getName().equals("pretius")).isEmpty();

    }

}