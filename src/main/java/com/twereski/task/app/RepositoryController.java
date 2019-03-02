package com.twereski.task.app;

import com.twereski.task.app.dto.RepositoryDto;
import com.twereski.task.app.dto.Sort;
import com.twereski.task.app.github.GithubFacade;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/repository")
public class RepositoryController {

    private final GithubFacade facade;

    @GetMapping("/{userName}")
    List<RepositoryDto> exchange(@PathVariable("userName") String userName,
                                 @RequestParam(value = "actual", required = false) Boolean actual,
                                 @RequestParam(value = "sort", required = false, defaultValue = "ASC") Sort sort) {
        return facade.getRepos(userName, actual, sort);
//        return new RepositoriesInfoDto();
    }

}
