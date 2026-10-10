package com.norgini;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.norgini.exception.BusinessException;
import com.norgini.model.Transfer;
import com.norgini.repository.TransferRepository;
import com.norgini.service.TransferService;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

	@Mock
	private TransferRepository repository;

	@InjectMocks
	private TransferService transferService;

	private Transfer baseTransfer;
	private LocalDate today;

	@BeforeEach
	void setUp() {
		today = LocalDate.now();
		baseTransfer = new Transfer();
		baseTransfer.setSourceAccount("1234567890");
		baseTransfer.setDestinationAccount("0987654321");
		baseTransfer.setAmount(new BigDecimal("1000.00"));
		baseTransfer.setScheduledDate(today);

		lenient().when(repository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));
	}

	@Test
	void testRange0Days() {
		baseTransfer.setTransferDate(today);
		Transfer result = transferService.scheduleTransfer(baseTransfer);
		assertNotNull(result.getFee());
		assertEquals(new BigDecimal("28.00"), result.getFee());
	}

	@Test
	void testRange1To10Days() {
		baseTransfer.setTransferDate(today.plusDays(5));
		Transfer result = transferService.scheduleTransfer(baseTransfer);
		assertEquals(new BigDecimal("12.00"), result.getFee());
	}

	@Test
	void testRange11To20Days() {
		baseTransfer.setTransferDate(today.plusDays(15));
		Transfer result = transferService.scheduleTransfer(baseTransfer);
		assertEquals(new BigDecimal("82.00"), result.getFee());
	}

	@Test
	void testRange21To30Days() {
		baseTransfer.setTransferDate(today.plusDays(25));
		Transfer result = transferService.scheduleTransfer(baseTransfer);
		assertEquals(new BigDecimal("69.00"), result.getFee());
	}

	@Test
	void testRange31To40Days() {
		baseTransfer.setTransferDate(today.plusDays(35));
		Transfer result = transferService.scheduleTransfer(baseTransfer);
		assertEquals(new BigDecimal("47.00"), result.getFee());
	}

	@Test
	void testRange41To50Days() {
		baseTransfer.setTransferDate(today.plusDays(45));
		Transfer result = transferService.scheduleTransfer(baseTransfer);
		assertEquals(new BigDecimal("17.00"), result.getFee());
	}

	@Test
	void testErrorAbove50Days() {
		baseTransfer.setTransferDate(today.plusDays(55));
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			transferService.scheduleTransfer(baseTransfer);
		});

		assertEquals(
				"Não há taxa aplicável para um agendamento com 55 dias de antecedência. O limite máximo permitido é de 50 dias.",
				exception.getMessage());
		verify(repository, never()).save(any());
	}
	
}
