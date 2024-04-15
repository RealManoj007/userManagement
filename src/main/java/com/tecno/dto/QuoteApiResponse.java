package com.tecno.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuoteApiResponse {

	private List<QuoteDto> quotes; //can take QuoteDto[] also
}
