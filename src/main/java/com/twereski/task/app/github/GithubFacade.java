package com.twereski.task.app.github;

import com.twereski.task.app.dto.RepositoryDto;
import com.twereski.task.app.dto.Sort;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.twereski.task.app.github.UserReposPredicate.isCompatibleWithUpdateFilter;
import static com.twereski.task.app.github.UserReposPredicate.isOlderThanDate;

@Service
public class GithubFacade {

    private static final int monthUpdated = 3;

    private final Clock clock;
    private final Invoker invoker;

    public GithubFacade(Clock clock, Invoker invoker) {
        this.clock = clock;
        this.invoker = invoker;
    }

    public List<RepositoryDto> getRepos(String userName, Boolean actual, Sort sort) {
        LocalDateTime updatedDate = LocalDateTime.now(clock).minusMonths(monthUpdated);
        return invoker.getUserRepositories(userName, sort)
                .stream()
                .filter(isCompatibleWithUpdateFilter(actual, updatedDate))
                .map( r ->
                        new RepositoryDto(r.getName(), r.getLanguage(), r.getCreated_at(), r.getUpdated_at(),
                                isOlderThanDate(updatedDate).test(r))
                )
                .collect(Collectors.toList());
    }
}
