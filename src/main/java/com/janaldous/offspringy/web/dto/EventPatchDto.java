package com.janaldous.offspringy.web.dto;

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