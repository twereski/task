package com.twereski.task.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RepositoryDto {

    private String name;
    private String language;
    private LocalDateTime createDate;
    private LocalDateTime lastModificationDate;
    private boolean updatedLessThanThreeMonthAgo;
}
