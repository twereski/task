package com.twereski.task.app.github;

import com.twereski.task.app.github.dto.UserReposDto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Predicate;

class UserReposPredicate {

    static Predicate<UserReposDto> isCompatibleWithUpdateFilter(Boolean fresh, LocalDateTime dateUpdated) {
        return r -> {
            if (Objects.isNull(fresh)) {
                return true;
            }
            if (fresh) {
                return r.getUpdated_at().isAfter(dateUpdated);
            } else {
                return r.getUpdated_at().isBefore(dateUpdated);
            }
        };
    }

    static Predicate<UserReposDto> isEarlierThanDate(LocalDateTime date) {
        return r -> r.getUpdated_at().isAfter(date);
    }

}
