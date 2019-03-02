package com.twereski.task.app.github.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserReposDto {

    private String name;
    private String id;
    private String language;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
