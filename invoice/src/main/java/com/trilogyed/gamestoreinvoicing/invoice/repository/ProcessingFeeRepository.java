package com.trilogyed.gamestoreinvoicing.invoice.repository;


import com.trilogyed.gamestoreinvoicing.invoice.model.ProcessingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessingFeeRepository extends JpaRepository<ProcessingFee, String> {
}

