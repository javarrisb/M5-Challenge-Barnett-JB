package com.trilogyed.gamestoreinvoicing.invoice.repository;


import com.trilogyed.gamestoreinvoicing.invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByName(String name);
}
