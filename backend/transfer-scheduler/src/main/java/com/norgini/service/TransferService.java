package com.norgini.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.norgini.exception.BusinessException;
import com.norgini.model.FeeRule;
import com.norgini.model.Transfer;
import com.norgini.repository.TransferRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferService {

	private final TransferRepository repository;

	public List<Transfer> getAllTransfers() {
		return repository.findAll();
	}

	public Transfer scheduleTransfer(Transfer transfer) {
		transfer.setScheduledDate(LocalDate.now());

		long daysBetween = ChronoUnit.DAYS.between(
				transfer.getScheduledDate(), 
				transfer.getTransferDate());

		FeeRule rule = FeeRule.findRule(daysBetween)
				.orElseThrow(() -> new BusinessException(
						"Não há taxa aplicável para um agendamento com " 
						+ daysBetween
						+ " dias de antecedência. O limite máximo permitido é de 50 dias."));

		transfer.setFee(rule.calculateFee(transfer.getAmount()));

		return repository.save(transfer);
	}
	
}
