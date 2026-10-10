package com.norgini.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.norgini.dto.TransferRequest;
import com.norgini.dto.TransferResponse;
import com.norgini.model.Transfer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransferMapper {

	private final ModelMapper mapper;

	public Transfer toEntity(TransferRequest request) {
		return mapper.map(request, Transfer.class);
	}

	public TransferResponse toResponse(Transfer transfer) {
		return mapper.map(transfer, TransferResponse.class);
	}

	public List<TransferResponse> toResponseList(List<Transfer> transfers) {
		return transfers.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

}
