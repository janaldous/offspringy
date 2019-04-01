package com.janaldous.offspringy.activity.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    private Date date;
    
    @NotNull
    private String title;
    
    @NotNull
    private String description;
}