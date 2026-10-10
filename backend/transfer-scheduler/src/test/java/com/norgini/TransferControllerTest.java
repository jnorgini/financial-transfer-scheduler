package com.norgini;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.norgini.controller.TransferController;
import com.norgini.dto.TransferRequest;
import com.norgini.dto.TransferResponse;
import com.norgini.mapper.TransferMapper;
import com.norgini.model.Transfer;
import com.norgini.service.TransferService;

@WebMvcTest(TransferController.class)
class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransferService transferService;

	@MockBean
	private TransferMapper transferMapper;

	@Test
	void shouldReturnOkWhenGettingStatement() throws Exception {
		when(transferService.getAllTransfers()).thenReturn(Collections.emptyList());
		when(transferMapper.toResponseList(any())).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/api/transfers").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void shouldReturnCreatedWhenSchedulingTransferSuccessfully() throws Exception {
		Transfer mockTransfer = new Transfer();
		TransferResponse mockResponse = new TransferResponse();

		when(transferMapper.toEntity(any(TransferRequest.class))).thenReturn(mockTransfer);
		when(transferService.scheduleTransfer(any(Transfer.class))).thenReturn(mockTransfer);
		when(transferMapper.toResponse(any(Transfer.class))).thenReturn(mockResponse);

		String jsonPayload = "{" + "\"sourceAccount\":\"1234567890\"," + "\"destinationAccount\":\"0987654321\","
				+ "\"amount\":1000.00," + "\"transferDate\":\"2026-10-15\"" + "}";

		mockMvc.perform(post("/api/transfers").contentType(MediaType.APPLICATION_JSON).content(jsonPayload))
				.andExpect(status().isCreated());
	}
}
