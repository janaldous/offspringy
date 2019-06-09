package com.janaldous.offspringy.web.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {

    @NotNull
    private LocalDate date;
    
    @NotNull
    private String title;
    
    @NotNull
    private String description;
    
    private int capacity;
}