package com.trilogyed.gamestoreinvoicing.invoice.repository;

import com.trilogyed.gamestoreinvoicing.invoice.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {
}

