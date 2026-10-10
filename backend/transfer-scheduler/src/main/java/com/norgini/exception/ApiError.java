package com.norgini.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

	private LocalDateTime timestamp;
	private Integer status;
	private String error;
	private String message;
	private List<FieldMessage> fields;

	@Data
	@Builder
	public static class FieldMessage {
		private String name;
		private String message;
	}

}
