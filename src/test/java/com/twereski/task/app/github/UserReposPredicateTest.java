package com.twereski.task.app.github;

import com.twereski.task.app.github.dto.UserReposDto;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class UserReposPredicateTest {

    @Test
    public void isCompatibleWithUpdateFilterForEarlier() {

        //Given
        UserReposDto userReposDto = new UserReposDto();
        userReposDto.setUpdated_at(LocalDateTime.now().minusMonths(1));

        //AND
        Predicate<UserReposDto> compatibleWithUpdateFilter =
                UserReposPredicate.isCompatibleWithUpdateFilter(true, LocalDateTime.now());

        //WHEN
        boolean actual = compatibleWithUpdateFilter.test(userReposDto);

        //THEN
        Assert.assertFalse(actual);
    }

    @Test
    public void isCompatibleWithUpdateFilterForOlder() {

        //Given
        UserReposDto userReposDto = new UserReposDto();
        userReposDto.setUpdated_at(LocalDateTime.now());

        //AND
        Predicate<UserReposDto> compatibleWithUpdateFilter =
                UserReposPredicate.isCompatibleWithUpdateFilter(true, LocalDateTime.now().minusMonths(1));

        //WHEN
        boolean actual = compatibleWithUpdateFilter.test(userReposDto);

        //THEN
        Assert.assertTrue(actual);
    }

    @Test
    public void isCompatibleWithUpdateFilterForNull() {

        //Given
        UserReposDto userReposDto = new UserReposDto();

        //AND
        Predicate<UserReposDto> compatibleWithUpdateFilter =
                UserReposPredicate.isCompatibleWithUpdateFilter(null, LocalDateTime.now());

        //WHEN
        boolean actual = compatibleWithUpdateFilter.test(userReposDto);

        //THEN
        Assert.assertTrue(actual);
    }

    @Test
    public void isOlderThanDate() {
        //Given
        UserReposDto userReposDto = new UserReposDto();
        userReposDto.setUpdated_at(LocalDateTime.now());

        //AND
        Predicate<UserReposDto> compatibleWithUpdateFilter =
                UserReposPredicate.isEarlierThanDate(LocalDateTime.now().minusMonths(1));

        //WHEN
        boolean actual = compatibleWithUpdateFilter.test(userReposDto);

        //THEN
        Assert.assertTrue(actual);

    }
}