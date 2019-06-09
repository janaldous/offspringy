package com.janaldous.offspringy.web.dto;

import lombok.Data;

@Data
public class SuccessfulBookingDto {
	private EventDto event;
	private int quantity;
}
