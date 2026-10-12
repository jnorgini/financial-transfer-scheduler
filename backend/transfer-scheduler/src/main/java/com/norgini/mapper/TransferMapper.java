package com.norgini.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.norgini.dto.TransferRequest;
import com.norgini.dto.TransferResponse;
import com.norgini.model.Transfer;

@Mapper(componentModel = "spring")
public interface TransferMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "fee", ignore = true)
	@Mapping(target = "scheduledDate", ignore = true)
	Transfer toEntity(TransferRequest request);

	TransferResponse toResponse(Transfer transfer);

	List<TransferResponse> toResponseList(List<Transfer> transfers);

}
