package com.janaldous.offspringy.activity.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventPatchDto {

    private Date date;
    
    private String title;
    
    private String description;
}