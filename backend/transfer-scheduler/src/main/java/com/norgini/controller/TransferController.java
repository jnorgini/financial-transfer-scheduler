package com.norgini.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.norgini.dto.TransferRequest;
import com.norgini.dto.TransferResponse;
import com.norgini.mapper.TransferMapper;
import com.norgini.model.Transfer;
import com.norgini.service.TransferService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

	private final TransferService transferService;
	private final TransferMapper transferMapper;

	@GetMapping
	public ResponseEntity<List<TransferResponse>> getStatement() {
		List<Transfer> transfers = transferService.getAllTransfers();
		List<TransferResponse> responseList = transferMapper.toResponseList(transfers);
		return ResponseEntity.ok(responseList);
	}

	@PostMapping
	public ResponseEntity<TransferResponse> schedule(@Valid @RequestBody TransferRequest request) {
		Transfer transferEntity = transferMapper.toEntity(request);
		Transfer savedTransfer = transferService.scheduleTransfer(transferEntity);
		TransferResponse response = transferMapper.toResponse(savedTransfer);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
