package com.norgini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.norgini.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
