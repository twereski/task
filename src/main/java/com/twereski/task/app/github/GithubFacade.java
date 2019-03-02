package com.twereski.task.app.github;

import com.twereski.task.app.dto.RepositoryDto;
import com.twereski.task.app.dto.Sort;
import com.twereski.task.app.github.dto.UserReposDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GithubFacade {

    private final Invoker invoker;

    public List<RepositoryDto> getRepos(String userName, Boolean actual, Sort sort) {
        return invoker.getUserRepositories(userName, sort)
                .stream()
                .filter(isCompatibleWithUpdateFilter(actual))
                .map( r ->
                        new RepositoryDto(r.getName(), r.getLanguage(), r.getCreated_at(), r.getUpdated_at(),
                                isOlderThanThreeMonths().test(r))
                )
                .collect(Collectors.toList());
    }

    private static Predicate<UserReposDto> isCompatibleWithUpdateFilter(Boolean fresh) {
        return r -> {
            if(Objects.isNull(fresh)) {
                return true;
            }
            LocalDateTime minusThreeMonths = LocalDateTime.now().minusMonths(3);
            if (fresh) {
                return  r.getUpdated_at().isAfter(minusThreeMonths);
            } else {
                return r.getUpdated_at().isBefore(minusThreeMonths);
            }
        };
    }

    private static Predicate<UserReposDto> isOlderThanThreeMonths() {
        return r -> r.getUpdated_at().isBefore(LocalDateTime.now().minusMonths(3));
    }
}
